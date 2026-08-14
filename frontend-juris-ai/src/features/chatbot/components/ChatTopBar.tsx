import React from 'react'
import { Sun, Bell, User } from 'lucide-react'

const ChatTopBar: React.FC = () => {
  return (
    <div className='border-b border-gray-200 bg-white px-6 py-4 shadow-sm'>
      <div className='flex items-center justify-between'>
        <div className='flex items-center space-x-2'>
          <span className='rounded-full bg-blue-100 px-3 py-1 text-xs font-medium text-blue-800'>
            Juris Chat
          </span>
          <span className='rounded bg-gray-100 px-2 py-1 text-xs text-gray-600'>
            Version 1.0
          </span>
        </div>
        <div className='flex items-center space-x-4'>
          <button className='rounded-lg p-2 transition-colors duration-200 hover:bg-gray-100'>
            <Sun className='h-5 w-5 text-gray-600' />
          </button>
          <button className='rounded-lg p-2 transition-colors duration-200 hover:bg-gray-100'>
            <Bell className='h-5 w-5 text-gray-600' />
          </button>
          <button className='rounded-lg p-2 transition-colors duration-200 hover:bg-gray-100'>
            <User className='h-5 w-5 text-gray-600' />
          </button>
        </div>
      </div>
    </div>
  )
}

export default ChatTopBar
