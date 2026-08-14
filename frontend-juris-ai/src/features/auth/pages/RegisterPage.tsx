import React, { useState } from 'react'
import { Eye, EyeOff, Mail, Lock, User, Phone } from 'lucide-react'
import { useNavigate } from 'react-router-dom'

const RegisterPage = () => {
  const [showPassword, setShowPassword] = useState(false)
  const [showConfirmPassword, setShowConfirmPassword] = useState(false)
  const navigate = useNavigate()

  return (
    <div className='flex min-h-screen items-center justify-center bg-gradient-to-br from-purple-600 via-blue-600 to-indigo-700 p-4'>
      <div className='w-full max-w-md rounded-2xl bg-white px-8 py-4 shadow-2xl'>
        <div className='mb-4 text-center'>
          <h1 className='mb-2 text-3xl font-bold text-blue-900'>JurisAI</h1>
        </div>

        <div className='space-y-3'>
          <div>
            <label className='mb-2 block text-sm font-medium text-gray-700'>
              Họ và tên
            </label>
            <div className='relative'>
              <User className='absolute top-1/2 left-3 h-5 w-5 -translate-y-1/2 transform text-gray-400' />
              <input
                type='text'
                name='name'
                className='w-full rounded-lg border border-gray-300 py-3 pr-4 pl-10 transition-colors focus:border-transparent focus:ring-2 focus:ring-purple-500'
                placeholder='Nhập họ và tên'
              />
            </div>
          </div>

          <div>
            <label className='mb-2 block text-sm font-medium text-gray-700'>
              Email
            </label>
            <div className='relative'>
              <Mail className='absolute top-1/2 left-3 h-5 w-5 -translate-y-1/2 transform text-gray-400' />
              <input
                type='email'
                name='email'
                className='w-full rounded-lg border border-gray-300 py-3 pr-4 pl-10 transition-colors focus:border-transparent focus:ring-2 focus:ring-purple-500'
                placeholder='Nhập email của bạn'
              />
            </div>
          </div>

          <div>
            <label className='mb-2 block text-sm font-medium text-gray-700'>
              Số điện thoại
            </label>
            <div className='relative'>
              <Phone className='absolute top-1/2 left-3 h-5 w-5 -translate-y-1/2 transform text-gray-400' />
              <input
                type='tel'
                name='phone'
                className='w-full rounded-lg border border-gray-300 py-3 pr-4 pl-10 transition-colors focus:border-transparent focus:ring-2 focus:ring-purple-500'
                placeholder='Nhập số điện thoại'
              />
            </div>
          </div>

          <div>
            <label className='mb-2 block text-sm font-medium text-gray-700'>
              Mật khẩu
            </label>
            <div className='relative'>
              <Lock className='absolute top-1/2 left-3 h-5 w-5 -translate-y-1/2 transform text-gray-400' />
              <input
                type={showPassword ? 'text' : 'password'}
                name='password'
                className='w-full rounded-lg border border-gray-300 py-3 pr-12 pl-10 transition-colors focus:border-transparent focus:ring-2 focus:ring-purple-500'
                placeholder='Nhập mật khẩu'
              />
              <button
                type='button'
                onClick={() => setShowPassword(!showPassword)}
                className='absolute top-1/2 right-3 -translate-y-1/2 transform text-gray-400 hover:text-gray-600'
              >
                {showPassword ? (
                  <EyeOff className='h-5 w-5' />
                ) : (
                  <Eye className='h-5 w-5' />
                )}
              </button>
            </div>
          </div>

          <div>
            <label className='mb-2 block text-sm font-medium text-gray-700'>
              Xác nhận mật khẩu
            </label>
            <div className='relative'>
              <Lock className='absolute top-1/2 left-3 h-5 w-5 -translate-y-1/2 transform text-gray-400' />
              <input
                type={showConfirmPassword ? 'text' : 'password'}
                name='confirmPassword'
                className='w-full rounded-lg border border-gray-300 py-3 pr-12 pl-10 transition-colors focus:border-transparent focus:ring-2 focus:ring-purple-500'
                placeholder='Nhập lại mật khẩu'
              />
              <button
                type='button'
                onClick={() => setShowConfirmPassword(!showConfirmPassword)}
                className='absolute top-1/2 right-3 -translate-y-1/2 transform text-gray-400 hover:text-gray-600'
              >
                {showConfirmPassword ? (
                  <EyeOff className='h-5 w-5' />
                ) : (
                  <Eye className='h-5 w-5' />
                )}
              </button>
            </div>
          </div>

          <div>
            <label className='flex items-start space-x-2'>
              <input
                type='checkbox'
                className='mt-1 h-4 w-4 rounded border-gray-300 text-purple-600 focus:ring-purple-500'
              />
              <span className='text-sm text-gray-600'>
                Tôi đồng ý với{' '}
                <a href='#' className='text-purple-600 hover:text-purple-800'>
                  Điều khoản sử dụng
                </a>{' '}
                và{' '}
                <a href='#' className='text-purple-600 hover:text-purple-800'>
                  Chính sách bảo mật
                </a>
              </span>
            </label>
          </div>

          <button
            type='button'
            className='w-full rounded-lg bg-gradient-to-r from-purple-600 to-blue-600 px-4 py-3 font-medium text-white transition-all duration-200 hover:from-purple-700 hover:to-blue-700 focus:ring-2 focus:ring-purple-500 focus:ring-offset-2'
          >
            Đăng ký
          </button>
        </div>

        <div className='mt-4 text-center'>
          <p className='text-gray-600'>
            Đã có tài khoản?{' '}
            <button
              className='font-medium text-purple-600 hover:text-purple-800'
              onClick={() => navigate('/login')}
            >
              Đăng nhập ngay
            </button>
          </p>
        </div>
      </div>
    </div>
  )
}

export default RegisterPage
