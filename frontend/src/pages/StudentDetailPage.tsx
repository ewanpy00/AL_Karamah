import { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import { studentsApi } from '../api/studentsApi'
import { StudentDetail } from '../types/student'
import './StudentDetailPage.css'

export default function StudentDetailPage() {
  const { id } = useParams<{ id: string }>()
  const [student, setStudent] = useState<StudentDetail | null>(null)
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)

  useEffect(() => {
    const load = async () => {
      if (!id) return
      try {
        const data = await studentsApi.getStudentById(id)
        setStudent(data)
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
      </div>
    </div>
  )
}


