import React, { useState } from 'react'
import { Edit, Trash2, Check, X } from 'lucide-react'
import { useUpdateMessage } from '../hooks/useUpdateMessage'
import { useDeleteMessage } from '../hooks/useDeleteMessage'
import type { Message, MessageRequest } from '../types/chat'

interface UserMessageProps {
  message: Message
  accountId: number
  conversationId: number
  onMessageUpdated?: () => void
  onMessageDeleted?: () => void
}

const UserMessage: React.FC<UserMessageProps> = ({
  message,
  accountId,
  conversationId,
  onMessageUpdated,
  onMessageDeleted
}) => {
  const [isEditing, setIsEditing] = useState(false)
  const [editText, setEditText] = useState(message.content || '')

  const { update, loading: updateLoading } = useUpdateMessage(
    accountId,
    conversationId,
    message.message_id
  )
  const { removeMessage, loading: deleteLoading } = useDeleteMessage(
    accountId,
    conversationId,
    message.message_id
  )

  const handleEdit = () => {
    setIsEditing(true)
    setEditText(message.content || '')
  }

  const handleSaveEdit = async () => {
    if (editText.trim() && editText !== message.content) {
      const messageRequest: MessageRequest = {
        parent_messages_id: message.parent_messages_id,
        model_chat: 'default', // hoặc lấy từ context
        sender: 1, // user
        content: editText.trim(),
        file_uuid: message.file_uuid,
        status: 1
      }

      const result = await update(messageRequest)

      if (result) {
        setIsEditing(false)
        onMessageUpdated?.()
      }
    } else {
      setIsEditing(false)
    }
  }

  const handleCancelEdit = () => {
    setIsEditing(false)
    setEditText(message.content || '')
  }

  const handleDelete = async () => {
    if (window.confirm('Bạn có chắc chắn muốn xóa tin nhắn này?')) {
      await removeMessage()
      onMessageDeleted?.()
    }
  }

  const handleKeyPress = (e: React.KeyboardEvent<HTMLTextAreaElement>) => {
    if (e.key === 'Enter' && !e.shiftKey) {
      e.preventDefault()
      handleSaveEdit()
    }
    if (e.key === 'Escape') {
      handleCancelEdit()
    }
  }

  return (
    <div className='group relative max-w-md rounded-2xl bg-white/90 px-4 py-3 shadow-lg backdrop-blur-sm'>
      {isEditing ? (
        <div className='space-y-3'>
          <textarea
            value={editText}
            onChange={(e) => setEditText(e.target.value)}
            onKeyPress={handleKeyPress}
            className='w-full resize-none rounded-lg border border-gray-300 bg-transparent px-3 py-2 text-sm text-gray-800 focus:border-blue-500 focus:outline-none'
            rows={3}
            autoFocus
            disabled={updateLoading}
          />

          <div className='flex justify-end space-x-2'>
            <button
              onClick={handleCancelEdit}
              className='rounded-lg p-1.5 transition-colors duration-200 hover:bg-gray-100'
              disabled={updateLoading}
              title='Hủy'
            >
              <X className='h-4 w-4 text-gray-500' />
            </button>

            <button
              onClick={handleSaveEdit}
              className='rounded-lg p-1.5 transition-colors duration-200 hover:bg-green-100'
              disabled={updateLoading || !editText.trim()}
              title='Lưu'
            >
              {updateLoading ? (
                <div className='h-4 w-4 animate-spin rounded-full border border-green-600 border-t-transparent' />
              ) : (
                <Check className='h-4 w-4 text-green-600' />
              )}
            </button>
          </div>
        </div>
      ) : (
        <>
          <p className='text-sm text-gray-800'>{message.content}</p>

          {/* Action buttons - hiện khi hover */}
          <div className='absolute -top-2 -right-2 flex space-x-1 opacity-0 transition-opacity duration-200 group-hover:opacity-100'>
            <button
              onClick={handleEdit}
              className='rounded-full border border-gray-200 bg-white p-1.5 shadow-sm transition-colors duration-200 hover:bg-gray-50'
              title='Chỉnh sửa'
              disabled={deleteLoading}
            >
              <Edit className='h-3 w-3 text-gray-600' />
            </button>

            <button
              onClick={handleDelete}
              className='rounded-full border border-gray-200 bg-white p-1.5 shadow-sm transition-colors duration-200 hover:bg-red-50'
              title='Xóa'
              disabled={deleteLoading}
            >
              {deleteLoading ? (
                <div className='h-3 w-3 animate-spin rounded-full border border-red-600 border-t-transparent' />
              ) : (
                <Trash2 className='h-3 w-3 text-red-600' />
              )}
            </button>
          </div>
        </>
      )}
    </div>
  )
}
export default UserMessage
