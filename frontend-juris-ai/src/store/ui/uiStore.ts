import { create } from 'zustand'

interface UIState {
  theme: 'light' | 'dark'
  language: 'vi' | 'en'
  isLoading: boolean
  setTheme: (theme: 'light' | 'dark') => void
  setLanguage: (lang: 'vi' | 'en') => void
  setLoading: (loading: boolean) => void
}

export const useUIStore = create<UIState>((set) => ({
  theme: 'light',
  language: 'vi',
  isLoading: false,
  setTheme: (theme) => set({ theme }),
  setLanguage: (language) => set({ language }),
  setLoading: (isLoading) => set({ isLoading })
}))
