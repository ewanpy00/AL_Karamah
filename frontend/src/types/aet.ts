export interface AetDomain {
  id: string
  code: string
  name: string
  description?: string
}

export interface AetStatement {
  id: string
  domainId: string
  code: string
  description: string
  ageRangeMin?: number
  ageRangeMax?: number
  level?: string
}

