export interface StudentSummary {
  id: string
  firstName: string
  lastName: string
  age: number
  keyNotes?: string
}

export interface AetProfileEntry {
  domainId: string
  domainName: string
  statementId: string
  statementCode: string
  statementDescription: string
  currentLevel?: string
  lastUpdated: string
}

export interface StudentDetail {
  id: string
  firstName: string
  lastName: string
  age: number
  primaryNeedsNotes?: string
  communicationNotes?: string
  sensoryNotes?: string
  generalNotes?: string
  aetProfile: AetProfileEntry[]
}

