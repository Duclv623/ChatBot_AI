import React from 'react'
import { Routes, Route } from 'react-router-dom'
import routes from './routes'

const AppRouter: React.FC = () => {
  return (
    <Routes>
      {routes.map(({ path, component: Component }, index) => (
        <Route key={index} path={path} element={<Component />} />
      ))}
    </Routes>
  )
}

export default AppRouter
