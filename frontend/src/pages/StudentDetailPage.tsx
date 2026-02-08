import { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import { studentsApi } from '../api/studentsApi'
import { StudentDetail } from '../types/student'
import { ProgressStatus, StudentProgressEntry } from '../types/progress'
import { progressTree } from '../data/progressTree'
import './StudentDetailPage.css'

export default function StudentDetailPage() {
  const { id } = useParams<{ id: string }>()
  const [student, setStudent] = useState<StudentDetail | null>(null)
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)
  const [progressMap, setProgressMap] = useState<Record<string, ProgressStatus>>({})
  const [savingKey, setSavingKey] = useState<string | null>(null)
  const [selectedDomainId, setSelectedDomainId] = useState(progressTree[0]?.id || '')

  useEffect(() => {
    const load = async () => {
      if (!id) return
      try {
        const data = await studentsApi.getStudentById(id)
        setStudent(data)
        const progress = await studentsApi.getStudentProgress(id)
        const map: Record<string, ProgressStatus> = {}
        progress.forEach((entry: StudentProgressEntry) => {
          map[entry.itemKey] = entry.status
        })
        setProgressMap(map)
      } catch (e: any) {
        console.error('Failed to load student', e)
        setError('Failed to load student')
      } finally {
        setLoading(false)
      }
    }
    load()
  }, [id])

  if (loading) {
    return <div className="student-detail-page"><div className="loading">Loading...</div></div>
  }

  if (error || !student) {
    return <div className="student-detail-page"><div className="error">{error || 'Student not found'}</div></div>
  }

  return (
    <div className="student-detail-page">
      <div className="student-header">
        <div>
          <h2>{student.firstName} {student.lastName}</h2>
          <p className="student-subtitle">Age {student.age}</p>
        </div>
      </div>

      <div className="student-layout">
        <section className="student-section">
          <h3>Profile</h3>
          <div className="student-notes-grid">
            <div>
              <h4>Primary needs</h4>
              <p>{student.primaryNeedsNotes || '—'}</p>
            </div>
            <div>
              <h4>Communication</h4>
              <p>{student.communicationNotes || '—'}</p>
            </div>
            <div>
              <h4>Sensory</h4>
              <p>{student.sensoryNotes || '—'}</p>
            </div>
            <div>
              <h4>General notes</h4>
              <p>{student.generalNotes || '—'}</p>
            </div>
          </div>
        </section>

        <section className="student-section">
          <h3>AET Profile</h3>
          {!student.aetProfile || student.aetProfile.length === 0 ? (
            <p>No AET profile entries yet.</p>
          ) : (
            <div className="aet-table">
              <div className="aet-table-header">
                <span>Domain</span>
                <span>Code</span>
                <span>Description</span>
                <span>Level</span>
                <span>Last updated</span>
              </div>
              {student.aetProfile.map(entry => (
                <div key={entry.statementId} className="aet-table-row">
                  <span>{entry.domainName}</span>
                  <span>{entry.statementCode}</span>
                  <span>{entry.statementDescription}</span>
                  <span>{entry.currentLevel || '—'}</span>
                  <span>{new Date(entry.lastUpdated).toLocaleDateString()}</span>
                </div>
              ))}
            </div>
          )}
        </section>

        <section className="student-section">
          <h3>Progress</h3>
          <div className="progress-domain-picker">
            {progressTree.map(domain => (
              <button
                key={domain.id}
                type="button"
                className={`progress-domain-btn ${selectedDomainId === domain.id ? 'active' : ''}`}
                onClick={() => setSelectedDomainId(domain.id)}
              >
                {domain.id}. {domain.title}
              </button>
            ))}
          </div>

          {progressTree
            .filter(domain => domain.id === selectedDomainId)
            .map(domain => (
              <div key={domain.id} className="progress-domain">
                {domain.sections.map(section => (
                  <div key={section.id} className="progress-section">
                    <div className="progress-section-title">{section.id} {section.title}</div>
                    <div className="progress-items">
                      {section.items.map(item => {
                        const status = progressMap[item.id] || 'NOT_STARTED'
                        return (
                          <div key={item.id} className={`progress-item status-${status.toLowerCase()}`}>
                            <div className="progress-item-text">
                              <span className="progress-item-id">{item.id}</span>
                              <span className="progress-item-title">{item.title}</span>
                            </div>
                            <div className="progress-item-actions">
                              {(['NOT_STARTED', 'IN_PROGRESS', 'COMPLETED'] as ProgressStatus[]).map(s => (
                                <button
                                  key={s}
                                  type="button"
                                  className={`progress-status-btn ${status === s ? 'active' : ''}`}
                                  disabled={savingKey === item.id}
                                  onClick={async () => {
                                    if (!id) return
                                    setSavingKey(item.id)
                                    try {
                                      await studentsApi.updateStudentProgress(id, item.id, s)
                                      setProgressMap((prev) => ({ ...prev, [item.id]: s }))
                                    } finally {
                                      setSavingKey(null)
                                    }
                                  }}
                                >
                                  {s === 'NOT_STARTED' ? 'Not done' : s === 'IN_PROGRESS' ? 'In progress' : 'Done'}
                                </button>
                              ))}
                            </div>
                          </div>
                        )
                      })}
                    </div>
                  </div>
                ))}
              </div>
            ))}
        </section>
      </div>
    </div>
  )
}
