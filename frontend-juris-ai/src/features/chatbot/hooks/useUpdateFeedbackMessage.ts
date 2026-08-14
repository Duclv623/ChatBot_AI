import { useState } from 'react'
import { updateReview } from '../services/messageService'
import type { Message } from '../types/chat'

export function useUpdateFeedbackMessage() {
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)

  const updateFeedback = async (
    accountId: number,
    conversationId: number,
    messageId: number,
    feedbackId: number
  ): Promise<Message | null> => {
    setLoading(true)
    setError(null)

    try {
      const res = await updateReview(
        accountId,
        conversationId,
        messageId,
        feedbackId
      )
      return res.data.message
    } catch (e) {
      setError('Failed to update message feedback')
      return null
    } finally {
      setLoading(false)
    }
  }

  return {
    loading,
    error,
    updateFeedback
  }
}
