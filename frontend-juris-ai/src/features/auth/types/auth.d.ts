export interface AuthUser {
  id: number
  email: string
  name?: string // Optional: tên người dùng có thể không có hoặc là một chuỗi rỗng
  // Thêm các trường thông tin user khác từ backend trả về nếu có (ví dụ: role, avatarUrl)
  // role?: 'admin' | 'user';
}

export interface LoginPayload {
  email: string
  password: string
}

export interface RegisterPayload extends LoginPayload {
  name: string // Thường đăng ký cần thêm tên
  // Thêm các trường đăng ký khác nếu cần (ví dụ: confirmPassword - sẽ được xử lý ở FE)
}

export interface AuthResponse {
  user: AuthUser
  token: string // JWT token
  // Có thể có refreshToken nếu backend hỗ trợ cho việc làm mới token tự động
  // refreshToken?: string;
}
