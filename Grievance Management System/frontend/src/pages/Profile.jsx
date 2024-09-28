import { useState, useEffect } from 'react';
import useAuth from '../hooks/useAuth';

export default function Profile() {

    const [isEditing, setIsEditing] = useState({
        username: false,
        password: false,
        role: false
    });

    const { user, email, jwt, setUser } = useAuth()
    const { username, role, id } = user || {}

    const [userDetails, setUserDetails] = useState({
        email: '',
        username: '',
        password: 'password',
        role: ''
    });

    useEffect(() => {
        if (user) {
            setUserDetails({
                email: email || '',
                username: username || '',
                password: 'password',
                role: role || ''
            });
        }
    }, [user, email, username, role]);

    const handleEditClick = (field) => {
        if (field === 'password') {
            setUserDetails((prevState) => ({
                ...prevState,
                password: ''
            }));
        } 
        setIsEditing((prevState) => ({
            ...prevState,
            [field]: !prevState[field]
        }));    
        
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setUserDetails((prevState) => ({
            ...prevState,
            [name]: value
        }));
    };

    const handleUsernameSubmit = async () => {
        if (userDetails.username.length < 1) {
            alert('Username cannot be empty');
            return;
        }
        const response = await fetch('http://localhost:8080/api/users/username/'+ id, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${jwt}`
            }, 
            body: JSON.stringify({ username: userDetails.username, id: id })
        })
        if (response.ok) {
            alert('Username updated successfully');
            setUser((prevUser) => ({
                ...prevUser,
                username: userDetails.username
            }));
        } else {
            alert('Error updating username');
        }
        setIsEditing((prevState) => ({
            ...prevState,
            username: !prevState.username
        }));
        const storedUser = JSON.parse(sessionStorage.getItem('user'));
        sessionStorage.setItem('user', JSON.stringify({ ...storedUser, username: userDetails.username }));
    }

    const handlePasswordSubmit = async () => {
        if (userDetails.password.length < 1) {
            alert('Password cannot be empty');
            return;
        }
        const response = await fetch('http://localhost:8080/api/users/password/' + id, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${jwt}`
            },
            body: JSON.stringify({ password: userDetails.password, id: id })
        })
        if (response.ok) {
            alert('password updated successfully');
        } else {
            alert('Error updating password');
        }
        setIsEditing((prevState) => ({
            ...prevState,
            password: !prevState.password
        }));
    }

    return (
        <div className="min-h-[90vh] bg-gray-500 flex flex-col justify-center py-12 sm:px-6 lg:px-8">
            <div className="mt-8 sm:mx-auto sm:w-full sm:max-w-md z-1">
                <div className="bg-gray-900 py-8 px-4 shadow sm:rounded-lg sm:px-10">
                    <h2 className="mt-6 mb-12 text-center text-3xl font-extrabold text-gray-100">
                        User Profile
                    </h2>
                    <form className="space-y-6">
                        <div>
                            <label className="block text-sm font-medium text-gray-100">Email</label>
                            <input
                                type="email"
                                name="email"
                                value={userDetails.email}
                                readOnly
                                className="mt-1 block w-full px-3 py-2 bg-gray-700 text-gray-100 border border-gray-600 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 sm:text-sm"
                            />
                        </div>
                        <div>
                            <label className="block text-sm font-medium text-gray-100">Username</label>
                            <div className="flex">
                                <input
                                    type="text"
                                    name="username"
                                    value={userDetails.username}
                                    onChange={handleChange}
                                    readOnly={!isEditing.username}
                                    className="mt-1 block w-full px-3 py-2 bg-gray-700 text-gray-100 border border-gray-600 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 sm:text-sm"
                                />
                                {!(role === "ADMIN") && <button
                                    type="button"
                                    onClick={() => isEditing.username ? handleUsernameSubmit() : handleEditClick('username')}
                                    className="ml-2 px-4 py-2 bg-blue-500 text-white rounded-md"
                                >
                                    {isEditing.username ? 'Submit' : 'Edit'}
                                </button>}
                            </div>
                        </div>
                        <div>
                            <label className="block text-sm font-medium text-gray-100">Password</label>
                            <div className="flex">
                                <input
                                    type="password"
                                    name="password"
                                    value={userDetails.password}
                                    onChange={handleChange}
                                    readOnly={!isEditing.password}
                                    className="mt-1 block w-full px-3 py-2 bg-gray-700 text-gray-100 border border-gray-600 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 sm:text-sm"
                                />
                                {!(role === "ADMIN") && <button
                                    type="button"
                                    onClick={() => isEditing.password ? handlePasswordSubmit() : handleEditClick('password')}
                                    className="ml-2 px-4 py-2 bg-blue-500 text-white rounded-md"
                                >
                                    {isEditing.password ? 'Submit' : 'Edit'}
                                </button>}
                            </div>
                        </div>
                        <div>
                            <label className="block text-sm font-medium text-gray-100">Role</label>
                            <input
                                type="text"
                                name="role"
                                value={userDetails.role}
                                readOnly
                                className="mt-1 block w-full px-3 py-2 bg-gray-700 text-gray-100 border border-gray-600 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 sm:text-sm"
                            />
                        </div>
                    </form>
                </div>
            </div>
        </div>
    );
}
