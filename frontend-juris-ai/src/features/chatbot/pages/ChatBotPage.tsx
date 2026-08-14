import React, { useState, useCallback } from 'react'
import ChatSidebar from '../components/ChatSidebar'
import ChatTopBar from '../components/ChatTopBar'
import ChatMessages from '../components/ChatMessages'
import ChatInput from '../components/ChatInput'

interface ChatBotPageProps {
  accountId?: number
  initialConversationId?: number
}

const ChatBotPage: React.FC<ChatBotPageProps> = ({
  accountId = 1,
  initialConversationId = 1
}) => {
  const [currentConversationId, setCurrentConversationId] = useState(
    initialConversationId
  )
  const [refreshKey, setRefreshKey] = useState(0)
  const [isNewConversation, setIsNewConversation] = useState(false)

  // Callback để force refresh messages khi cần
  const handleRefreshMessages = useCallback(() => {
    setRefreshKey((prev) => prev + 1)
    // Khi có message mới, không còn là new conversation
    setIsNewConversation(false)
  }, [])

  // Callback khi chuyển conversation
  const handleConversationChange = useCallback((conversationId: number) => {
    console.log('Changing to conversation ID:', conversationId)
    setCurrentConversationId(conversationId)
    setRefreshKey((prev) => prev + 1) // Force refresh messages
    setIsNewConversation(false) // Reset new conversation state
  }, [])

  // Callback khi tạo conversation mới
  const handleNewConversation = useCallback(() => {
    console.log('Creating new conversation')
    setIsNewConversation(true)
    setRefreshKey((prev) => prev + 1) // Force refresh to show empty state
  }, [])

  // Debug log để kiểm tra currentConversationId
  console.log('Current conversation ID in ChatBotPage:', currentConversationId)

  return (
    <div className='flex h-screen bg-gray-50'>
      <ChatSidebar
        accountId={accountId}
        currentConversationId={currentConversationId}
        onConversationChange={handleConversationChange}
        onNewConversation={handleNewConversation}
      />

      <div className='flex flex-1 flex-col bg-gradient-to-br from-purple-600 via-blue-600 to-blue-700'>
        <ChatTopBar />
        <ChatMessages
          key={`${refreshKey}-${currentConversationId}`}
          accountId={accountId}
          conversationId={currentConversationId}
          forceEmpty={isNewConversation}
        />
        <ChatInput
          key={
            isNewConversation ? `new-${refreshKey}` : `existing-${refreshKey}`
          }
          accountId={accountId}
          conversationId={currentConversationId}
          onMessageSent={handleRefreshMessages}
          clearOnNewConversation={isNewConversation}
        />
      </div>
    </div>
  )
}

export default ChatBotPage
