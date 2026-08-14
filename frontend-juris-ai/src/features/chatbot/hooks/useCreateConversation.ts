import { useState } from 'react'
import { addConversation } from '../services/conversationService'
import type { ConversationRequest, ConversationResponse } from '../types/chat'

export function useCreateConversation(accountId: number) {
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)

  const create = async (
    conversation: ConversationRequest
  ): Promise<ConversationResponse | null> => {
    setLoading(true)
    setError(null)
    try {
      const response = await addConversation(accountId, conversation)
      return response
    } catch (err) {
      setError('Tạo cuộc trò chuyện thất bại.')
      return null
    } finally {
      setLoading(false)
    }
  }

  return {
    create,
    loading,
    error
  }
}
