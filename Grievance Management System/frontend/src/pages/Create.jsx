import { useState } from "react";
import useAuth from "../hooks/useAuth";

export default function Create() {

    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const {jwt, email} = useAuth();

    const handleSubmit = async (e) => {
        e.preventDefault()
        if (title.length < 1 || description.length < 1) {
            alert('Title and Description cannot be empty');
            return;
        }
        const response = await fetch('http://localhost:8080/api/grievances/', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${jwt}`
            },
            body: JSON.stringify({
                title: title,
                description: description,
                email: email
            })
        });
        if (response.status === 201) {
            alert('Grievance submitted successfully');
            setTitle('');
            setDescription('');
        } else {
            alert('Failed to submit grievance');
        }
    }

    return (
        <div className="min-h-[90vh] bg-gray-500 flex flex-col justify-center py-12 sm:px-6 lg:px-8">
            <div className="mt-8 sm:mx-auto sm:w-full sm:max-w-md z-1">
                <div className="bg-gray-900 py-8 px-4 shadow sm:rounded-lg sm:px-10">
                    <form className="space-y-6 flex flex-col" onSubmit={handleSubmit}>
                        <h1 className="text-3xl font-bold mb-4 text-white text-center">Submit new Grievance</h1>
                        <div className="mb-4">
                            <label htmlFor="title" className="block text-sm font-medium text-gray-200">Title</label>
                            <input type="text" id="title" name="title" value={title} onChange={(e) => setTitle(e.target.value)}
                                className="mt-1 block w-full p-2 border outline-none border-gray-300 rounded-md"
                                placeholder="Title..." />
                        </div>
                        <div className="mb-4">
                            <label htmlFor="description" className="block text-sm font-medium text-gray-200">Description</label>
                            <textarea id="description" name="description" rows="4" value={description} onChange={(e) => setDescription(e.target.value)}
                                className="mt-1 block w-full p-2 border outline-none border-gray-300 rounded-md"
                                placeholder="Description..." />
                            <div className="text-xs text-gray-300">
                                *Please provide a detailed description of your grievance
                            </div>
                        </div>
                        
                        <button type="submit" className="bg-blue-500 text-white py-2 px-4 rounded hover:bg-blue-700">Submit</button>
                    </form>
                </div>
            </div>
            
        </div>
    )
}