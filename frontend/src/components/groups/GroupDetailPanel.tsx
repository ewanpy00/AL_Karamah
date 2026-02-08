import { GroupDetail, CompatibilityIssue } from '../../types/group'
import { useNavigate } from 'react-router-dom'
import './GroupDetailPanel.css'

interface GroupDetailPanelProps {
  group: GroupDetail
  onRefresh: () => void
  onEdit: (group: GroupDetail) => void
  onDelete: (group: GroupDetail) => void
}

export default function GroupDetailPanel({ group, onRefresh, onEdit, onDelete }: GroupDetailPanelProps) {
  const navigate = useNavigate()
  const getSeverityColor = (severity: string) => {
    switch (severity) {
      case 'HIGH': return '#e74c3c'
      case 'WARNING': return '#f39c12'
      default: return '#3498db'
    }
  }

  // ensure onRefresh is marked as used for TypeScript build environments
  void onRefresh

  return (
    <div className="group-detail-panel">
      <div className="panel-header">
        <div>
          <h2>{group.name}</h2>
          <div className="header-actions">
            <button className="header-btn" onClick={() => onEdit(group)}>Edit</button>
            <button className="header-btn danger" onClick={() => onDelete(group)}>Delete</button>
          </div>
        </div>
        <div className="header-badges">
          {group.focusDomainName && (
            <span className="domain-chip">{group.focusDomainName}</span>
          )}
          <span className="age-chip">Ages {group.ageRangeMin}-{group.ageRangeMax}</span>
        </div>
      </div>

      {group.description && (
        <div className="panel-section">
          <p>{group.description}</p>
        </div>
      )}

      <div className="panel-section">
        <h3>Compatibility Summary</h3>
        <div className="compatibility-score">
          <div className="score-bar">
            <div
              className="score-fill"
              style={{
                width: `${group.compatibilitySummary.score}%`,
                backgroundColor: group.compatibilitySummary.score >= 80 ? '#2ecc71' :
                                group.compatibilitySummary.score >= 60 ? '#f39c12' : '#e74c3c'
              }}
            />
          </div>
          <span className="score-text">{group.compatibilitySummary.score}%</span>
        </div>
        {group.compatibilitySummary.issues.length > 0 && (
          <div className="compatibility-issues">
            {group.compatibilitySummary.issues.map((issue: CompatibilityIssue, idx: number) => (
              <div key={idx} className="issue-item" style={{ borderLeftColor: getSeverityColor(issue.severity) }}>
                <span className="issue-severity" style={{ color: getSeverityColor(issue.severity) }}>
                  {issue.severity}
                </span>
                <p>{issue.message}</p>
              </div>
            ))}
          </div>
        )}
      </div>

      <div className="panel-section">
        <h3>Students ({group.students.length})</h3>
        <div className="students-grid">
          {group.students.map(student => (
            <button
              key={student.id}
              type="button"
              className="student-card student-card-button"
              onClick={() => navigate(`/students/${student.id}`)}
            >
              <div className="student-name">{student.name}</div>
              <div className="student-age">Age {student.age}</div>
              {student.compatibilityFlags.length > 0 && (
                <div className="student-flags">
                  {student.compatibilityFlags.map((flag, idx) => (
                    <span key={idx} className="flag-badge">{flag}</span>
                  ))}
                </div>
              )}
            </button>
          ))}
        </div>
      </div>
    </div>
  )
}
