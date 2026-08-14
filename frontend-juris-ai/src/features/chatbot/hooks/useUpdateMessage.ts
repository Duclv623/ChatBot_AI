import { useState } from 'react'
import { updateMessage } from '../services/messageService'
import type { MessageRequest, Message } from '../types/chat'

export function useUpdateMessage(
  accountId: number,
  conversationId: number,
  messageId: number
) {
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)

  const update = async (message: MessageRequest): Promise<Message | null> => {
    setLoading(true)
    setError(null)
    try {
      message.status = 1
      const res = await updateMessage(
        accountId,
        conversationId,
        messageId,
        message
      )
      return res.data.message
    } catch (e) {
      setError('Failed to update message')
      return null
    } finally {
      setLoading(false)
    }
  }

  return {
    loading,
    error,
    update
  }
}
