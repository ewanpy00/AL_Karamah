import { Link, useLocation } from 'react-router-dom'
import { useState } from 'react'
import { useAuthStore } from '../../store/authStore'
import './AppShell.css'

interface AppShellProps {
  children: React.ReactNode
}

export default function AppShell({ children }: AppShellProps) {
  const location = useLocation()
  const { user, logout } = useAuthStore()
  const [isMobileNavOpen, setIsMobileNavOpen] = useState(false)

  const isActive = (path: string) => location.pathname === path

  return (
    <div className="app-shell">
      <header className="mobile-header">
        <button
          className="burger-btn"
          type="button"
          aria-label="Open menu"
          onClick={() => setIsMobileNavOpen(true)}
        >
          <span />
          <span />
          <span />
        </button>
        <div className="mobile-title">AET Platform</div>
      </header>

      <div
        className={`mobile-overlay ${isMobileNavOpen ? 'open' : ''}`}
        onClick={() => setIsMobileNavOpen(false)}
      />

      <nav className={`sidebar ${isMobileNavOpen ? 'open' : ''}`}>
        <div className="sidebar-header">
          <h1>AET Platform</h1>
        </div>
        <ul className="sidebar-nav">
          <li>
            <Link to="/" className={isActive('/') ? 'active' : ''} onClick={() => setIsMobileNavOpen(false)}>
              🏠 Dashboard
            </Link>
          </li>
          <li>
            <Link to="/calendar" className={isActive('/calendar') ? 'active' : ''} onClick={() => setIsMobileNavOpen(false)}>
              📅 Calendar
            </Link>
          </li>
          <li>
            <Link to="/groups" className={isActive('/groups') ? 'active' : ''} onClick={() => setIsMobileNavOpen(false)}>
              👥 Groups
            </Link>
          </li>
          <li>
            <Link to="/students" className={isActive('/students') ? 'active' : ''} onClick={() => setIsMobileNavOpen(false)}>
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
