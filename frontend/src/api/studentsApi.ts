import apiClient from './client'
import { StudentSummary, StudentDetail } from '../types/student'
import { StudentProgressEntry, ProgressStatus } from '../types/progress'

export const studentsApi = {
  getStudents: async (search?: string, ageMin?: number, ageMax?: number): Promise<StudentSummary[]> => {
    const params = new URLSearchParams()
    if (search) params.append('search', search)
    if (ageMin) params.append('ageMin', ageMin.toString())
    if (ageMax) params.append('ageMax', ageMax.toString())
    
    const response = await apiClient.get<StudentSummary[]>(`/students?${params.toString()}`)
    return response.data
  },

  getStudentById: async (id: string): Promise<StudentDetail> => {
    const response = await apiClient.get<StudentDetail>(`/students/${id}`)
    return response.data
  },

  getStudentProgress: async (id: string): Promise<StudentProgressEntry[]> => {
    const response = await apiClient.get<StudentProgressEntry[]>(`/students/${id}/progress`)
    return response.data
  },

  updateStudentProgress: async (id: string, itemKey: string, status: ProgressStatus): Promise<StudentProgressEntry> => {
    const response = await apiClient.put<StudentProgressEntry>(`/students/${id}/progress`, { itemKey, status })
    return response.data
  },
}
