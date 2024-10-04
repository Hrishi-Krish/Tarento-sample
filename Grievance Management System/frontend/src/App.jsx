import { Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar';
import Welcome from './pages/Welcome';
import Login from './pages/Login';
import Register from './pages/Register';
import Home from './pages/Home';
import Create from './pages/Create';
import Profile from './pages/Profile';
import Search from './pages/Search';
import Manage from './pages/Manage';
import PrivateRoute from './components/PrivateRoute';
import PublicRoute from './components/PublicRoute';
import GrievanceDetails from './pages/GrievanceDetails';

function App() {
 
  return (
    <>
      <main className='flex flex-col min-h-screen bg-gray-500'>
        <Navbar />
        <Routes>
          <Route path='/' element={<PublicRoute><Welcome /></PublicRoute>} />
          <Route path='/login' element={<PublicRoute><Login /></PublicRoute>} />
          <Route path='/register' element={<PublicRoute><Register /></PublicRoute>} />
          <Route element={<PrivateRoute />}>
            <Route path='/home' element={<Home />} />
            <Route path='/create' element={<Create />} />
            <Route path='/profile' element={<Profile />} />
            <Route path='/search' element={<Search />} />
            <Route path='/manage' element={<Manage />} />
            <Route path="/grievance/:id" element={<GrievanceDetails />} />
          </Route>
        </Routes>
      </main>
    </>
  );
}

export default App;