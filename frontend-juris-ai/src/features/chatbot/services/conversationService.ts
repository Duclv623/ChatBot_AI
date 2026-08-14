import { api } from '../../../lib/api'
import type {
  ConversationRequest,
  ConversationResponse,
  ConversationsResponse
} from '../types/chat'

const API_BASE_URL = 'http://localhost:9002' // Replace with your actual API base URL
const API_ENDPOINTS = {
  MESSAGE_SERVICE: '/v1/message-service/'
}

export const addConversation = async (
  accountId: number,
  conversation: ConversationRequest
): Promise<ConversationResponse> => {
  return api.post(
    `${API_BASE_URL}${API_ENDPOINTS.MESSAGE_SERVICE}${accountId}/conversations`,
    conversation
  )
}

export const getConversation = async (
  accountId: number,
  conversationId: number
): Promise<ConversationResponse> => {
  return api.get(
    `${API_BASE_URL}${API_ENDPOINTS.MESSAGE_SERVICE}${accountId}/conversations/${conversationId}`
  )
}

export const getConversations = async (
  accountId: number,
  page = 0,
  size = 4
): Promise<ConversationsResponse> => {
  return api.get(
    `${API_BASE_URL}${API_ENDPOINTS.MESSAGE_SERVICE}${accountId}/conversations`,
    {
      params: { page, size }
    }
  )
}

export const updateConversation = async (
  accountId: number,
  conversationId: number,
  conversation: ConversationRequest
): Promise<ConversationResponse> => {
  return api.patch(
    `${API_BASE_URL}${API_ENDPOINTS.MESSAGE_SERVICE}${accountId}/conversations/${conversationId}`,
    conversation
  )
}

export const deleteConversation = async (
  accountId: number,
  conversationId: number
): Promise<void> => {
  await api.delete(
    `${API_BASE_URL}${API_ENDPOINTS.MESSAGE_SERVICE}${accountId}/conversations/${conversationId}`
  )
}
