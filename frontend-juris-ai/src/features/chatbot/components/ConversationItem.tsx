import React, { useState } from 'react'
import { MessageCircle, Trash2 } from 'lucide-react'
import { useDeleteConversation } from '../hooks/useDeleteConversation'
import type { Conversation } from '../types/chat'

interface ConversationItemProps {
  conversation: Conversation // Replace with proper Conversation type
  isActive: boolean
  onClick: () => void
  accountId: number
  onDeleted: () => void
  formatTitle: (title: string) => string
  formatDate: (date: string) => string
}

const ConversationItem: React.FC<ConversationItemProps> = ({
  conversation,
  isActive,
  onClick,
  accountId,
  onDeleted,
  formatTitle
}) => {
  const [showDelete, setShowDelete] = useState(false)
  const { removeConversation, loading: deleteLoading } = useDeleteConversation(
    accountId,
    conversation.conversation_id
  )

  const handleDelete = async (e: React.MouseEvent) => {
    e.stopPropagation()
    if (window.confirm('Bạn có chắc chắn muốn xóa cuộc trò chuyện này?')) {
      await removeConversation()
      onDeleted()
    }
  }

  return (
    <div
      className={`group relative rounded-lg transition-all duration-200 ${
        isActive
          ? 'border border-blue-300 bg-blue-100'
          : 'hover:bg-white hover:shadow-sm'
      }`}
      onMouseEnter={() => setShowDelete(true)}
      onMouseLeave={() => setShowDelete(false)}
    >
      <button
        onClick={onClick}
        className='flex w-full items-start space-x-3 p-3 text-left'
      >
        <MessageCircle
          className={`mt-0.5 h-4 w-4 flex-shrink-0 ${
            isActive ? 'text-blue-600' : 'text-gray-500'
          }`}
        />
        <div className='min-w-0 flex-1'>
          <div
            className={`truncate text-sm font-medium ${
              isActive ? 'text-blue-800' : 'text-gray-700'
            }`}
          >
            {formatTitle(conversation.title)}
          </div>
        </div>
      </button>

      {/* Delete button */}
      {showDelete && !isActive && (
        <button
          onClick={handleDelete}
          disabled={deleteLoading}
          className='absolute top-2 right-2 rounded-full p-1 transition-colors duration-200 hover:bg-red-100'
          title='Xóa cuộc trò chuyện'
        >
          {deleteLoading ? (
            <div className='h-3 w-3 animate-spin rounded-full border border-red-600 border-t-transparent' />
          ) : (
            <Trash2 className='h-3 w-3 text-red-600' />
          )}
        </button>
      )}
    </div>
  )
}

export default ConversationItem
