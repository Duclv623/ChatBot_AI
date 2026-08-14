// app/components/layout/Header.tsx
import React from 'react'
import { Link } from 'react-router-dom'
import { useAuthStore } from '../../features/auth/store/authStore' // Để lấy thông tin user và logout

const Header: React.FC = () => {
  const user = useAuthStore((state) => state.user)
  const logout = useAuthStore((state) => state.logout)

  const handleLogout = () => {
    logout()
    // Có thể chuyển hướng về trang login sau khi logout, tùy thuộc vào PrivateRoute của bạn
  }

  return (
    <header style={headerStyle}>
      <div style={{ display: 'flex', alignItems: 'center' }}>
        <Link to='/' style={logoStyle}>
          Juris AI
        </Link>
        {/* Các nút hoặc icon khác trên Header */}
      </div>
      <nav style={navStyle}>
        {user ? (
          <>
            <span style={{ marginRight: '15px' }}>
              Xin chào, {user.name || user.email}!
            </span>
            <button onClick={handleLogout} style={logoutButtonStyle}>
              Đăng xuất
            </button>
          </>
        ) : (
          <Link to='/login' style={loginLinkStyle}>
            Đăng nhập
          </Link>
        )}
      </nav>
    </header>
  )
}

// Simple inline styles for demonstration (you'd use CSS/Styled Components normally)
const headerStyle: React.CSSProperties = {
  backgroundColor: '#f8f9fa',
  padding: '15px 30px',
  display: 'flex',
  justifyContent: 'space-between',
  alignItems: 'center',
  borderBottom: '1px solid #e0e0e0'
}

const logoStyle: React.CSSProperties = {
  fontSize: '24px',
  fontWeight: 'bold',
  color: '#333',
  textDecoration: 'none'
}

const navStyle: React.CSSProperties = {
  display: 'flex',
  alignItems: 'center'
}

const logoutButtonStyle: React.CSSProperties = {
  padding: '8px 15px',
  backgroundColor: '#dc3545',
  color: 'white',
  border: 'none',
  borderRadius: '5px',
  cursor: 'pointer'
}

const loginLinkStyle: React.CSSProperties = {
  textDecoration: 'none',
  color: '#007bff',
  marginLeft: '15px'
}

export default Header
