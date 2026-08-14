// app/components/layout/Sidebar.tsx
import React from 'react'
import { Link, useLocation } from 'react-router-dom' // useLocation để làm active link

const Sidebar: React.FC = () => {
  const location = useLocation()

  const navItems = [
    { path: '/chat', label: 'Quầy Lễ Quan Chat' },
    { path: '/legal-documents', label: 'Văn Bản Pháp Luật' },
    { path: '/contract-templates', label: 'Mẫu Hợp Đồng' },
    { path: '/chat-history', label: 'Lịch Sử Chat' }
  ]

  return (
    <aside style={sidebarStyle}>
      <ul style={ulStyle}>
        {navItems.map((item) => (
          <li key={item.path} style={liStyle}>
            <Link
              to={item.path}
              style={{
                ...linkStyle,
                ...(location.pathname === item.path ? activeLinkStyle : {})
              }}
            >
              {item.label}
            </Link>
          </li>
        ))}
      </ul>
      <div style={sectionStyle}>
        <h3>Mở sản phẩm của JurisAI</h3>
        {/* Nút hoặc link để mở các sản phẩm khác */}
      </div>
    </aside>
  )
}

// Simple inline styles for demonstration
const sidebarStyle: React.CSSProperties = {
  width: '250px',
  backgroundColor: '#f0f2f5',
  padding: '20px',
  borderRight: '1px solid #e0e0e0',
  minHeight: 'calc(100vh - 80px)', // Giả sử header cao 80px
  display: 'flex',
  flexDirection: 'column',
  justifyContent: 'space-between' // Đẩy phần "Mở sản phẩm" xuống dưới
}

const ulStyle: React.CSSProperties = {
  listStyle: 'none',
  padding: 0,
  margin: 0
}

const liStyle: React.CSSProperties = {
  marginBottom: '10px'
}

const linkStyle: React.CSSProperties = {
  textDecoration: 'none',
  color: '#333',
  padding: '10px 15px',
  display: 'block',
  borderRadius: '5px',
  transition: 'background-color 0.2s ease'
}

const activeLinkStyle: React.CSSProperties = {
  backgroundColor: '#e6e8eb', // Màu nền khi link active
  fontWeight: 'bold',
  color: '#007bff' // Màu chữ khi link active
}

const sectionStyle: React.CSSProperties = {
  marginTop: 'auto', // Đẩy phần này xuống cuối sidebar
  paddingTop: '20px',
  borderTop: '1px solid #e0e0e0',
  textAlign: 'center'
}

export default Sidebar
