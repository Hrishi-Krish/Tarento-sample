import  { useState, useEffect } from 'react';
import { XMarkIcon } from '@heroicons/react/24/outline'; // Import the XMarkIcon from Heroicons
import useAuth from '../hooks/useAuth';

export default function Manage() {

    const {jwt} = useAuth();
    const [isFormVisible, setIsFormVisible] = useState(false);
    const [formData, setFormData] = useState({
        username: '',
        email: '',
        password: '',
        role: 'SUPERVISOR', // Default role
    });

    useEffect(() => {
        // Clear form data on component mount
        setFormData({
            username: '',
            email: '',
            password: '',
            role: 'SUPERVISOR', // Default role
        });
    }, []);

    const handleCreateEmployeeClick = () => {
        setIsFormVisible(true);
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prevData) => ({
            ...prevData,
            [name]: value,
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await fetch('http://localhost:8080/api/users/newEmployee', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${jwt}`,
                },
                body: JSON.stringify({username: formData.username, email: formData.email, password: formData.password, role: formData.role}),
            });
            if (!response.ok) {
                throw new Error('Failed to create employee');
            } else {
                alert('Employee created successfully');
            }
        } catch (error) {
            alert('Failed to create employee');
            console.error('Failed to create employee:', error);
        }
        console.log('Form submitted:', formData);
        setIsFormVisible(false);
    };

    const handleClose = () => {
        setFormData({
            username: '',
            email: '',
            password: '',
            role: 'SUPERVISOR', // Default role
        });
        setIsFormVisible(false);
    };

    return (
        <>
            <div className="min-h-[90vh] mx-3 my-2 rounded-lg bg-gray-900 flex flex-col gap-32 justify-center py-12 sm:px-6 lg:px-8">
                <button
                    onClick={handleCreateEmployeeClick}
                    className="bg-blue-500 text-white p-2 rounded text-center max-w-64 outline-none"
                >
                    Create New Employee
                </button>
            </div>
            {isFormVisible && (
                <div className="fixed inset-0 bg-gray-800 bg-opacity-75 flex justify-center items-center z-50">
                    <div className="bg-white p-6 rounded shadow-lg w-full max-w-md relative">
                        <button
                            onClick={handleClose}
                            className="absolute top-2 right-2 text-gray-500 hover:text-gray-700"
                        >
                            <XMarkIcon className="h-6 w-6" />
                        </button>
                        <form onSubmit={handleSubmit}>
                            <div>
                                <label htmlFor="username" className="block text-sm font-medium text-gray-700">
                                    Username
                                </label>
                                <input
                                    type="text"
                                    name="username"
                                    id="username"
                                    value={formData.username}
                                    onChange={handleChange}
                                    className="mt-1 p-2 block w-full border border-gray-300 rounded-md shadow-sm focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                                />
                            </div>
                            <div className="mt-4">
                                <label htmlFor="email" className="block text-sm font-medium text-gray-700">
                                    Email
                                </label>
                                <input
                                    type="email"
                                    name="email"
                                    id="email"
                                    value={formData.email}
                                    onChange={handleChange}
                                    className="mt-1 p-2 block w-full border border-gray-300 rounded-md shadow-sm focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                                />
                            </div>
                            <div className="mt-4">
                                <label htmlFor="password" className="block text-sm font-medium text-gray-700">
                                    Password
                                </label>
                                <input
                                    type="password"
                                    name="password"
                                    id="password"
                                    value={formData.password}
                                    onChange={handleChange}
                                    className="mt-1 p-2 block w-full border border-gray-300 rounded-md shadow-sm focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                                />
                            </div>
                            <div className="mt-4">
                                <label htmlFor="role" className="block text-sm font-medium text-gray-700">
                                    Role
                                </label>
                                <select
                                    name="role"
                                    id="role"
                                    value={formData.role}
                                    onChange={handleChange}
                                    className="mt-1 p-2 block w-full border border-gray-300 rounded-md shadow-sm focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                                >
                                    <option value="SUPERVISOR">SUPERVISOR</option>
                                    <option value="ASSIGNEE">ASSIGNEE</option>
                                </select>
                            </div>
                            <div className="mt-6">
                                <button
                                    type="submit"
                                    className="w-full bg-blue-500 text-white p-2 rounded text-center outline-none"
                                >
                                    Register
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            )}
        </>
    );
}