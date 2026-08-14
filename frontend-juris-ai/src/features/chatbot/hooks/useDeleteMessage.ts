import { useState } from 'react'
import { deleteMessage } from '../services/messageService'

export function useDeleteMessage(
  accountId: number,
  conversationId: number,
  messageId: number
) {
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)

  const removeMessage = async (): Promise<void | null> => {
    setLoading(true)
    setError(null)
    try {
      await deleteMessage(accountId, conversationId, messageId)
    } catch (e) {
      setError('Failed to delete message')
      return null
    } finally {
      setLoading(false)
    }
  }

  return {
    loading,
    error,
    removeMessage
  }
}
