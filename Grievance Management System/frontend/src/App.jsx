import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'
import Navbar from './components/Navbar'
import './App.css'
import Welcome from './pages/Welcome'
import Login from './pages/Login'
import Register from './pages/Register'
import { AuthProvider } from './context/AuthContext'
import Home from './pages/Home'
import Create from './pages/Create'
import Profile from './pages/Profile'
import Search from './pages/Search'
import Manage from './pages/Manage'

function App() {

  return (
    <>
    <AuthProvider>
        <Router>
          <main className='flex flex-col min-h-screen bg-gray-500'>
            <Navbar />
            <Routes>
              <Route path='/' element={<Welcome />} />
              <Route path='/login' element={<Login />} />
              <Route path='/register' element={<Register />} />
              <Route path='/home' element={<Home />} />
              <Route path='/create' element={<Create />} />
              <Route path='/profile' element={<Profile />} />
              <Route path='/search' element={<Search />} />
              <Route path='/manage' element={<Manage />} />
            </Routes>
          </main>
        </Router>
    </AuthProvider>
      
    </>
  )
}

export default App
