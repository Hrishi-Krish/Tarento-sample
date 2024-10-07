import { useEffect, useState } from 'react';
import { MagnifyingGlassIcon, ChevronDownIcon } from "@heroicons/react/24/solid";

function SearchBar({ users, headers=[] }) {
    const [searchTerm, setSearchTerm] = useState('');
    const [searchField, setSearchField] = useState(headers.length > 0 ? headers[0] : '');
    const [isDropdownVisible, setIsDropdownVisible] = useState(false);

    useEffect(() => {
        if (headers.length > 0) {
            setSearchField(headers[0]);
        }
    }, [headers]);

    const handleFieldSelect = (field) => {
        setSearchField(field);
        setIsDropdownVisible(false);
    };

    // Filter users based on the selected search field
    const filteredUsers = users.filter((user) =>
        user[searchField] ? user[searchField].toString().toLowerCase().includes(searchTerm.toLowerCase()) : false
    );

    const formatHeader = (header) => {
        if (header === 'roleName') return 'Role';
        return header.charAt(0).toUpperCase() + header.slice(1);
    };

    return (
        <div className="flex flex-col items-center min-h-[40vh] bg-gray-100 p-4">
            {/* Search bar and dropdown */}
            <div className="relative w-full max-w-xl mb-6">
                <div className="flex items-center border border-gray-300 rounded-md shadow-sm bg-white">
                    {/* Dropdown for search field */}
                    <div className="relative">
                        <button
                            onClick={() => setIsDropdownVisible(!isDropdownVisible)}
                            className="flex items-center bg-gray-200 p-2 rounded-l-md shadow-sm focus:outline-none">
                            <span className="mr-2">{formatHeader(searchField)}</span>
                            <ChevronDownIcon className="h-5 w-5 text-gray-500" />
                        </button>
                        {/* Dropdown menu */}
                        {isDropdownVisible && (
                            <div className="absolute mt-2 w-48 bg-white border border-gray-300 rounded-md shadow-lg">
                                <ul>
                                    {headers.map((field, index) => (
                                        <li key={`${field}-${index}`}
                                            className="p-2 hover:bg-gray-100 cursor-pointer"
                                            onClick={() => handleFieldSelect(field)}>
                                            {formatHeader(field)}
                                        </li>
                                    ))}
                                </ul>
                            </div>
                        )}
                    </div>

                    {/* Search input */}
                    <input
                        type="text"
                        value={searchTerm}
                        onChange={e => setSearchTerm(e.target.value)}
                        placeholder={`Search by ${formatHeader(searchField) }`}
                        className="flex-grow p-2 border-l border-gray-300 outline-none focus:border-transparent"
                    />

                    {/* Search icon */}
                    <MagnifyingGlassIcon className="w-5 h-5 text-gray-500 mr-4" />
                </div>
            </div>

            {/* Display filtered users */}
            <table className="w-full max-w-xl bg-white border border-gray-200 rounded-md shadow-lg">
                <thead className="text-xs border-b border-t">
                    <tr>
                        {headers.map((header) => (
                            <th key={header} scope="col" className={`px-6 py-4 text-center`}>
                                {formatHeader(header)}
                            </th>
                        ))}
                    </tr>
                </thead>
                <tbody>
                    {filteredUsers.length > 0 ? (
                        filteredUsers.map((user) => (
                            <tr key={user.id} className="bg-white border-b">
                                {headers.map((header) => (
                                    <td key={header} className={`px-6 py-4 text-center `}>
                                        {user[header]}
                                    </td>
                                ))}
                            </tr>
                        ))
                    ) : (
                        <tr>
                            <td colSpan={headers.length} className="px-4 py-2 text-gray-500 text-center">No results found</td>
                        </tr>
                    )}
                </tbody>
            </table>
        </div>
    );
}

export default SearchBar;