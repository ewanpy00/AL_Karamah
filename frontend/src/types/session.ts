export type SessionStatus = 'PLANNED' | 'IN_PROGRESS' | 'COMPLETED' | 'CANCELED'

export interface CalendarSession {
  id: string
  title: string
  groupId: string
  groupName: string
  startTime: string
  endTime: string
  focusDomainCode: string
  focusDomainName: string
  status: SessionStatus
  aetTags: string[]
}

export interface AetStatement {
  id: string
  code: string
  description: string
  domainName: string
}

export interface SessionResource {
  id: string
  title: string
  type: string
  role: string
}

export interface SessionDetail {
  id: string
  title: string
  groupId: string
  groupName: string
  startTime: string
  endTime: string
  location: string
  ownerId: string
  ownerName: string
  status: SessionStatus
  notes?: string
  aetFocusStatements: AetStatement[]
  selectedResources: SessionResource[]
}

export interface CreateSessionRequest {
  title: string
  groupId: string
  ownerId: string
  startTime: string
  endTime: string
  location: string
  notes?: string
  aetStatementIds?: string[]
}

export interface UpdateSessionRequest {
  title?: string
  startTime?: string
  endTime?: string
  location?: string
  notes?: string
}

export interface ResourceSuggestion {
  resourceId: string
  title: string
  type: string
  tags: string[]
  score: number
  reason: string
}

export interface ResourceSuggestionResponse {
  sessionId: string
  suggestions: ResourceSuggestion[]
}
