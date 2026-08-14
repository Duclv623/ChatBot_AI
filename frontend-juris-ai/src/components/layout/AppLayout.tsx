// app/components/layout/AppLayout.tsx
import React from 'react'
import { Outlet } from 'react-router-dom'

// Import các component layout con (bạn sẽ cần tạo các file này)
import Header from './Header' // Ví dụ: app/components/layout/Header.tsx
import Sidebar from './Sidebar' // Ví dụ: app/components/layout/Sidebar.tsx

// Bạn có thể dùng CSS Modules, Styled Components, hoặc Tailwind CSS
// Import CSS Module nếu bạn dùng (ví dụ: app/components/layout/AppLayout.module.css)
// import styles from './AppLayout.module.css';

const AppLayout: React.FC = () => {
  return (
    // Container bao bọc toàn bộ layout
    <div className='app-container'>
      {' '}
      {/* hoặc styles.appContainer nếu dùng CSS Modules */}
      {/* Header của ứng dụng */}
      <Header />
      {/* Main content area: Sidebar và phần nội dung chính */}
      <div className='main-content-wrapper'>
        {' '}
        {/* styles.mainContentWrapper */}
        {/* Sidebar điều hướng */}
        <Sidebar />
        {/* Khu vực nội dung chính của từng trang */}
        <main className='page-content'>
          {' '}
          {/* styles.pageContent */}
          <Outlet />{' '}
          {/* <-- Đây là nơi các trang con (ChatPage, LegalDocsPage,...) sẽ được render */}
        </main>
      </div>
      {/* Bạn có thể thêm Footer ở đây nếu có */}
      {/* <Footer /> */}
    </div>
  )
}

export default AppLayout
