import React, { useEffect } from 'react'
import { Send, Paperclip } from 'lucide-react'
import { useSendMessage } from '../hooks/useSendMessage'

interface ChatInputProps {
  accountId: number
  conversationId: number
  onMessageSent?: () => void // Callback để refresh messages
  clearOnNewConversation?: boolean // Prop để clear input khi tạo conversation mới
}

const ChatInput: React.FC<ChatInputProps> = ({
  accountId,
  conversationId,
  onMessageSent,
  clearOnNewConversation = false
}) => {
  const [inputValue, setInputValue] = React.useState('')
  const { send, loading, error } = useSendMessage(accountId, conversationId)

  // Clear input khi tạo conversation mới
  useEffect(() => {
    if (clearOnNewConversation) {
      setInputValue('')
    }
  }, [clearOnNewConversation])

  const handleSubmit = async () => {
    if (inputValue.trim() && !loading) {
      const messageRequest = {
        parent_messages_id: 0,
        content: inputValue.trim(),
        sender: 1,
        file_uuid: 0,
        model_chat: 'juris-ai',
        status: 1
      }

      const result = await send(messageRequest)

      if (result) {
        setInputValue('')
        onMessageSent?.() // Gọi callback để refresh danh sách messages
      }
    }
  }

  const handleKeyPress = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === 'Enter' && !e.shiftKey) {
      e.preventDefault()
      handleSubmit()
    }
  }

  return (
    <div className='p-6'>
      <div className='mx-auto max-w-4xl'>
        {error && (
          <div className='mb-4 rounded-lg border border-red-300 bg-red-100 p-3 text-sm text-red-700'>
            {error}
          </div>
        )}
        <div className='flex items-center space-x-4 rounded-2xl border border-gray-200 bg-white p-2 shadow-lg'>
          <button
            type='button'
            className='rounded-lg p-2 transition-colors duration-200 hover:bg-gray-100'
          >
            <Paperclip className='h-5 w-5 text-gray-600' />
          </button>
          <input
            type='text'
            value={inputValue}
            onChange={(e) => setInputValue(e.target.value)}
            onKeyPress={handleKeyPress}
            placeholder='Nhập câu hỏi của bạn tại đây...'
            disabled={loading}
            className='flex-1 border-none bg-transparent px-2 py-3 text-sm text-gray-800 placeholder-gray-400 outline-none focus:placeholder-gray-300'
          />
          <button
            onClick={handleSubmit}
            disabled={loading || !inputValue.trim()}
            className={`rounded-xl p-3 shadow-md transition-colors duration-200 ${
              loading || !inputValue.trim()
                ? 'cursor-not-allowed bg-gray-400'
                : 'bg-blue-600 hover:bg-blue-700'
            } text-white`}
          >
            {loading ? (
              <div className='h-5 w-5 animate-spin rounded-full border-2 border-white border-t-transparent' />
            ) : (
              <Send className='h-5 w-5' />
            )}
          </button>
        </div>
      </div>
    </div>
  )
}
export default ChatInput
