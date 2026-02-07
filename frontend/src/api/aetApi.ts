import apiClient from './client'
import { AetDomain } from '../types/aet'

export const aetApi = {
  getDomains: async (): Promise<AetDomain[]> => {
    const response = await apiClient.get<AetDomain[]>('/aet/domains')
    return response.data
  },
}
