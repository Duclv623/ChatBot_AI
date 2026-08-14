import type { Citation } from './citation'

export interface Message {
  message_id: number
  parent_messages_id: number
  index_messages: number
  sender: number
  content: string
  file_uuid: number
  status: number
  feed_back: number
  num_citation: number
  created_at: number
  updated_at: number
  citations?: Citation[]
}

export interface SendMessageParams {
  accountId: number | string
  conversationId: number | string
  message: MessageRequest
}

export interface MessageRequest {
  parent_messages_id: number
  model_chat: string
  sender: number
  content: string
  file_uuid: number
  status: number
}

export interface MessageResponse {
  data: {
    account_id: number
    conversations_id: number
    child_message_id: number
    model_chat: string
    message: Message
    citations?: Citation[]
  }
  message: string
  success: boolean
}

export interface MessagesResponse {
  data: {
    account_id: number
    conversations_id: number
    model_chat: string
    messages: Message[]
    pagination: {
      total_pages: number
      total_elements: number
      current_page: number
      page_size: number
    }
  }
  message: string
  success: boolean
}
