import { useEffect, useMemo, useState } from 'react'
import { Calendar, momentLocalizer } from 'react-big-calendar'
import withDragAndDrop from 'react-big-calendar/lib/addons/dragAndDrop'
import moment from 'moment'
import 'moment/locale/en-gb'
import 'react-big-calendar/lib/css/react-big-calendar.css'
import 'react-big-calendar/lib/addons/dragAndDrop/styles.css'
import { useAuthStore } from '../store/authStore'
import { sessionsApi } from '../api/sessionsApi'
import { CalendarSession } from '../types/session'
import SessionDetailsDrawer from '../components/calendar/SessionDetailsDrawer'
import CreateSessionModal from '../components/calendar/CreateSessionModal'
import './DashboardPage.css'

moment.locale('en-gb')
const localizer = momentLocalizer(moment)
const DnDCalendar = withDragAndDrop(Calendar as any)
const DnDCalendarAny = DnDCalendar as any

function getInitials(name?: string) {
  if (!name) return '?'
  const parts = name.trim().split(/\s+/)
  const first = parts[0]?.[0] ?? ''
  const second = parts[1]?.[0] ?? ''
  return `${first}${second}`.toUpperCase() || '?'
}

export default function DashboardPage() {
  const { user } = useAuthStore()
  const [sessions, setSessions] = useState<CalendarSession[]>([])
  const [selectedSession, setSelectedSession] = useState<string | null>(null)
  const [loading, setLoading] = useState(true)
  const [isCreateOpen, setIsCreateOpen] = useState(false)
  const [createRange, setCreateRange] = useState<{ start: Date; end: Date } | null>(null)
  const [isEditingClass, setIsEditingClass] = useState(false)
  const [classValue, setClassValue] = useState(user?.className || '')

  const today = useMemo(() => new Date(), [])

  const loadSessions = async () => {
    setLoading(true)
    try {
      const start = moment(today).startOf('day').toISOString()
      const end = moment(today).endOf('day').toISOString()
      const data = await sessionsApi.getCalendarSessions(start, end)
      setSessions(data)
    } catch (error) {
      console.error('Failed to load today sessions', error)
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    loadSessions()
  }, [today])

  useEffect(() => {
    setClassValue(user?.className || '')
  }, [user?.className])

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
    <div className="dashboard-page">
      <div className="dashboard-hero">
        <div className="avatar">
          <span>{getInitials(user?.fullName)}</span>
        </div>
        <div className="hero-text">
          <div className="hero-name">{user?.fullName || 'User'}</div>
          <div className="hero-class">
            {isEditingClass ? (
              <input
                className="hero-class-input"
                value={classValue}
                onChange={(e) => setClassValue(e.target.value)}
                onBlur={() => setIsEditingClass(false)}
                onKeyDown={(e) => {
                  if (e.key === 'Enter') setIsEditingClass(false)
                }}
                placeholder="Class number"
                autoFocus
              />
            ) : (
              <button
                type="button"
                className="hero-class-button"
                onClick={() => setIsEditingClass(true)}
                aria-label="Change class"
              >
                {classValue || user?.className || 'Class not set'}
              </button>
            )}
          </div>
        </div>
      </div>

      <div className="dashboard-calendar">
        <div className="calendar-title">Today</div>
        {loading ? (
          <div className="loading">Loading...</div>
        ) : (
          <DnDCalendarAny
            localizer={localizer}
            events={events}
            startAccessor="start"
            endAccessor="end"
            view="day"
            views={['day']}
            date={today}
            toolbar={false}
            onSelectEvent={(event: any) => setSelectedSession(event.id)}
            eventPropGetter={eventStyleGetter}
            selectable
            resizable
            onSelectSlot={(slotInfo: any) => {
              setCreateRange({ start: slotInfo.start as Date, end: slotInfo.end as Date })
              setIsCreateOpen(true)
            }}
            onEventDrop={async ({ event, start, end }: any) => {
              await sessionsApi.updateSession(event.id, {
                startTime: (start as Date).toISOString(),
                endTime: (end as Date).toISOString(),
              })
              await loadSessions()
            }}
            onEventResize={async ({ event, start, end }: any) => {
              await sessionsApi.updateSession(event.id, {
                startTime: (start as Date).toISOString(),
                endTime: (end as Date).toISOString(),
              })
              await loadSessions()
            }}
            style={{ height: '520px' }}
          />
        )}
      </div>

      {selectedSession && (
        <SessionDetailsDrawer
          sessionId={selectedSession}
          onClose={() => setSelectedSession(null)}
        />
      )}
      {isCreateOpen && createRange && (
        <CreateSessionModal
          open={isCreateOpen}
          start={createRange.start}
          end={createRange.end}
          onClose={() => setIsCreateOpen(false)}
          onCreate={async ({ title, groupId, location }) => {
            if (!user?.id) return
            await sessionsApi.createSession({
              title,
              groupId,
              ownerId: user.id,
              startTime: createRange.start.toISOString(),
              endTime: createRange.end.toISOString(),
              location,
            })
            setIsCreateOpen(false)
            await loadSessions()
          }}
        />
      )}
    </div>
  )
}
