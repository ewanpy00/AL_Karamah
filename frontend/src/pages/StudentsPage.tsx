import { useState, useEffect } from 'react'
import { studentsApi } from '../api/studentsApi'
import { StudentSummary } from '../types/student'
import { useNavigate } from 'react-router-dom'
import './StudentsPage.css'

export default function StudentsPage() {
  const [students, setStudents] = useState<StudentSummary[]>([])
  const [loading, setLoading] = useState(true)
  const [search, setSearch] = useState('')
  const navigate = useNavigate()

  useEffect(() => {
    loadStudents()
  }, [search])

  const loadStudents = async () => {
    setLoading(true)
    try {
      const data = await studentsApi.getStudents(search || undefined)
      setStudents(data)
    } catch (error) {
      console.error('Failed to load students', error)
      setStudents([])
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="students-page">
      <div className="students-header">
        <h2>Students</h2>
        <input
          type="text"
          placeholder="Search students..."
          value={search}
          onChange={(e) => setSearch(e.target.value)}
          className="search-input"
        />
      </div>
      {loading ? (
        <div className="loading">Loading...</div>
      ) : (
        <div className="students-grid">
          {students.map(student => (
            <div
              key={student.id}
              className="student-card"
              onClick={() => navigate(`/students/${student.id}`)}
              style={{ cursor: 'pointer' }}
            >
              <h3>{student.firstName} {student.lastName}</h3>
              <div className="student-info">
                <span className="student-age">Age {student.age}</span>
                {student.keyNotes && (
                  <p className="student-notes">{student.keyNotes}</p>
                )}
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  )
}

