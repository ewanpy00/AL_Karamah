import { useState, useEffect } from 'react'
import { groupsApi } from '../api/groupsApi'
import { studentsApi } from '../api/studentsApi'
import { aetApi } from '../api/aetApi'
import { GroupSummary, GroupDetail } from '../types/group'
import { StudentSummary } from '../types/student'
import { AetDomain } from '../types/aet'
import { useAuthStore } from '../store/authStore'
import GroupDetailPanel from '../components/groups/GroupDetailPanel'
import './GroupsPage.css'

export default function GroupsPage() {
  const { user } = useAuthStore()
  const [groups, setGroups] = useState<GroupSummary[]>([])
  const [selectedGroup, setSelectedGroup] = useState<string | null>(null)
  const [groupDetail, setGroupDetail] = useState<GroupDetail | null>(null)
  const [loading, setLoading] = useState(true)
  const [isCreateOpen, setIsCreateOpen] = useState(false)
  const [domains, setDomains] = useState<AetDomain[]>([])
  const [students, setStudents] = useState<StudentSummary[]>([])
  const [selectedStudentIds, setSelectedStudentIds] = useState<string[]>([])
  const [creating, setCreating] = useState(false)
  const [createError, setCreateError] = useState<string | null>(null)
  const [createForm, setCreateForm] = useState({
    name: '',
    ageRangeMin: '',
    ageRangeMax: '',
    focusDomainId: '',
    description: '',
  })

  useEffect(() => {
    loadGroups()
  }, [])

  useEffect(() => {
    if (isCreateOpen) {
      loadDomainsAndStudents()
    }
  }, [isCreateOpen])

  useEffect(() => {
    if (selectedGroup) {
      loadGroupDetail(selectedGroup)
    }
  }, [selectedGroup])

  const loadGroups = async () => {
    try {
      const data = await groupsApi.getGroups(true)
      setGroups(data)
    } catch (error) {
      console.error('Failed to load groups', error)
    } finally {
      setLoading(false)
    }
  }

  const loadGroupDetail = async (groupId: string) => {
    try {
      const data = await groupsApi.getGroupById(groupId)
      setGroupDetail(data)
    } catch (error) {
      console.error('Failed to load group detail', error)
    }
  }

  const loadDomainsAndStudents = async () => {
    try {
      const [domainsData, studentsData] = await Promise.all([
        aetApi.getDomains(),
        studentsApi.getStudents(),
      ])
      setDomains(domainsData)
      setStudents(studentsData)
    } catch (error) {
      console.error('Failed to load create data', error)
    }
  }

  const getCompatibilityColor = (score?: number) => {
    if (!score) return '#95a5a6'
    if (score >= 80) return '#2ecc71'
    if (score >= 60) return '#f39c12'
    return '#e74c3c'
  }

  const toggleStudent = (id: string) => {
    setSelectedStudentIds((prev) => (
      prev.includes(id) ? prev.filter((item) => item !== id) : [...prev, id]
    ))
  }

  const resetCreateForm = () => {
    setCreateForm({
      name: '',
      ageRangeMin: '',
      ageRangeMax: '',
      focusDomainId: '',
      description: '',
    })
    setSelectedStudentIds([])
    setCreateError(null)
  }

  const handleCreateGroup = async () => {
    setCreateError(null)
    if (!user?.id) {
      setCreateError('User not available')
      return
    }

    const ageMin = Number(createForm.ageRangeMin)
    const ageMax = Number(createForm.ageRangeMax)

    if (!createForm.name.trim()) {
      setCreateError('Group name is required')
      return
    }
    if (!ageMin || !ageMax) {
      setCreateError('Age range is required')
      return
    }
    if (ageMin > ageMax) {
      setCreateError('Age range is invalid')
      return
    }

    try {
      setCreating(true)
      const group = await groupsApi.createGroup({
        name: createForm.name.trim(),
        ageRangeMin: ageMin,
        ageRangeMax: ageMax,
        focusDomainId: createForm.focusDomainId || undefined,
        createdById: user.id,
        description: createForm.description.trim() || undefined,
      })

      if (selectedStudentIds.length > 0) {
        await groupsApi.addStudentsToGroup(group.id, { studentIds: selectedStudentIds })
      }

      await loadGroups()
      setSelectedGroup(group.id)
      setIsCreateOpen(false)
      resetCreateForm()
    } catch (error) {
      console.error('Failed to create group', error)
      setCreateError('Failed to create group')
    } finally {
      setCreating(false)
    }
  }

  return (
    <div className="groups-page">
      <div className="groups-header">
        <h2>Groups</h2>
        <button className="create-group-btn" onClick={() => setIsCreateOpen(true)}>
          + Create Group
        </button>
      </div>
      <div className="groups-layout">
        <div className="groups-list">
          {loading ? (
            <div className="loading">Loading...</div>
          ) : (
            <>
              {groups.map(group => (
                <div
                  key={group.id}
                  className={`group-item ${selectedGroup === group.id ? 'active' : ''}`}
                  onClick={() => setSelectedGroup(group.id)}
                >
                  <div className="group-item-header">
                    <h3>{group.name}</h3>
                    <span className="compatibility-indicator" style={{ backgroundColor: getCompatibilityColor(group.compatibilityScore) }}>
                      {group.compatibilityScore !== undefined ? `${group.compatibilityScore}%` : 'N/A'}
                    </span>
                  </div>
                  <div className="group-item-info">
                    <span className="domain-badge">{group.focusDomainName || 'No domain'}</span>
                    <span className="age-range">Ages {group.ageRangeMin}-{group.ageRangeMax}</span>
                    <span className="student-count">{group.studentCount} students</span>
                  </div>
                </div>
              ))}
            </>
          )}
        </div>
        <div className="groups-detail">
          {groupDetail ? (
            <GroupDetailPanel group={groupDetail} onRefresh={loadGroups} />
          ) : (
            <div className="no-selection">Select a group to view details</div>
          )}
        </div>
      </div>

      {isCreateOpen && (
        <div className="modal-overlay" onClick={() => { setIsCreateOpen(false); resetCreateForm() }}>
          <div className="modal" onClick={(e) => e.stopPropagation()}>
            <div className="modal-header">
              <h3>Create Group</h3>
              <button className="modal-close" onClick={() => { setIsCreateOpen(false); resetCreateForm() }}>✕</button>
            </div>
            <div className="modal-body">
              <div className="form-row">
                <label>Group name</label>
                <input
                  value={createForm.name}
                  onChange={(e) => setCreateForm({ ...createForm, name: e.target.value })}
                  placeholder="e.g. Class 5A"
                />
              </div>
              <div className="form-row form-row-inline">
                <div>
                  <label>Age min</label>
                  <input
                    type="number"
                    value={createForm.ageRangeMin}
                    onChange={(e) => setCreateForm({ ...createForm, ageRangeMin: e.target.value })}
                  />
                </div>
                <div>
                  <label>Age max</label>
                  <input
                    type="number"
                    value={createForm.ageRangeMax}
                    onChange={(e) => setCreateForm({ ...createForm, ageRangeMax: e.target.value })}
                  />
                </div>
              </div>
              <div className="form-row">
                <label>Focus domain</label>
                <select
                  value={createForm.focusDomainId}
                  onChange={(e) => setCreateForm({ ...createForm, focusDomainId: e.target.value })}
                >
                  <option value="">Select domain</option>
                  {domains.map((domain) => (
                    <option key={domain.id} value={domain.id}>{domain.name}</option>
                  ))}
                </select>
              </div>
              <div className="form-row">
                <label>Description</label>
                <textarea
                  value={createForm.description}
                  onChange={(e) => setCreateForm({ ...createForm, description: e.target.value })}
                  rows={3}
                />
              </div>

              <div className="form-row">
                <label>Add students</label>
                <div className="student-list">
                  {students.map((student) => {
                    const name = `${student.firstName} ${student.lastName}`
                    return (
                      <label key={student.id} className="student-item">
                        <input
                          type="checkbox"
                          checked={selectedStudentIds.includes(student.id)}
                          onChange={() => toggleStudent(student.id)}
                        />
                        <span>{name} (age {student.age})</span>
                      </label>
                    )
                  })}
                </div>
              </div>

              {createError && <div className="form-error">{createError}</div>}
            </div>
            <div className="modal-footer">
              <button className="secondary-btn" onClick={() => { setIsCreateOpen(false); resetCreateForm() }}>
                Cancel
              </button>
              <button className="primary-btn" onClick={handleCreateGroup} disabled={creating}>
                {creating ? 'Creating...' : 'Create'}
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  )
}
