import { useState } from 'react'
import { updateConversation } from '../services/conversationService'
import type { Conversation, ConversationRequest } from '../types/chat'

export function useUpdateConversation(
  accountId: number,
  conversationId: number
) {
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)

  const update = async (
    conversation: ConversationRequest
  ): Promise<Conversation | null> => {
    setLoading(true)
    setError(null)
    try {
      const res = await updateConversation(
        accountId,
        conversationId,
        conversation
      )
      return res.data.conversation
    } catch (e) {
      setError('Failed to update conversation')
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
