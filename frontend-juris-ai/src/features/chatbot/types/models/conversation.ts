export interface Conversation {
  conversation_id: number
  parent_conv_id: number
  title: string
  status: number
  created_at: number
  updated_at: number
}

export interface ConversationRequest {
  title: string
  parent_conv_id: number
  status: number
}

export interface ConversationsResponse {
  data: {
    account_id: number
    conversations: Conversation[]
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

export interface ConversationResponse {
  data: {
    account_id: number
    conversation: Conversation
  }
  message: string
  success: boolean
}
