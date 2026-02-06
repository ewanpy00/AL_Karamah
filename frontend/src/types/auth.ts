export enum Role {
  TEACHER = 'TEACHER',
  THERAPIST = 'THERAPIST',
  ADMIN = 'ADMIN'
}

export interface User {
  id: string
  fullName: string
  email: string
  role: Role
}

export interface LoginRequest {
  email: string
  password: string
}

export interface LoginResponse {
  accessToken: string
  user: User
}

