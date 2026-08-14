import React from 'react'
import UserMessage from './UserMessage'
import AIMessage from './AIMessage'

import { useMessages } from '../hooks/useMessages'

interface ChatMessagesProps {
  accountId: number
  conversationId: number
  page?: number
  size?: number
  forceEmpty?: boolean
}

const ChatMessages: React.FC<ChatMessagesProps> = ({
  accountId,
  conversationId,
  page = 0,
  size = 20,
  forceEmpty = false
}) => {
  const { messages, loading, error, refetch } = useMessages({
    accountId,
    conversationId,
    page,
    size
  })
  // Loading state
  if (loading && !messages) {
    return (
      <div className='flex flex-1 items-center justify-center overflow-y-auto p-6'>
        <div className='flex items-center space-x-2 text-white/70'>
          <div className='h-4 w-4 animate-spin rounded-full border-2 border-white/50 border-t-white' />
          <span>Đang tải tin nhắn...</span>
        </div>
      </div>
    )
  }
  // Error state
  if (error) {
    return (
      <div className='flex flex-1 items-center justify-center overflow-y-auto p-6'>
        <div className='text-center'>
          <div className='mb-4 text-white/70'>{error}</div>
          <button
            onClick={refetch}
            className='rounded-lg bg-white/20 px-4 py-2 text-white transition-colors hover:bg-white/30'
          >
            Thử lại
          </button>
        </div>
      </div>
    )
  }

  // No messages state
  if (forceEmpty || !messages || messages.length === 0) {
    return (
      <div className='flex flex-1 items-center justify-center overflow-y-auto p-6'>
        <div className='mx-auto max-w-3xl text-center'>
          {/* Main container with solid background */}
          <div className='rounded-3xl bg-white p-6 shadow-2xl md:p-8'>
            {/* Main heading */}
            <h1 className='mb-4 text-3xl font-bold tracking-tight text-gray-700 md:text-4xl'>
              Chào mừng đến với <span className='text-blue-500'>Juris AI</span>
            </h1>

            {/* Description */}
            <p className='mb-6 text-xl leading-relaxed font-medium text-gray-600 md:text-2xl'>
              Trợ lý AI chuyên nghiệp, tư vấn về các thắc mắc pháp luật
            </p>

            {/* Specialties */}
            <div className='mb-6 grid grid-cols-1 gap-6 md:grid-cols-3'>
              <div className='transform rounded-2xl border border-blue-200 bg-gradient-to-br from-blue-50 to-blue-100 p-6 transition-all duration-300 hover:scale-105 hover:from-blue-100 hover:to-blue-200'>
                <div className='mx-auto mb-4 flex h-14 w-14 items-center justify-center rounded-xl bg-gradient-to-br from-blue-500 to-blue-600'>
                  <span className='text-2xl text-white'>⚖️</span>
                </div>
                <h3 className='mb-3 text-lg font-bold text-gray-800'>
                  Luật Lao Động
                </h3>
                <p className='text-base leading-relaxed text-gray-600'>
                  Hợp đồng, nghỉ phép, thôi việc, quyền lợi người lao động
                </p>
              </div>

              <div className='transform rounded-2xl border border-green-200 bg-gradient-to-br from-green-50 to-green-100 p-6 transition-all duration-300 hover:scale-105 hover:from-green-100 hover:to-green-200'>
                <div className='mx-auto mb-4 flex h-14 w-14 items-center justify-center rounded-xl bg-gradient-to-br from-green-500 to-green-600'>
                  <span className='text-2xl text-white'>🏥</span>
                </div>
                <h3 className='mb-3 text-lg font-bold text-gray-800'>
                  Bảo Hiểm XH
                </h3>
                <p className='text-base leading-relaxed text-gray-600'>
                  BHXH, BHYT, thủ tục hưu trí, trợ cấp thất nghiệp
                </p>
              </div>

              <div className='transform rounded-2xl border border-purple-200 bg-gradient-to-br from-purple-50 to-purple-100 p-6 transition-all duration-300 hover:scale-105 hover:from-purple-100 hover:to-purple-200'>
                <div className='mx-auto mb-4 flex h-14 w-14 items-center justify-center rounded-xl bg-gradient-to-br from-purple-500 to-purple-600'>
                  <span className='text-2xl text-white'>💰</span>
                </div>
                <h3 className='mb-3 text-lg font-bold text-gray-800'>
                  Thuế TNCN
                </h3>
                <p className='text-base leading-relaxed text-gray-600'>
                  Tính thuế, giảm trừ, quyết toán thuế thu nhập cá nhân
                </p>
              </div>
            </div>

            {/* Call to action */}
            <p className='mt-4 text-lg font-medium text-gray-500'>
              Hãy bắt đầu cuộc trò chuyện bằng cách nhập câu hỏi của bạn bên
              dưới
            </p>
          </div>
        </div>
      </div>
    )
  }

  return (
    <div className='flex-1 overflow-y-auto p-6'>
      <div className='mx-auto max-w-4xl space-y-6'>
        {messages.map((message) => (
          <div
            key={message.message_id}
            className={`flex ${message.sender === 1 ? 'justify-end' : 'justify-start'}`}
          >
            {message.sender === 1 ? (
              <UserMessage
                message={message}
                accountId={accountId}
                conversationId={conversationId}
              />
            ) : (
              <AIMessage
                message={message}
                accountId={accountId}
                conversationId={conversationId}
                onFeedbackUpdate={refetch}
              />
            )}
          </div>
        ))}

        {/* Loading indicator for pagination */}
        {loading && messages && (
          <div className='flex justify-center py-4'>
            <div className='h-4 w-4 animate-spin rounded-full border-2 border-white/50 border-t-white' />
          </div>
        )}
      </div>
    </div>
  )
}
export default ChatMessages
