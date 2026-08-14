import React, { useState } from 'react'
import {
  Plus,
  ChevronDown,
  MessageCircle,
  FileText,
  BookOpen,
  Shield
} from 'lucide-react'
import ConversationItem from './ConversationItem'

import { useConversations } from '../hooks/useConversations'
import { useCreateConversation } from '../hooks/useCreateConversation'

interface ChatSidebarProps {
  accountId: number
  currentConversationId: number
  onConversationChange: (conversationId: number) => void
  onNewConversation: () => void
}

const ChatSidebar: React.FC<ChatSidebarProps> = ({
  accountId,
  currentConversationId,
  onConversationChange,
  onNewConversation
}) => {
  const [activeSection, setActiveSection] = useState<string>('Lịch Sử Chat')
  const [expandedSections, setExpandedSections] = useState<string[]>([
    'Lịch Sử Chat'
  ])

  // Hooks for conversations
  const {
    conversations,
    loading: conversationsLoading,
    refetch
  } = useConversations({
    accountId,
    page: 0,
    size: 20
  })

  const { create: createConversation, loading: createLoading } =
    useCreateConversation(accountId)

  const sidebarSections = [
    {
      title: 'Văn Bản Pháp Luật',
      icon: <FileText className='h-5 w-5' />,
      items: ['Luật Lao Động', 'Luật BHXH', 'Luật Thuế TNCN']
    },
    {
      title: 'Soạn Hợp Đồng',
      icon: <BookOpen className='h-5 w-5' />,
      items: ['Mẫu Hợp Đồng']
    },
    {
      title: 'Lịch Sử Chat',
      icon: <MessageCircle className='h-5 w-5' />,
      items: [], // Sẽ được populate từ conversations
      isConversationList: true
    }
  ]

  const toggleSection = (sectionTitle: string) => {
    setActiveSection(sectionTitle)
    setExpandedSections((prev) =>
      prev.includes(sectionTitle)
        ? prev.filter((s) => s !== sectionTitle)
        : [...prev, sectionTitle]
    )
  }

  const handleNewChat = async () => {
    try {
      const newConversation = {
        parent_conv_id: conversations?.[0]?.conversation_id || 0,
        title: 'New Chat',
        status: 1
      }

      const result = await createConversation(newConversation)

      if (result && result.data && result.data.conversation) {
        const newConversationId = result.data.conversation.conversation_id

        // Đầu tiên gọi onNewConversation để set isNewConversation = true
        onNewConversation()

        // Sau đó cập nhật currentConversationId
        onConversationChange(newConversationId)

        // Cuối cùng refetch để cập nhật danh sách conversations
        await refetch()

        console.log('New conversation created with ID:', newConversationId)
      }
    } catch (error) {
      console.error('Lỗi tạo cuộc trò chuyện mới:', error)
    }
  }

  const handleConversationClick = (conversationId: number) => {
    onConversationChange(conversationId)
  }

  const formatConversationTitle = (title: string, maxLength: number = 25) => {
    return title.length > maxLength
      ? `${title.substring(0, maxLength)}...`
      : title
  }

  const formatDate = (dateString: string) => {
    const date = new Date(dateString)
    const now = new Date()
    const diffTime = Math.abs(now.getTime() - date.getTime())
    const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24))

    if (diffDays === 1) return 'Hôm nay'
    if (diffDays === 2) return 'Hôm qua'
    if (diffDays <= 7) return `${diffDays} ngày trước`
    return date.toLocaleDateString('vi-VN')
  }

  return (
    <div className='flex h-full w-80 flex-col border-r border-gray-200 bg-white shadow-xl'>
      {/* Header */}
      <div className='border-b border-gray-100 p-6'>
        <div className='mb-6 flex items-center justify-center'>
          <h1 className='text-2xl font-bold text-blue-900'>Juris AI</h1>
        </div>
        <button
          onClick={handleNewChat}
          disabled={createLoading}
          className='flex w-full items-center justify-center space-x-2 rounded-xl bg-gradient-to-r from-blue-600 to-blue-700 px-4 py-3 font-medium text-white shadow-md transition-all duration-200 hover:from-blue-700 hover:to-blue-800 hover:shadow-lg disabled:opacity-50'
        >
          {createLoading ? (
            <div className='h-5 w-5 animate-spin rounded-full border-2 border-white border-t-transparent' />
          ) : (
            <Plus className='h-5 w-5' />
          )}
          <span>Cuộc Trò Chuyện Mới</span>
        </button>
      </div>

      {/* Navigation Sections */}
      <div className='flex-1 space-y-3 overflow-y-auto p-4'>
        {sidebarSections.map((section, index) => {
          const isExpanded = expandedSections.includes(section.title)
          const isActive = activeSection === section.title

          return (
            <div
              key={index}
              className={`overflow-hidden rounded-xl ${isActive ? 'border border-blue-200 bg-blue-50' : 'bg-gray-50'}`}
            >
              <button
                onClick={() => toggleSection(section.title)}
                className={`flex w-full items-center justify-between p-4 transition-colors duration-200 ${
                  isActive
                    ? 'text-blue-700 hover:bg-blue-100'
                    : 'text-gray-800 hover:bg-gray-100'
                }`}
              >
                <div className='flex items-center space-x-3'>
                  <div className='text-blue-600'>{section.icon}</div>
                  <span className='font-medium'>{section.title}</span>
                </div>
                <ChevronDown
                  className={`h-5 w-5 transition-transform duration-200 ${
                    isExpanded ? 'rotate-180' : ''
                  } ${isActive ? 'text-blue-500' : 'text-gray-500'}`}
                />
              </button>

              {/* Items */}
              {isExpanded && (
                <div className='space-y-2 px-4 pb-2'>
                  {section.isConversationList ? (
                    // Render conversations
                    <>
                      {conversationsLoading ? (
                        <div className='flex items-center justify-center py-4'>
                          <div className='h-4 w-4 animate-spin rounded-full border-2 border-blue-600 border-t-transparent' />
                        </div>
                      ) : conversations && conversations.length > 0 ? (
                        conversations.map((conversation) => (
                          <ConversationItem
                            key={conversation.conversation_id}
                            conversation={conversation}
                            isActive={
                              conversation.conversation_id ===
                              currentConversationId
                            }
                            onClick={() =>
                              handleConversationClick(
                                conversation.conversation_id
                              )
                            }
                            accountId={accountId}
                            onDeleted={refetch}
                            formatTitle={formatConversationTitle}
                            formatDate={formatDate}
                          />
                        ))
                      ) : (
                        <div className='py-4 text-center text-sm text-gray-500'>
                          Chưa có cuộc trò chuyện nào
                        </div>
                      )}
                    </>
                  ) : (
                    // Render static items
                    section.items.map((item, itemIndex) => (
                      <button
                        key={itemIndex}
                        className='flex w-full items-center space-x-3 rounded-lg p-2 text-left text-gray-700 transition-all duration-200 hover:bg-white hover:text-blue-600 hover:shadow-sm'
                        onClick={() => console.log(`Clicked: ${item}`)}
                      >
                        <Shield className='h-4 w-4 text-blue-600' />
                        <span className='text-sm'>{item}</span>
                      </button>
                    ))
                  )}
                </div>
              )}
            </div>
          )
        })}
      </div>

      {/* Footer */}
      <div className='mt-auto border-t border-gray-100 bg-white p-4'>
        <div className='text-center'>
          <div className='mb-1 text-sm font-medium text-blue-600'>
            Một sản phẩm của
          </div>
          <div className='text-lg font-bold text-blue-900'>JurisAI</div>
        </div>
      </div>
    </div>
  )
}

export default ChatSidebar
