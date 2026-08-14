// app/components/common/Button/Button.tsx
import React from 'react'

// Định nghĩa Props cho Button component
interface ButtonProps extends React.ButtonHTMLAttributes<HTMLButtonElement> {
  variant?: 'primary' | 'secondary' | 'danger' | 'text'
  size?: 'small' | 'medium' | 'large'
  isLoading?: boolean // Cho trạng thái loading
}

const Button: React.FC<ButtonProps> = ({
  children,
  className,
  variant = 'primary', // Default variant
  size = 'medium', // Default size
  isLoading = false,
  disabled,
  ...props
}) => {
  // Base classes áp dụng cho tất cả các button
  const baseClasses = `
    inline-flex items-center justify-center
    font-semibold rounded-md cursor-pointer
    transition-all duration-200 ease-in-out
    focus:outline-none focus:ring-2 focus:ring-offset-2
    disabled:opacity-50 disabled:cursor-not-allowed
  `

  // Classes theo Variant
  const variantClasses = {
    primary: `
      bg-blue-600 text-white hover:bg-blue-700
      focus:ring-blue-500
    `,
    secondary: `
      bg-gray-500 text-white hover:bg-gray-600
      focus:ring-gray-400
    `,
    danger: `
      bg-red-600 text-white hover:bg-red-700
      focus:ring-red-500
    `,
    text: `
      bg-transparent text-blue-600 border border-transparent hover:bg-blue-50
      focus:ring-blue-500 focus:ring-offset-0
    `
  }

  // Classes theo Size
  const sizeClasses = {
    small: 'px-3 py-1.5 text-sm',
    medium: 'px-4 py-2 text-base',
    large: 'px-6 py-3 text-lg'
  }

  // Loading state classes
  const loadingClasses = isLoading ? 'opacity-70 cursor-progress' : ''

  // Kết hợp tất cả các class
  const combinedClasses = `
    ${baseClasses}
    ${variantClasses[variant]}
    ${sizeClasses[size]}
    ${loadingClasses}
    ${className || ''}
  `
    .trim()
    .replace(/\s+/g, ' ') // Xóa khoảng trắng thừa

  return (
    <button
      className={combinedClasses}
      disabled={disabled || isLoading} // Vô hiệu hóa button nếu đang loading
      {...props} // Truyền tất cả các props HTML Button tiêu chuẩn
    >
      {isLoading ? 'Loading...' : children}
    </button>
  )
}

export { Button } // Dùng named export
