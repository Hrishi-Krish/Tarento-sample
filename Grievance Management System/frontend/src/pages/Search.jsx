import { useCallback, useEffect, useState } from "react";
import SearchBar from "../components/SearchBar";
import useAuth from "../hooks/useAuth";

export default function Search() {

    const [users, setUsers] = useState([]);
    const {jwt} = useAuth();

    const loadUsers = useCallback(async () => {
        try {
            const response = await fetch("http://localhost:8080/api/users", {
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${jwt}`
            }})
            if (response.ok) {
                const data = await response.json();
                setUsers(data);
                console.log(data);
                
            }
        } catch (error) {
            console.error(error);
        }
    }, [jwt]);

    useEffect(() => {
        loadUsers();
    }, [loadUsers]);

    const headers = users?.length > 0 ? Object.keys(users[0]) : [];

    return (
        <div className="min-h-[90vh] bg-gray-500 flex flex-col justify-center py-12 sm:px-6 lg:px-8 ">
            <div className="mt-8 flex justify-center sm:mx-auto sm:w-full sm:max-w-md z-1">
                <div className="bg-gray-900 min-w-[60vw] py-8 px-4 shadow sm:rounded-lg sm:px-10">
                    {users.length > 0 && headers.length > 0 && (
                        <SearchBar users={users} headers={headers} />
                    )}
                </div>
            </div>
        </div>
    )
}