import { Link, useLocation } from 'react-router-dom'
import { useAuthStore } from '../../store/authStore'
import './AppShell.css'

interface AppShellProps {
  children: React.ReactNode
}

export default function AppShell({ children }: AppShellProps) {
  const location = useLocation()
  const { user, logout } = useAuthStore()

  const isActive = (path: string) => location.pathname === path

  return (
    <div className="app-shell">
      <nav className="sidebar">
        <div className="sidebar-header">
          <h1>AET Platform</h1>
        </div>
        <ul className="sidebar-nav">
          <li>
            <Link to="/" className={isActive('/') ? 'active' : ''}>
              📅 Calendar
            </Link>
          </li>
          <li>
            <Link to="/groups" className={isActive('/groups') ? 'active' : ''}>
              👥 Groups
            </Link>
          </li>
          <li>
            <Link to="/students" className={isActive('/students') ? 'active' : ''}>
              🎓 Students
            </Link>
          </li>
        </ul>
        <div className="sidebar-footer">
          <div className="user-info">
            <span>{user?.fullName}</span>
            <span className="user-role">{user?.role}</span>
          </div>
          <button onClick={logout} className="logout-btn">
            Logout
          </button>
        </div>
      </nav>
      <main className="main-content">
        {children}
      </main>
    </div>
  )
}

