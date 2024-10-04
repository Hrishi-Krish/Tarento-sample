import { useCallback, useEffect, useState } from 'react';
import useAuth from "../hooks/useAuth";
import { useNavigate } from 'react-router-dom';

export default function GrievanceManagementTable() {

    const { user, jwt } = useAuth();
    const { id } = user || {};
    const [grievances, setGrievances] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const navigate = useNavigate();
    const itemsPerPage = 5;

    const handleRowClick = (grievance) => {
        localStorage.setItem('selectedGrievance', JSON.stringify(grievance));
        navigate(`/grievance/${grievance.id}`, { state: { grievance } });
    }

    const fetchUserGrievances = useCallback(async () => {
        if (!id || !jwt) return;
        try {
            const status = 'Submitted to supervisor for review';
            const encodedStatus = encodeURIComponent(status);
            const response = await fetch(`http://localhost:8080/api/grievances/status/${encodedStatus}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${jwt}`
                },
            });
            if (response.ok) {
                const data = await response.json();
                setGrievances(data);
            }
        } catch (error) {
            console.error(error);
        }
    }, [id, jwt]);

    useEffect(() => {
        fetchUserGrievances();
    }, [fetchUserGrievances]); // Dependencies array

    const tableHeaders = grievances.length > 0 ? Object.keys(grievances[0]) : [];
    if (grievances.length === 0) {
        return <h1 className="text-2xl text-center text-white mt-4">No grievances found</h1>
    }

    // Calculate paginated data
    const indexOfLastItem = currentPage * itemsPerPage;
    const indexOfFirstItem = indexOfLastItem - itemsPerPage;
    const currentItems = grievances.slice(indexOfFirstItem, indexOfLastItem);

    // Calculate total pages
    const totalPages = Math.ceil(grievances.length / itemsPerPage);

    return (
        <>
            <table className="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
                {
                    !grievances.length ? (
                        <thead>
                            <tr>
                                <td colSpan={tableHeaders.length} className="text-center py-4">
                                    No grievances found.
                                </td>
                            </tr>
                        </thead>) : (
                        <>
                            <thead className="text-xs border-b border-t dark:border-yellow-500 text-gray-700 uppercase dark:text-gray-400">
                                <tr>
                                    {tableHeaders.map((header, index) => (
                                        <th key={header} scope="col" className={`px-6 py-4 text-center ${(index % 2 === 1) && ' bg-gray-800'}`}>
                                            {header.replace(/([A-Z])/g, ' $1').toUpperCase()}
                                        </th>
                                    ))}
                                </tr>
                            </thead>
                            <tbody>
                                {currentItems.map((grievance) => (
                                    <tr key={grievance.id}
                                        className="bg-white border-b dark:bg-gray-800 dark:border-gray-700 cursor-pointer"
                                        onClick={() => handleRowClick(grievance)}>
                                        {tableHeaders.map((header, index) => (
                                            <td key={header}
                                                className={`px-6 py-4 text-center ${(index % 2 === 0) && ' bg-gray-900'}`}>
                                                {header === 'category' ? (grievance[header] ? grievance[header] : 'Unassigned') : grievance[header]}
                                            </td>
                                        ))}
                                    </tr>
                                ))}
                            </tbody>
                        </>
                    )
                }

            </table>
            {(totalPages != 0) && <div className="flex justify-between items-center py-3">
                <button
                    onClick={() => setCurrentPage((prev) => Math.max(prev - 1, 1))}
                    disabled={currentPage === 1}
                    className="px-4 py-2 ml-2 bg-indigo-600 text-white rounded  disabled:bg-gray-300 disabled:text-gray-700 disabled:opacity-50"
                >
                    Previous
                </button>
                <span className="text-white">
                    Page {currentPage} of {totalPages}
                </span>
                <button
                    onClick={() => setCurrentPage((prev) => Math.min(prev + 1, totalPages))}
                    disabled={currentPage === totalPages}
                    className="px-4 py-2 mr-2 bg-indigo-600 text-white rounded disabled:bg-gray-300 disabled:text-gray-700 disabled:opacity-50"
                >
                    Next
                </button>
            </div>}
        </>
    )
}
