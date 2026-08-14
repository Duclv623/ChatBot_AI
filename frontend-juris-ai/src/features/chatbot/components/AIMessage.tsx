import React, { useState } from 'react'
import { Copy, ThumbsUp, ThumbsDown, Globe, BookOpen } from 'lucide-react'
import { useUpdateFeedbackMessage } from '../hooks/useUpdateFeedbackMessage'
import { useDeleteMessage } from '../hooks/useDeleteMessage'
import type { Message } from '../types/chat'

interface AIMessageProps {
  message: Message
  accountId: number
  conversationId: number
  onFeedbackUpdate?: () => void
  onMessageDeleted?: () => void
}

// DetailedAIMessage Component
const AIMessage: React.FC<AIMessageProps> = ({
  message,
  accountId,
  conversationId,
  onFeedbackUpdate,
  onMessageDeleted
}) => {
  console.log(message)
  const [copied, setCopied] = useState(false)
  const { updateFeedback, loading: feedbackLoading } =
    useUpdateFeedbackMessage()
  const { removeMessage, loading: deleteLoading } = useDeleteMessage(
    accountId,
    conversationId,
    message.message_id
  )

  // Copy functionality
  const handleCopy = async () => {
    try {
      await navigator.clipboard.writeText(message.content || '')
      setCopied(true)
      setTimeout(() => setCopied(false), 2000)
    } catch (err) {
      console.error('Failed to copy text:', err)
    }
  }

  // Feedback functionality
  const handleFeedback = async (feedbackType: 'positive' | 'negative') => {
    // Giả sử feedbackId: 1 = positive, 2 = negative
    const feedbackId = feedbackType === 'positive' ? 1 : 2

    const result = await updateFeedback(
      accountId,
      conversationId,
      message.message_id,
      feedbackId
    )

    if (result) {
      onFeedbackUpdate?.()
    }
  }

  // Delete functionality (optional)
  const handleDelete = async () => {
    if (window.confirm('Bạn có chắc chắn muốn xóa tin nhắn này?')) {
      await removeMessage()
      onMessageDeleted?.()
    }
  }

  // Parse message content if it's structured
  const parseContent = () => {
    try {
      if (typeof message.content === 'string') {
        return JSON.parse(message.content)
      }
      return message.content
    } catch {
      return { mainText: message.content }
    }
  }

  const content = parseContent()

  return (
    <div className='max-w-2xl rounded-2xl border border-white/20 bg-white/95 p-6 shadow-lg backdrop-blur-sm'>
      {/* <div className='flex items-center space-x-3 mb-4'>
        <div className='w-8 h-8 bg-gradient-to-br from-blue-500 to-purple-600 rounded-full flex items-center justify-center shadow-sm'>
          <span className='text-white font-semibold text-sm'>AI</span>
        </div>
        <span className='text-gray-700 text-sm font-medium'>Juris AI</span>
      </div> */}

      <div className='space-y-4'>
        <div className='text-sm leading-relaxed text-gray-800'>
          {message.content || 'Không có nội dung'}
        </div>

        {message.citations && message.citations.length > 0 && (
          <div className='space-y-3'>
            <h5 className='text-sm font-medium text-gray-800'>Trích dẫn:</h5>
            <div className='space-y-2'>
              {message.citations.map((citation, index) => (
                <div
                  key={index}
                  className='flex items-start space-x-2 rounded-lg border border-blue-200/50 bg-blue-50/70 px-3 py-2 transition-shadow hover:shadow-sm'
                >
                  <div className='mt-0.5 flex h-5 w-5 flex-shrink-0 items-center justify-center rounded-full bg-blue-500 text-white shadow-sm'>
                    <span className='text-xs font-medium'>{index + 1}</span>
                  </div>
                  <div className='flex-1'>
                    <p className='text-sm font-medium text-blue-800'>
                      {citation.law_name}
                    </p>
                    {citation.chapter && (
                      <p className='text-xs text-blue-700'>
                        Chương {citation.chapter}
                      </p>
                    )}
                    {citation.section && (
                      <p className='text-xs text-blue-700'>
                        Mục {citation.section}
                      </p>
                    )}
                    {citation.article && (
                      <p className='text-xs text-blue-700'>
                        Điều {citation.article}
                      </p>
                    )}
                    {citation.content && (
                      <p className='mt-1 text-sm text-blue-800'>
                        {citation.content}
                      </p>
                    )}
                  </div>
                </div>
              ))}
            </div>
          </div>
        )}

        {/* <div className='border-t border-gray-200 pt-4'>
          <h5 className='font-medium text-gray-800 text-sm mb-3'>Kết quả tìm kiếm tương tự:</h5>
          <div className='space-y-2'>
            {content.searchResults.map((result, index) => (
              <div
                key={index}
                className='flex items-center justify-between bg-gray-50/70 hover:bg-gray-100/80 rounded-lg px-3 py-2 cursor-pointer transition-all duration-200 border border-gray-100 hover:border-gray-200 hover:shadow-sm'
              >
                <div className='flex items-center space-x-3'>
                  <div className='w-6 h-6 bg-gray-200 rounded flex items-center justify-center border border-gray-300'>
                    <result.icon className='text-blue-600 text-xs font-medium' />
                  </div>
                  <span className='text-gray-700 text-sm'>{result.text}</span>
                </div>
                <ChevronRight className='w-4 h-4 text-gray-400' />
              </div>
            ))}
          </div>
        </div> */}
      </div>

      {/* Feedback buttons */}
      <div className='mt-4 flex items-center justify-end space-x-1 border-t border-gray-200/70 pt-3'>
        <button
          onClick={handleCopy}
          className='group rounded-lg p-2 transition-colors duration-200 hover:bg-gray-100/70'
          title={copied ? 'Đã sao chép!' : 'Sao chép'}
          disabled={copied}
        >
          <Copy
            className={`h-4 w-4 ${copied ? 'text-green-600' : 'text-gray-500 group-hover:text-gray-700'}`}
          />
        </button>

        <button
          onClick={() => handleFeedback('positive')}
          className='group rounded-lg p-2 transition-colors duration-200 hover:bg-green-50'
          title='Hữu ích'
          disabled={feedbackLoading}
        >
          {feedbackLoading ? (
            <div className='h-4 w-4 animate-spin rounded-full border border-green-600 border-t-transparent' />
          ) : (
            <ThumbsUp
              className={`h-4 w-4 ${
                message.feed_back === 1
                  ? 'text-green-600'
                  : 'text-gray-500 group-hover:text-green-600'
              }`}
            />
          )}
        </button>

        <button
          onClick={() => handleFeedback('negative')}
          className='group rounded-lg p-2 transition-colors duration-200 hover:bg-red-50'
          title='Không hữu ích'
          disabled={feedbackLoading}
        >
          {feedbackLoading ? (
            <div className='h-4 w-4 animate-spin rounded-full border border-red-600 border-t-transparent' />
          ) : (
            <ThumbsDown
              className={`h-4 w-4 ${
                message.feed_back === 2
                  ? 'text-red-600'
                  : 'text-gray-500 group-hover:text-red-600'
              }`}
            />
          )}
        </button>
      </div>
    </div>
  )
}

export default AIMessage
