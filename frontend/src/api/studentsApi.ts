import apiClient from './client'
import { StudentSummary, StudentDetail } from '../types/student'

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
}

