import { createContext, useEffect, useState } from 'react';

const AuthContext = createContext();

function AuthProvider({ children }) {

    const [user, setUser] = useState(null);
    const [isAuthenticated, setIsAuthenticated] = useState(false);
    const [jwt, setJwt] = useState(null);
    const [email, setEmail] = useState(null);

    useEffect(() => {
        const storedUser = sessionStorage.getItem('user');
        const storedIsAuthenticated = sessionStorage.getItem('isAuthenticated');
        const storedJwt = sessionStorage.getItem('jwt');
        const storedEmail = sessionStorage.getItem('email');
        if (storedUser && storedIsAuthenticated && storedJwt && storedEmail) {
            setUser(JSON.parse(storedUser));
            setIsAuthenticated(JSON.parse(storedIsAuthenticated));
            setJwt(storedJwt);
            setEmail(storedEmail);
        }
    }, [])

    const login = async (email, password) => {
        const response = await fetch("http://localhost:8080/api/auth/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ email, password })
        });
        if (response.ok) {
            const data = await response.json();
            setUser(data.response);
            setIsAuthenticated(true);
            setJwt(data.jwt);
            setEmail(email);
            sessionStorage.setItem('user', JSON.stringify(data.response));
            sessionStorage.setItem('isAuthenticated', JSON.stringify(true));
            sessionStorage.setItem('jwt', data.jwt);
            sessionStorage.setItem('email', email);
        } else {
            throw new Error();
        }
    }

    const logout = async () => {
        setUser(null);
        setIsAuthenticated(false);
        setJwt(null);
        sessionStorage.removeItem('user');
        sessionStorage.removeItem('isAuthenticated');
        sessionStorage.removeItem('jwt');
        
        await fetch("http://localhost:8080/api/auth/logout", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${jwt}`
            }
        })
    }

    const register = async (username, email, password) => {
        const response = await fetch("http://localhost:8080/api/users/create", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ username, email, password })
        });
        if (response.ok) {
            login(email, password);
        } else if (response.status === 409) {
            throw new Error("User already exists");
        } else {
            throw new Error("Registration failed");
        }
    }

    return (
        <AuthContext.Provider value={{user, email, setUser, isAuthenticated, login, logout, jwt, register}}>
            {children}
        </AuthContext.Provider>
    )
}

export { AuthContext as default, AuthProvider } ;