import LoginPage from '../features/auth/pages/LoginPage'
import RegisterPage from '../features/auth/pages/RegisterPage'
import ChatBotPage from '../features/chatbot/pages/ChatBotPage'

export interface IRoute {
  path: string
  component: React.ComponentType
}

const routes: IRoute[] = [
  { path: '/chat', component: ChatBotPage },
  { path: '/login', component: LoginPage },
  { path: '/register', component: RegisterPage }
]

export default routes
