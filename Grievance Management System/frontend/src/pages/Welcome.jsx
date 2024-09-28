import { Link } from "react-router-dom";

export default function Welcome() {
    return (
        <div className="h-screen flex flex-col items-center text-white">
            <div className="relative p-40 top-0 flex flex-col items-center justify-center w-full" style={{ backgroundImage: "url('https://picsum.photos/id/491/1500/460')", backgroundSize: "cover", backgroundPosition: "center", height: "50vh", backgroundRepeat: "no-repeat" }}>
                <div className="absolute inset-0 bg-black opacity-50"></div> {/* Overlay */}
                <h1 className="relative text-5xl mb-3 font-semibold">Welcome to Grievance Management System.</h1>
            </div>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4 m-10 w-full">
                <div className="flex flex-col justify-center items-center p-4">
                    <p className="mb-4 text-2xl">A one stop solution for all your grievances,</p>
                    <p className="text-2xl mb-4">here you can register your grievances and track their status.</p>
                    <p className="text-xl"><Link to='/login' className="underline font-bold text-yellow-400">Login</Link> to get started.</p>
                </div>
                <div className="relative flex justify-center items-center">
                    <img src="https://picsum.photos/id/9/600/400" alt="Dimmed" className="w-3/5 h-auto object-cover max-w-600 max-h-400" />
                    <div className="absolute inset-0 bg-gray-500 opacity-30"></div> {/* Overlay */}
                </div>
            </div>
            
        </div>
    )
}