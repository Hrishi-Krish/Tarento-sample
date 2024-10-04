import { Navigate } from 'react-router-dom';
import useAuth from '../hooks/useAuth'; // Adjust the import path as necessary

const PublicRoute = ({ children }) => {
    const { isAuthenticated } = useAuth();

    return isAuthenticated ? <Navigate to="/home" /> : children;
};

export default PublicRoute;