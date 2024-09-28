import useAuth from "../hooks/useAuth"

export default function Home() {

    const {user, email} = useAuth()
    const { username, id, role } = user || {}

    return (
        <div className="min-h-[90vh] mx-3 my-2 rounded-lg bg-gray-900 flex flex-col justify-center py-12 sm:px-6 lg:px-8 ">
            <div className="mt-8 sm:mx-auto sm:w-full sm:max-w-md z-1">
                <div className="bg-gray-500 py-8 px-4 shadow sm:rounded-lg sm:px-10">
                    <h2 className="mt-6 mb-12 text-center text-4xl font-light text-gray-100">
                        Welcome to the Home Page, {username},
                    </h2>
                </div>
            </div>
        </div>
    )
}