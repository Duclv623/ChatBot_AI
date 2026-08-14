import axios from 'axios'
import type {
  AxiosError,
  AxiosInstance,
  InternalAxiosRequestConfig
} from 'axios'

const API_BASE_URL =
  import.meta.env.VITE_API_BASE_URL || 'http://localhost:5000/api'

const api = axios.create({
  baseURL: API_BASE_URL,
  timeout: 30000,
  headers: {
    'Content-Type': 'application/json'
  }
}) as AxiosInstance

// Request interceptor to add auth token
api.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    const token = localStorage.getItem('authToken')
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  (error: AxiosError) => {
    return Promise.reject(error)
  }
)

// Response interceptor to handle common errors and token refresh
api.interceptors.response.use(
  (response) => {
    return response.data
  },
  async (error: AxiosError) => {
    const originalRequest = error.config as InternalAxiosRequestConfig & {
      _retry?: boolean
    }

    // Handle 401 Unauthorized
    if (error.response?.status === 401) {
      if (!originalRequest._retry) {
        originalRequest._retry = true

        try {
          // Try to refresh token
          const refreshToken = localStorage.getItem('refreshToken')
          if (refreshToken) {
            const response = await api.post('/auth/refresh', { refreshToken })
            const { accessToken, newRefreshToken } = response.data

            localStorage.setItem('authToken', accessToken)
            localStorage.setItem('refreshToken', newRefreshToken)

            // Retry original request with new token
            originalRequest.headers.Authorization = `Bearer ${accessToken}`
            return api(originalRequest)
          }
        } catch (refreshError) {
          // If refresh fails, log out
          localStorage.removeItem('authToken')
          localStorage.removeItem('refreshToken')
          window.location.href = '/login'
          return Promise.reject(refreshError)
        }
      }

      // If retry already attempted, log out
      localStorage.removeItem('authToken')
      localStorage.removeItem('refreshToken')
      window.location.href = '/login'
    }

    // Handle other common errors
    if (error.response?.status === 403) {
      console.error('Access forbidden:', error.response.data)
    } else if (error.response?.status === 404) {
      console.error('Resource not found:', error.response.data)
    } else if (error.response?.status === 500) {
      console.error('Server error:', error.response.data)
    }

    return Promise.reject(error)
  }
)

export { api }
