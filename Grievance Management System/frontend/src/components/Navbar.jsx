import { Disclosure} from '@headlessui/react'
import { Bars3Icon, XMarkIcon, UserIcon, ArrowRightOnRectangleIcon } from '@heroicons/react/24/outline'
import { Link, useLocation, useNavigate } from 'react-router-dom'
import { useEffect, useState } from 'react'
import useAuth from '../hooks/useAuth'

const initialNavigation = [
    { name: 'Home', href: '/home', current: true },
    { name: 'Create', href: '/create', current: false },
    { name: 'Search', href: '/search', current: false },
    { name: 'Manage', href: '/manage', current: false },
]

function classNames(...classes) {
    return classes.filter(Boolean).join(' ')
}

export default function Example() {

    const [logoLink, setLogoLink] = useState('/')
    const {isAuthenticated, logout, user} = useAuth();  
    const [navigation, setNavigation] = useState(initialNavigation);
    const navigate = useNavigate()
    const location = useLocation()

    const handleNavigationClick = (name) => {
        setNavigation(navigation.map(item => ({
            ...item,
            current: item.name === name
        })))
    }

    useEffect(() => {
        setNavigation(n => n.map(item => ({
            ...item,
            current: item.href === location.pathname
        })))
    }, [location])

    const handleLogout = () => {
        try {
            logout()
            navigate('/')    
        } catch (error) {
            console.error(error)
        }
    }

    useEffect(() => {
        if (isAuthenticated) {
            setLogoLink('/home')
        } else {
            setLogoLink('/')
        }
    }, [isAuthenticated])

    return (
        <Disclosure as="nav" className="bg-gray-800">
            {({ open }) => (
                <>
                    <div className="mx-auto max-w-7xl px-2 sm:px-6 lg:px-8">
                        <div className="relative flex h-16 items-center justify-between">
                            <div className="absolute inset-y-0 left-0 flex items-center sm:hidden">
                                {/* Mobile menu button (hamburger/close icons) */}
                                {isAuthenticated && (
                                    <Disclosure.Button className="inline-flex items-center justify-center rounded-md p-2 text-gray-400 hover:bg-gray-700 hover:text-white focus:outline-none focus:ring-2 focus:ring-inset focus:ring-white">
                                        <span className="sr-only">Open main menu</span>
                                        {open ? (
                                            <XMarkIcon className="block h-6 w-6" aria-hidden="true" />
                                        ) : (
                                            <Bars3Icon className="block h-6 w-6" aria-hidden="true" />
                                        )}
                                    </Disclosure.Button>
                                )}
                            </div>
                            <div className="flex flex-1 items-center justify-center sm:items-stretch sm:justify-start">
                                {/* Logo Section (Always Visible) */}
                                <Link to={logoLink}>
                                    <div className="flex flex-shrink-0 items-center cursor-pointer">
                                        <img
                                            alt="Logo"
                                            src="/assets/helmet-icon.png"
                                            className="h-8 w-auto"
                                        />
                                        {
                                            !isAuthenticated && (
                                                <div className='text-2xl text-white p-4'>Grievance Management System</div>
                                            )
                                        }

                                    </div>
                                </Link>

                                {/* Conditionally render the rest of the navbar if authenticated */}
                                {isAuthenticated && (
                                    <div className="hidden sm:ml-6 sm:block">
                                        <div className="flex space-x-4">
                                            {navigation.map((item) => {

                                                if ((item.name === "Search" || item.name === "Manage") && !(user.role === "ADMIN" || user.role === "SUPERVISOR")) {
                                                    return null
                                                }
                                                
                                                return (
                                                <Link
                                                    key={item.name}
                                                    to={item.href}
                                                    onClick={() => handleNavigationClick(item.name)}
                                                    className={classNames(
                                                        item.current ? 'bg-gray-900 text-white' : 'text-gray-300 hover:bg-gray-700 hover:text-white',
                                                        'rounded-md px-3 py-2 text-sm font-medium',
                                                    )}
                                                >
                                                    {item.name}
                                                </Link>
                                            )})}
                                        </div>
                                    </div>
                                )}
                            </div>

                            {/* If authenticated, show profile and notification buttons */}
                            {isAuthenticated && (
                                <div className="absolute inset-y-0 right-0 flex items-center pr-2 sm:static sm:inset-auto sm:ml-6 sm:pr-0">
                                    {/* Profile dropdown */}
                                    <Link to='/profile'>
                                        <button
                                            type="button"
                                            className="relative rounded-full bg-gray-800 ml-3 p-1 text-gray-400 hover:text-white focus:outline-none focus:ring-2 focus:ring-white focus:ring-offset-2 focus:ring-offset-gray-800"
                                        >
                                            <span className="sr-only">Profile</span>
                                            <UserIcon className="h-6 w-6" aria-hidden="true" />
                                        </button>
                                    </Link>
                                    

                                    <button
                                        type="button" onClick={handleLogout}
                                        className="relative rounded-full bg-gray-800 ml-3 p-1 text-gray-400 hover:text-white focus:outline-none focus:ring-2 focus:ring-white focus:ring-offset-2 focus:ring-offset-gray-800"
                                    >
                                        <span className="sr-only">Logout</span>
                                        <ArrowRightOnRectangleIcon className="h-6 w-6" aria-hidden="true" />
                                    </button>

                                </div>
                            )}
                            {!isAuthenticated && (
                                <div className="absolute inset-y-0 right-0 flex items-center pr-2 sm:static sm:inset-auto sm:ml-6 sm:pr-0">
                                    <Link to='/login'>
                                        <button
                                            type="button"
                                            className="relative rounded-full bg-gray-800 ml-3 p-1 text-gray-400 hover:text-white focus:outline-none focus:ring-2 focus:ring-white focus:ring-offset-2 focus:ring-offset-gray-800 text-lg"
                                        >
                                            Login
                                        </button>
                                    </Link>

                                    <Link to="/register">
                                        <button
                                            type="button"
                                            className="relative rounded-full bg-gray-800 ml-3 p-1 text-gray-400 hover:text-white focus:outline-none focus:ring-2 focus:ring-white focus:ring-offset-2 focus:ring-offset-gray-800 text-lg"
                                        >
                                            Register
                                        </button>
                                    </Link>
                                </div>
                            )}
                        </div>
                    </div>

                    {/* Mobile menu (visible only if authenticated and toggled open) */}
                    {isAuthenticated && (
                        <Disclosure.Panel className="sm:hidden">
                            <div className="space-y-1 px-2 pb-3 pt-2">
                                {navigation.map((item) => {

                                    if ((item.name === "Search" || item.name === "Manage") && !(user.role === "ADMIN" || user.role === "SUPERVISOR")) {
                                        return null
                                    }
                                    
                                    return (
                                    <Disclosure.Button
                                        key={item.name}
                                        as="a"
                                        href={item.href}
                                        aria-current={item.current ? 'page' : undefined}
                                        className={classNames(
                                            item.current ? 'bg-gray-900 text-white' : 'text-gray-300 hover:bg-gray-700 hover:text-white',
                                            'block rounded-md px-3 py-2 text-base font-medium',
                                        )}
                                    >
                                        {item.name}
                                    </Disclosure.Button>
                                )})}
                            </div>
                        </Disclosure.Panel>
                    )}
                </>
            )}
        </Disclosure>
    )
}