import { useState } from 'react'
import { getMessage } from '../services/messageService'
import type { Message } from '../types/chat'

export function useMessage(
  accountId: number,
  conversationId: number,
  messageId: number
) {
  const [message, setMessage] = useState<Message | null>(null)
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)

  const fetchMessage = async () => {
    setLoading(true)
    setError(null)
    try {
      const res = await getMessage(accountId, conversationId, messageId)
      setMessage(res.data.message)
    } catch (e) {
      setError('Failed to load message')
    } finally {
      setLoading(false)
    }
  }

  return {
    message,
    loading,
    error,
    refetch: fetchMessage
  }
}
