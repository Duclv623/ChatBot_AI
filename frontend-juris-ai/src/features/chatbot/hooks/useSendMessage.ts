import { useState } from 'react'
import { sendMessage } from '../services/messageService'
import type { MessageRequest, MessageResponse } from '../types/chat'

export function useSendMessage(accountId: number, conversationId: number) {
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)

  const send = async (
    message: MessageRequest
  ): Promise<MessageResponse | null> => {
    setLoading(true)
    setError(null)
    try {
      const response = await sendMessage(accountId, conversationId, message)
      return response
    } catch (err) {
      setError('Gửi tin nhắn thất bại.')
      return null
    } finally {
      setLoading(false)
    }
  }

  return {
    send,
    loading,
    error
  }
}
