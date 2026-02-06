import { useState, useEffect } from 'react'
import { groupsApi } from '../api/groupsApi'
import { GroupSummary, GroupDetail } from '../types/group'
import GroupDetailPanel from '../components/groups/GroupDetailPanel'
import './GroupsPage.css'

export default function GroupsPage() {
  const [groups, setGroups] = useState<GroupSummary[]>([])
  const [selectedGroup, setSelectedGroup] = useState<string | null>(null)
  const [groupDetail, setGroupDetail] = useState<GroupDetail | null>(null)
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    loadGroups()
  }, [])

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

  const getCompatibilityColor = (score?: number) => {
    if (!score) return '#95a5a6'
    if (score >= 80) return '#2ecc71'
    if (score >= 60) return '#f39c12'
    return '#e74c3c'
  }

  return (
    <div className="groups-page">
      <div className="groups-header">
        <h2>Groups</h2>
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
                    <span className="domain-badge">{group.focusDomainName}</span>
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
    </div>
  )
}

