import AssignedGrievancesTable from "../components/AssignedGrievancesTable";
import GrievanceManagementTable from "../components/GrievanceManagementTable";
import UserGrievancesTable from "../components/UserGrievancesTable";
import useAuth from "../hooks/useAuth";

export default function Home() {
   
    const { user } = useAuth();
    const {role} = user || {};

    return (
        <div className="min-h-[90vh] mx-3 my-2 rounded-lg bg-gray-900 flex flex-col gap-32 justify-center py-12 sm:px-6 lg:px-8">
            {(role === 'ADMIN' || role === 'SUPERVISOR') && 
            (<div className="relative min-h-[60vh] overflow-x-auto sm:rounded-lg border-2 border-yellow-500">
                <h1 className="text-white text-center py-3 text-2xl mb-3">
                    Unassigned Grievances
                </h1>
                <GrievanceManagementTable />
            </div>)}
            {(role === 'ASSIGNEE') &&
                (<div className="relative min-h-[60vh] overflow-x-auto sm:rounded-lg border-2 border-yellow-500">
                    <h1 className="text-white text-center py-3 text-2xl mb-3">
                        Assigned Grievances
                    </h1>
                    <AssignedGrievancesTable />
                </div>)}
            <div className="relative min-h-[60vh] overflow-x-auto sm:rounded-lg border-2 border-yellow-500">
                <h1 className="text-white text-center py-3 text-2xl mb-3">
                    Your Grievances
                </h1>
                <UserGrievancesTable />
            </div>
        </div>
    );
}