import apiClient from './client'
import { GroupSummary, GroupDetail, AddStudentsToGroupRequest, AddStudentsToGroupResponse, CompatibilitySummary, CreateGroupRequest, CreateGroupResponse, UpdateGroupRequest } from '../types/group'

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

  createGroup: async (request: CreateGroupRequest): Promise<CreateGroupResponse> => {
    const payload = {
      name: request.name,
      ageRangeMin: request.ageRangeMin,
      ageRangeMax: request.ageRangeMax,
      description: request.description,
      active: true,
      ...(request.focusDomainId ? { focusDomain: { id: request.focusDomainId } } : {}),
      createdBy: { id: request.createdById },
    }
    const response = await apiClient.post<CreateGroupResponse>('/groups', payload)
    return response.data
  },

  updateGroup: async (id: string, request: UpdateGroupRequest): Promise<void> => {
    await apiClient.put(`/groups/${id}`, request)
  },

  deleteGroup: async (id: string): Promise<void> => {
    await apiClient.delete(`/groups/${id}`)
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
