import { useEffect, useState } from 'react'
import { getConversations } from '../services/conversationService'
import type { Conversation } from '../types/chat'

interface UseConversationsParams {
  accountId: number
  page?: number
  size?: number
}

export function useConversations({
  accountId,
  page = 0,
  size = 10
}: UseConversationsParams) {
  const [conversations, setConversations] = useState<Conversation[] | null>(
    null
  )
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)

  const fetchConversations = async () => {
    setLoading(true)
    setError(null)
    try {
      const res = await getConversations(accountId, page, size)

      console.log(res)
      setConversations(res.data.conversations)
    } catch (e) {
      setError('Failed to load conversations')
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    fetchConversations()
  }, [accountId, page, size])

  return {
    conversations,
    loading,
    error,
    refetch: fetchConversations
  }
}
