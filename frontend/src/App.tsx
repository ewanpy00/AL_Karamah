import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom'
import { useAuthStore } from './store/authStore'
import LoginPage from './pages/LoginPage'
import CalendarPage from './pages/CalendarPage'
import GroupsPage from './pages/GroupsPage'
import StudentsPage from './pages/StudentsPage'
import StudentDetailPage from './pages/StudentDetailPage'
import AppShell from './components/layout/AppShell'

function App() {
  const { isAuthenticated } = useAuthStore()

  return (
    <Router>
      <Routes>
        <Route path="/login" element={!isAuthenticated ? <LoginPage /> : <Navigate to="/" />} />
        <Route path="/" element={isAuthenticated ? <AppShell><CalendarPage /></AppShell> : <Navigate to="/login" />} />
        <Route path="/groups" element={isAuthenticated ? <AppShell><GroupsPage /></AppShell> : <Navigate to="/login" />} />
        <Route path="/students" element={isAuthenticated ? <AppShell><StudentsPage /></AppShell> : <Navigate to="/login" />} />
        <Route path="/students/:id" element={isAuthenticated ? <AppShell><StudentDetailPage /></AppShell> : <Navigate to="/login" />} />
      </Routes>
    </Router>
  )
}

export default App

