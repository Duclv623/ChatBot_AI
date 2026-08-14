import type {
  AuthUser,
  LoginPayload,
  RegisterPayload,
  AuthResponse
} from '../types/auth'
import { api } from '../../../lib/api'
import axios from 'axios'

export const login = async (
  payload: LoginPayload,
  isTesting: boolean = true
): Promise<AuthResponse> => {
  if (isTesting) {
    return new Promise((resolve, reject) => {
      setTimeout(() => {
        if (
          payload.email === 'testuser@email.com' &&
          payload.password === 'juris@123'
        ) {
          resolve({
            user: { id: 1, name: 'testuser', email: 'testuser@email.com' },
            token: 'hardcoded-token-for-successful-login'
          })
        } else if (
          payload.email === 'erroruser' &&
          payload.password === 'errorpassword'
        ) {
          reject(new Error('Tên đăng nhập hoặc mật khẩu không đúng'))
        } else if (
          payload.email === 'networkerror' &&
          payload.password === 'networkpassword'
        ) {
          reject(
            new Error(
              'Có lỗi không mong muốn xảy ra trong quá trình đăng nhập.'
            )
          )
        } else {
          reject(new Error('Đăng nhập thất bại do lỗi không xác định.'))
        }
      }, 500)
    })
  } else {
    try {
      const response = await api.post<AuthResponse>('/auth/login', payload)
      return response.data
    } catch (error: any) {
      if (axios.isAxiosError(error)) {
        throw new Error(
          error.response?.data?.message || 'Đăng nhập thất bại từ máy chủ.'
        )
      } else if (error instanceof Error) {
        throw new Error(
          error.message ||
            'Có lỗi không mong muốn xảy ra trong quá trình đăng nhập.'
        )
      } else {
        throw new Error(
          'Có lỗi không xác định xảy ra trong quá trình đăng nhập.'
        )
      }
    }
  }
}

export const register = async (
  payload: RegisterPayload
): Promise<AuthResponse> => {
  try {
    const response = await api.post<AuthResponse>('/auth/register', payload)
    return response.data
  } catch (error) {
    if (axios.isAxiosError(error)) {
      throw new Error(
        error.response?.data?.message || 'Đăng ký thất bại từ máy chủ.'
      )
    } else if (error instanceof Error) {
      throw new Error(
        error.message ||
          'Có lỗi không mong muốn xảy ra trong quá trình đăng ký.'
      )
    } else {
      throw new Error('Có lỗi không xác định xảy ra trong quá trình đăng ký.')
    }
  }
}

export const fetchCurrentUser = async (
  isTesting: boolean = true
): Promise<AuthUser> => {
  if (isTesting) {
    return new Promise((resolve, reject) => {
      setTimeout(() => {
        const storedToken = localStorage.getItem('authToken')
        if (storedToken && storedToken.includes('hardcoded-token')) {
          resolve({
            id: 1,
            name: 'testuser',
            email: 'testuser@email.com'
          })
        } else {
          reject(new Error('Mock: Token không hợp lệ hoặc không có.'))
        }
      }, 200)
    })
  } else {
    try {
      const response = await api.get<AuthUser>('/auth/me')
      return response.data
    } catch (error) {
      if (axios.isAxiosError(error)) {
        throw new Error(
          error.response?.data?.message ||
            'Không thể lấy thông tin người dùng từ máy chủ.'
        )
      } else if (error instanceof Error) {
        throw new Error(
          error.message ||
            'Có lỗi không mong muốn xảy ra khi lấy thông tin người dùng.'
        )
      } else {
        throw new Error(
          'Có lỗi không xác định xảy ra khi lấy thông tin người dùng.'
        )
      }
    }
  }
}
