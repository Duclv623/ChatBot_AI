import { api } from '../../../lib/api'
import type {
  MessageRequest,
  MessageResponse,
  MessagesResponse
} from '../types/chat'

const API_BASE_URL = 'http://localhost:9002' // Replace with your actual API base URL
const API_ENDPOINTS = {
  MESSAGE_SERVICE: '/v1/message-service/'
}

export const sendMessage = async (
  accountId: number,
  conversationId: number,
  message: MessageRequest
): Promise<MessageResponse> => {
  return api.post(
    `${API_BASE_URL}${API_ENDPOINTS.MESSAGE_SERVICE}${accountId}/conversations/${conversationId}/messages`,
    message
  )
}

export const getMessage = async (
  accountId: number,
  conversationId: number,
  messageId: number
): Promise<MessageResponse> => {
  return api.get(
    `${API_BASE_URL}${API_ENDPOINTS.MESSAGE_SERVICE}${accountId}/conversations/${conversationId}/messages/${messageId}`
  )
}

export const getMessages = async (
  accountId: number,
  conversationId: number,
  page = 0,
  size = 20
): Promise<MessagesResponse> => {
  return api.get(
    `${API_BASE_URL}${API_ENDPOINTS.MESSAGE_SERVICE}${accountId}/conversations/${conversationId}/messages`,
    {
      params: { page, size }
    }
  )
}

export const updateMessage = async (
  accountId: number,
  conversationId: number,
  messageId: number,
  message: MessageRequest
): Promise<MessageResponse> => {
  return api.patch(
    `${API_BASE_URL}${API_ENDPOINTS.MESSAGE_SERVICE}${accountId}/conversations/${conversationId}/messages/${messageId}`,
    message
  )
}

export const updateReview = async (
  accountId: number,
  conversationId: number,
  messageId: number,
  feedbackId: number
): Promise<MessageResponse> => {
  return api.put(
    `${API_BASE_URL}${API_ENDPOINTS.MESSAGE_SERVICE}${accountId}/conversations/${conversationId}/messages/${messageId}/feedback/${feedbackId}`
  )
}

export const deleteMessage = async (
  accountId: number,
  conversationId: number,
  messageId: number
): Promise<void> => {
  await api.delete(
    `${API_BASE_URL}${API_ENDPOINTS.MESSAGE_SERVICE}${accountId}/conversations/${conversationId}/messages/${messageId}`
  )
}
