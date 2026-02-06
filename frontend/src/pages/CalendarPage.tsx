import { useState, useEffect } from 'react'
import { Calendar, momentLocalizer, View } from 'react-big-calendar'
import moment from 'moment'
import 'moment/locale/en-gb'
import 'react-big-calendar/lib/css/react-big-calendar.css'

moment.locale('en-gb')
import { sessionsApi } from '../api/sessionsApi'
import { CalendarSession } from '../types/session'
import SessionDetailsDrawer from '../components/calendar/SessionDetailsDrawer'
import './CalendarPage.css'

const localizer = momentLocalizer(moment)

export default function CalendarPage() {
  const [sessions, setSessions] = useState<CalendarSession[]>([])
  const [selectedSession, setSelectedSession] = useState<string | null>(null)
  const [currentView, setCurrentView] = useState<View>('week')
  const [currentDate, setCurrentDate] = useState(new Date())
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    loadSessions()
  }, [currentDate, currentView])

  const loadSessions = async () => {
    setLoading(true)
    try {
      const start = moment(currentDate).startOf(currentView === 'month' ? 'month' : 'week').toISOString()
      const end = moment(currentDate).endOf(currentView === 'month' ? 'month' : 'week').toISOString()
      const data = await sessionsApi.getCalendarSessions(start, end)
      setSessions(data)
    } catch (error) {
      console.error('Failed to load sessions', error)
    } finally {
      setLoading(false)
    }
  }

  const events = sessions.map(session => ({
    id: session.id,
    title: session.title,
    start: new Date(session.startTime),
    end: new Date(session.endTime),
    resource: session,
  }))

  const eventStyleGetter = (event: any) => {
    const session = event.resource as CalendarSession
    const colors: Record<string, string> = {
      'COMM': '#3498db',
      'SENS': '#e74c3c',
      'SOC': '#2ecc71',
    }
    const color = colors[session.focusDomainCode] || '#95a5a6'
    
    return {
      style: {
        backgroundColor: color,
        borderColor: color,
        color: 'white',
        borderRadius: '4px',
        border: 'none',
      },
    }
  }

  return (
    <div className="calendar-page">
      <div className="calendar-header">
        <h2>Weekly Calendar</h2>
        <div className="calendar-controls">
          <button onClick={() => setCurrentDate(new Date())}>Today</button>
          <button onClick={() => setCurrentDate(moment(currentDate).subtract(1, currentView === 'month' ? 'month' : 'week').toDate())}>
            ←
          </button>
          <button onClick={() => setCurrentDate(moment(currentDate).add(1, currentView === 'month' ? 'month' : 'week').toDate())}>
            →
          </button>
          <select value={currentView} onChange={(e) => setCurrentView(e.target.value as View)}>
            <option value="week">Week</option>
            <option value="month">Month</option>
          </select>
        </div>
      </div>
      {loading ? (
        <div className="loading">Loading...</div>
      ) : (
        <Calendar
          localizer={localizer}
          events={events}
          startAccessor="start"
          endAccessor="end"
          view={currentView}
          onView={setCurrentView}
          date={currentDate}
          onNavigate={setCurrentDate}
          onSelectEvent={(event) => setSelectedSession(event.id as string)}
          eventPropGetter={eventStyleGetter}
          style={{ height: 'calc(100vh - 120px)' }}
        />
      )}
      {selectedSession && (
        <SessionDetailsDrawer
          sessionId={selectedSession}
          onClose={() => setSelectedSession(null)}
        />
      )}
    </div>
  )
}

