// app/components/common/Input/Input.tsx
import React from 'react'

// Định nghĩa Props cho Input component
interface InputProps extends React.InputHTMLAttributes<HTMLInputElement> {
  // Bạn có thể thêm các props tùy chỉnh ở đây nếu cần
  // Ví dụ: error?: boolean; (để điều khiển style lỗi)
  //        label?: string;
}

const Input: React.FC<InputProps> = ({ className, ...props }) => {
  // Các class mặc định của Tailwind cho Input
  const defaultClasses = `
    w-full px-4 py-2 border border-gray-300 rounded-md
    text-base focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent
    placeholder-gray-400 transition-all duration-200 ease-in-out
  `

  // Nếu bạn muốn có style cho lỗi
  // const errorClasses = props.error ? 'border-red-500 ring-red-200' : '';

  // Kết hợp các class mặc định và class được truyền từ bên ngoài
  const combinedClasses = `${defaultClasses} ${className || ''}`.trim()

  return (
    <input
      className={combinedClasses}
      {...props} // Truyền tất cả các props HTML Input tiêu chuẩn
    />
  )
}

export { Input } // Dùng named export
