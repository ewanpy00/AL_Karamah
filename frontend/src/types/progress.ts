export type ProgressStatus = 'NOT_STARTED' | 'IN_PROGRESS' | 'COMPLETED'

export interface StudentProgressEntry {
  id: string
  itemKey: string
  status: ProgressStatus
  updatedAt: string
}
