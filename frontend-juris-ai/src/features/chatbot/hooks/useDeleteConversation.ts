import { useState } from 'react'
import { deleteConversation } from '../services/conversationService'

export function useDeleteConversation(
  accountId: number,
  conversationId: number
) {
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)

  const removeConversation = async (): Promise<void | null> => {
    setLoading(true)
    setError(null)
    try {
      await deleteConversation(accountId, conversationId)
    } catch (e) {
      setError('Failed to delete conversation')
      return null
    } finally {
      setLoading(false)
    }
  }

  return {
    loading,
    error,
    removeConversation
  }
}
