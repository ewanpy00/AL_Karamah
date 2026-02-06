export interface GroupSummary {
  id: string
  name: string
  ageRangeMin: number
  ageRangeMax: number
  focusDomainName: string
  studentCount: number
  compatibilityScore?: number
}

export interface CompatibilityIssue {
  type: string
  message: string
  severity: 'INFO' | 'WARNING' | 'HIGH'
}

export interface CompatibilitySummary {
  score: number
  issues: CompatibilityIssue[]
}

export interface StudentInGroup {
  id: string
  name: string
  age: number
  compatibilityFlags: string[]
}

export interface GroupDetail {
  id: string
  name: string
  ageRangeMin: number
  ageRangeMax: number
  focusDomainId: string
  focusDomainName: string
  description?: string
  students: StudentInGroup[]
  compatibilitySummary: CompatibilitySummary
}

export interface AddStudentsToGroupRequest {
  studentIds: string[]
}

export interface AddStudentsToGroupResponse {
  addedStudents: any[]
  compatibilityWarnings: CompatibilityIssue[]
}

