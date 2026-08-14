import { useEffect, useState } from 'react'
import { getMessages } from '../services/messageService'
import type { Message } from '../types/chat'

interface UseMessagesParams {
  accountId: number
  conversationId: number
  page?: number
  size?: number
}

export function useMessages({
  accountId,
  conversationId,
  page = 0,
  size = 10
}: UseMessagesParams) {
  const [messages, setMessages] = useState<Message[] | null>(null)
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)

  const fetchMessages = async () => {
    setLoading(true)
    setError(null)
    try {
      const res = await getMessages(accountId, conversationId, page, size)
      setMessages(res.data.messages)
    } catch (e) {
      setError('Failed to load messages')
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    fetchMessages()
  }, [accountId, conversationId, page, size])

  return {
    messages,
    loading,
    error,
    refetch: fetchMessages
  }
}
