import { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import useAuth from '../hooks/useAuth';

const GrievanceDetails = () => {
    const { state } = useLocation();
    const [grievance, setGrievance] = useState(state?.grievance || null);
    const [category, setCategory] = useState(grievance?.category || '');
    const [assign, setAssign] = useState('');
    const [assignees, setAssignees] = useState([]);
    const [isEditingCategory, setIsEditingCategory] = useState(false);
    const [isEditingAssign, setIsEditingAssign] = useState(false);
    const { user, jwt, email } = useAuth();
    const { role: userRole, id } = user || {};

    useEffect(() => {
        const savedGrievance = localStorage.getItem('selectedGrievance');
        if (savedGrievance) {
            const parsedGrievance = JSON.parse(savedGrievance);
            setGrievance(parsedGrievance);
            setCategory(parsedGrievance.category);
        }
    }, []);

    useEffect(() => {
        const fetchAssignees = async () => {
            if (userRole !== 'SUPERVISOR' && userRole !== 'ADMIN') return;
            try {
                const response = await fetch('http://localhost:8080/api/users/role/ASSIGNEE', {
                    method: 'GET',
                    headers: {
                        'Authorization': `Bearer ${jwt}`
                    }
                });
                if (response.ok) {
                    const data = await response.json();
                    setAssignees(data);
                    localStorage.setItem('assignees', JSON.stringify(data));
                }
            } catch (error) {
                console.error('Error fetching assignees:', error);
            }
        };

        fetchAssignees();
    }, [jwt, userRole]);

    useEffect(() => {
        const fetchAssignment = async () => {
            if (userRole !== 'SUPERVISOR' && userRole !== 'ADMIN') return;
            if (!grievance) return;
            try {
                const response = await fetch(`http://localhost:8080/api/assignments/grievance/${grievance.id}`, {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${jwt}`
                    }
                });
                if (response.ok) {
                    const data = await response.json();
                    const assigneeEmail = data.assigneeEmail;
                    const assignee = assignees.find(a => a.email === assigneeEmail);
                    if (assignee) {
                        setAssign(assignee.id);
                    }
                }
            } catch (error) {
                console.error('Error fetching assignment:', error);
            }
        };

        fetchAssignment();
    }, [grievance, assignees, jwt, userRole]);

    const handleGrievanceResolve = async () => {
        try {
            
            const response = await fetch(`http://localhost:8080/api/grievances/resolved/${grievance.id}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${jwt}`
            }});
            if (response.ok) {
                const updatedGrievance = { ...grievance, status: 'Resolved' };
                setGrievance(updatedGrievance);
                localStorage.setItem('selectedGrievance', JSON.stringify(updatedGrievance));
                alert('Grievance resolved successfully');
            }
        } catch (error) {
            console.error('Error resolving grievance:', error);
        }
    }

    const handleCategorySubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await fetch(`http://localhost:8080/api/grievances/category/${grievance.id}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${jwt}`
                },
                body: category
            });
            if (response.status === 200) {
                const updatedGrievance = { ...grievance, category }
                setGrievance(updatedGrievance);
                localStorage.setItem('selectedGrievance', JSON.stringify(updatedGrievance));
                alert('Category updated successfully');
            }
        } catch (error) {
            console.error(error);
        }
        setIsEditingCategory(false);
    };

    const handleAssigneeSelectSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await fetch('http://localhost:8080/api/assignments', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${jwt}`
                },
                body: JSON.stringify({supervisorId: id, assigneeId: assign, grievanceId: grievance.id}) 
            });
            if (response.ok) {
                alert('Assignee selected successfully');
                // Handle any additional logic after successful submission
            }
        } catch (error) {
            console.error('Error selecting assignee:', error);
        }
        setIsEditingAssign(false);
    };

    const selectedAssignee = assignees.find(assignee => assignee.id === assign);

    if (!grievance) {
        return (
            <div className="flex justify-center items-center min-h-[90vh] bg-gray-500">
                <div className="bg-gray-900 text-white p-8 rounded-lg shadow-lg w-3/5">
                    <h1 className="text-3xl mb-6 text-center">No grievance data available.</h1>
                </div>
            </div>
        );
    }

    return (
        <div className="flex justify-center items-center min-h-[90vh] bg-gray-500">
            <div className="bg-gray-900 text-white p-8 rounded-lg shadow-lg w-4/5">
                <h1 className="text-3xl mb-6 text-center">Grievance Details</h1>
                <div className="grid grid-cols-[auto,1fr] gap-4">
                    <p className="font-bold text-right flex justify-end items-center">ID:</p>
                    <p className="flex items-center">{grievance.id}</p>
                    <p className="font-bold text-right flex justify-end items-center">Title:</p>
                    <p className="flex items-center">{grievance.title}</p>
                    <p className="font-bold text-right flex justify-end items-center">Description:</p>
                    <p className="flex items-center">{grievance.description}</p>
                    <p className="font-bold text-right flex justify-end items-center">Status:</p>
                    <p className="flex items-center">{grievance.status}</p>
                    <p className="font-bold text-right flex justify-end items-center">Category:</p>
                    {(userRole === 'SUPERVISOR' || userRole === 'ADMIN') ? (
                        isEditingCategory ? (
                            <form onSubmit={handleCategorySubmit} className="col-span-1 flex items-center gap-4">
                                <input
                                    type="text"
                                    value={category}
                                    onChange={(e) => setCategory(e.target.value)}
                                    className="bg-gray-700 text-white p-2 rounded"
                                />
                                <button type="submit" className="bg-blue-500 text-white p-2 rounded outline-none">Submit</button>
                            </form>
                        ) : (
                            <div className="col-span-1 flex items-center gap-4">
                                <p>{category ? category : 'Unassigned'}</p>
                                    <button onClick={() => setIsEditingCategory(true)} className="bg-yellow-500 text-white p-2 rounded outline-none">Edit</button>
                            </div>
                        )
                    ) : (
                        <p>{grievance.category ? grievance.category : 'Unassigned'}</p>
                    )}
                    {
                        userRole === 'ASSIGNEE' && (
                            <>
                                <p className="font-bold text-right flex justify-end items-center">Assigned by:</p>
                                <p className="flex items-center">{grievance.supervisorEmail}</p>
                                <p className="font-bold text-right flex justify-end items-center">Assigned to:</p>
                                <p className="flex items-center">{grievance.assigneeEmail}</p>
                                </>
                        )
                    }
                </div>
                {
                    grievance.assigneeEmail === email && (
                        <div className="flex justify-center items-center mt-4">
                            <button
                                onClick={handleGrievanceResolve}
                                className="bg-blue-500 text-white p-2 rounded text-center outline-none"
                            >
                                Mark Grievance as Resolved
                            </button>
                        </div>
                    )
                }
                {(userRole === 'SUPERVISOR' || userRole === 'ADMIN') && (
                    isEditingAssign ? (
                        <form onSubmit={handleAssigneeSelectSubmit} className="mt-8 flex items-center gap-4">
                            <p className="font-bold text-right">Assign to:</p>
                            <select
                                value={assign}
                                onChange={(e) => setAssign(e.target.value)}
                                className="bg-gray-700 text-white p-2 rounded w-2/5 outline-none"
                            >
                                <option value="">Select an assignee</option>
                                {assignees.map((assignee) => (
                                    <option key={assignee.id} value={assignee.id}>
                                        {assignee.username} (ID: {assignee.id})
                                    </option>
                                ))}
                            </select>
                            <div className="col-span-1 flex justify-end">
                                <button type="submit" className="bg-green-500 text-white p-2 rounded outline-none">Submit</button>
                            </div>
                        </form>
                    ) : (
                        <div className="mt-8 flex items-center gap-4 text-white">
                            <p className="font-bold text-right">{grievance.status === 'Submitted to supervisor for review' ? 'Assign to:' : 'Assigned to:'}</p>
                                <p>{selectedAssignee ? selectedAssignee.username : 'Unassigned'}</p>
                                <button onClick={() => setIsEditingAssign(true)} className="bg-yellow-500 text-white p-2 rounded outline-none">Edit</button>
                        </div>
                    )
                )}
            </div>
        </div>
    );
};

export default GrievanceDetails;