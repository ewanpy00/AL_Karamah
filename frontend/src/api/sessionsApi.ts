import apiClient from './client'
import { CalendarSession, SessionDetail, CreateSessionRequest, ResourceSuggestionResponse, UpdateSessionRequest } from '../types/session'

export const sessionsApi = {
  getCalendarSessions: async (from: string, to: string, groupId?: string, ownerId?: string): Promise<CalendarSession[]> => {
    const params = new URLSearchParams()
    params.append('from', from)
    params.append('to', to)
    if (groupId) params.append('groupId', groupId)
    if (ownerId) params.append('ownerId', ownerId)
    
    const response = await apiClient.get<CalendarSession[]>(`/sessions/calendar?${params.toString()}`)
    return response.data
  },

  getSessionById: async (id: string): Promise<SessionDetail> => {
    const response = await apiClient.get<SessionDetail>(`/sessions/${id}`)
    return response.data
  },

  createSession: async (request: CreateSessionRequest): Promise<any> => {
    const response = await apiClient.post('/sessions', request)
    return response.data
  },

  updateSession: async (id: string, request: UpdateSessionRequest): Promise<any> => {
    const response = await apiClient.put(`/sessions/${id}`, request)
    return response.data
  },

  suggestResources: async (sessionId: string, maxResults: number = 10): Promise<ResourceSuggestionResponse> => {
    const response = await apiClient.post<ResourceSuggestionResponse>(
      `/sessions/${sessionId}/resources/suggestions?maxResults=${maxResults}`
    )
    return response.data
  },
}
