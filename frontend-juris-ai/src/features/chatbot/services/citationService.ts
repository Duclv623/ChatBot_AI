import { api } from '../../../lib/api'
import type { Citation } from '../types/chat'

const API_BASE_URL = 'http://localhost:9002' // Replace with your actual API base URL
const API_ENDPOINTS = {
  MESSAGE_SERVICE: '/v1/message-service/'
}

export const getCitations = async (
  accountId: number,
  messageId: number
): Promise<Citation[]> => {
  return api.get(
    `${API_BASE_URL}${API_ENDPOINTS.MESSAGE_SERVICE}${accountId}/citations/${messageId}`
  )
}
