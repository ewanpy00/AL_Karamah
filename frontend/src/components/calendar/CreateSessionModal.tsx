import { useEffect, useState } from 'react'
import { groupsApi } from '../../api/groupsApi'
import { GroupSummary } from '../../types/group'
import './CreateSessionModal.css'

interface CreateSessionModalProps {
  open: boolean
  start: Date
  end: Date
  onClose: () => void
  onCreate: (payload: { title: string; groupId: string; location: string }) => void
}

export default function CreateSessionModal({ open, start, end, onClose, onCreate }: CreateSessionModalProps) {
  const [groups, setGroups] = useState<GroupSummary[]>([])
  const [loadingGroups, setLoadingGroups] = useState(false)
  const [title, setTitle] = useState('')
  const [groupId, setGroupId] = useState('')
  const [location, setLocation] = useState('')
  const [error, setError] = useState<string | null>(null)

  useEffect(() => {
    if (!open) return
    const load = async () => {
      setLoadingGroups(true)
      try {
        const data = await groupsApi.getGroups(true)
        setGroups(data)
        if (!groupId && data.length > 0) {
          setGroupId(data[0].id)
        }
      } catch (e) {
        setError('Failed to load groups')
      } finally {
        setLoadingGroups(false)
      }
    }
    load()
  }, [open])

  useEffect(() => {
    if (!open) {
      setTitle('')
      setLocation('')
      setError(null)
    }
  }, [open])

  if (!open) return null

  const handleCreate = () => {
    setError(null)
    if (!title.trim()) {
      setError('Title is required')
      return
    }
    if (!groupId) {
      setError('Group is required')
      return
    }
    if (!location.trim()) {
      setError('Location is required')
      return
    }
    onCreate({ title: title.trim(), groupId, location: location.trim() })
  }

  return (
    <div className="session-modal-overlay" onClick={onClose}>
      <div className="session-modal" onClick={(e) => e.stopPropagation()}>
        <div className="session-modal-header">
          <h3>Create Session</h3>
          <button className="session-modal-close" onClick={onClose}>✕</button>
        </div>
        <div className="session-modal-body">
          <div className="session-modal-row">
            <label>Time</label>
            <div className="session-modal-time">
              {start.toLocaleString()} → {end.toLocaleString()}
            </div>
          </div>
          <div className="session-modal-row">
            <label>Title</label>
            <input value={title} onChange={(e) => setTitle(e.target.value)} />
          </div>
          <div className="session-modal-row">
            <label>Group</label>
            <select value={groupId} onChange={(e) => setGroupId(e.target.value)}>
              <option value="">Select group</option>
              {groups.map((g) => (
                <option key={g.id} value={g.id}>{g.name}</option>
              ))}
            </select>
            {loadingGroups && <div className="session-modal-hint">Loading groups...</div>}
          </div>
          <div className="session-modal-row">
            <label>Location</label>
            <input value={location} onChange={(e) => setLocation(e.target.value)} />
          </div>
          {error && <div className="session-modal-error">{error}</div>}
        </div>
        <div className="session-modal-footer">
          <button className="session-secondary-btn" onClick={onClose}>Cancel</button>
          <button className="session-primary-btn" onClick={handleCreate}>Create</button>
        </div>
      </div>
    </div>
  )
}
