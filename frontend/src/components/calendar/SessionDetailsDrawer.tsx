import { useState, useEffect } from 'react'
import { sessionsApi } from '../../api/sessionsApi'
import { SessionDetail } from '../../types/session'
import './SessionDetailsDrawer.css'

interface SessionDetailsDrawerProps {
  sessionId: string
  onClose: () => void
}

export default function SessionDetailsDrawer({ sessionId, onClose }: SessionDetailsDrawerProps) {
  const [session, setSession] = useState<SessionDetail | null>(null)
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    loadSession()
  }, [sessionId])

  const loadSession = async () => {
    try {
      const data = await sessionsApi.getSessionById(sessionId)
      setSession(data)
    } catch (error) {
      console.error('Failed to load session', error)
    } finally {
      setLoading(false)
    }
  }

  if (loading) {
    return (
      <div className="drawer-overlay" onClick={onClose}>
        <div className="drawer" onClick={(e) => e.stopPropagation()}>
          <div className="loading">Loading...</div>
        </div>
      </div>
    )
  }

  if (!session) {
    return null
  }

  return (
    <div className="drawer-overlay" onClick={onClose}>
      <div className="drawer" onClick={(e) => e.stopPropagation()}>
        <div className="drawer-header">
          <h3>{session.title}</h3>
          <button onClick={onClose} className="close-btn">×</button>
        </div>
        <div className="drawer-content">
          <div className="session-info">
            <div className="info-row">
              <strong>Group:</strong> {session.groupName}
            </div>
            <div className="info-row">
              <strong>Time:</strong> {new Date(session.startTime).toLocaleString()} - {new Date(session.endTime).toLocaleString()}
            </div>
            <div className="info-row">
              <strong>Location:</strong> {session.location}
            </div>
            <div className="info-row">
              <strong>Owner:</strong> {session.ownerName}
            </div>
            <div className="info-row">
              <strong>Status:</strong> <span className={`status-badge status-${session.status.toLowerCase()}`}>{session.status}</span>
            </div>
          </div>

          {session.aetFocusStatements.length > 0 && (
            <div className="section">
              <h4>AET Focus</h4>
              <div className="tags">
                {session.aetFocusStatements.map(stmt => (
                  <span key={stmt.id} className="tag">
                    {stmt.code}
                  </span>
                ))}
              </div>
            </div>
          )}

          {session.selectedResources.length > 0 && (
            <div className="section">
              <h4>Resources</h4>
              <ul className="resource-list">
                {session.selectedResources.map(resource => (
                  <li key={resource.id}>
                    {resource.title} <span className="resource-type">({resource.type})</span>
                  </li>
                ))}
              </ul>
            </div>
          )}

          {session.notes && (
            <div className="section">
              <h4>Notes</h4>
              <p>{session.notes}</p>
            </div>
          )}
        </div>
      </div>
    </div>
  )
}

