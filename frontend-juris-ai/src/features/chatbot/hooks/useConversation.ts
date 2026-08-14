import { useState } from 'react'
import { getConversation } from '../services/conversationService'
import type { Conversation } from '../types/chat'

export function useConversation(accountId: number, conversationId: number) {
  const [conversation, setConversation] = useState<Conversation | null>(null)
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)

  const fetchConversation = async () => {
    setLoading(true)
    setError(null)
    try {
      const res = await getConversation(accountId, conversationId)
      setConversation(res.data.conversation)
    } catch (e) {
      setError('Failed to load conversation')
    } finally {
      setLoading(false)
    }
  }

  return {
    conversation,
    loading,
    error,
    refetch: fetchConversation
  }
}
