import apiClient from './client'
import { GroupSummary, GroupDetail, AddStudentsToGroupRequest, AddStudentsToGroupResponse, CompatibilitySummary } from '../types/group'

export const groupsApi = {
  getGroups: async (active?: boolean, focusDomainId?: string, ageMin?: number, ageMax?: number): Promise<GroupSummary[]> => {
    const params = new URLSearchParams()
    if (active !== undefined) params.append('active', active.toString())
    if (focusDomainId) params.append('focusDomainId', focusDomainId)
    if (ageMin) params.append('ageMin', ageMin.toString())
    if (ageMax) params.append('ageMax', ageMax.toString())
    
    const response = await apiClient.get<GroupSummary[]>(`/groups?${params.toString()}`)
    return response.data
  },

  getGroupById: async (id: string): Promise<GroupDetail> => {
    const response = await apiClient.get<GroupDetail>(`/groups/${id}`)
    return response.data
  },

  addStudentsToGroup: async (groupId: string, request: AddStudentsToGroupRequest): Promise<AddStudentsToGroupResponse> => {
    const response = await apiClient.post<AddStudentsToGroupResponse>(`/groups/${groupId}/students`, request)
    return response.data
  },

  getCompatibility: async (groupId: string): Promise<CompatibilitySummary> => {
    const response = await apiClient.get<CompatibilitySummary>(`/groups/${groupId}/compatibility`)
    return response.data
  },
}

