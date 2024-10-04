/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      boxShadow: {
        'white': '4px 4px 6px rgba(255, 255, 255, 0.1), -4px -4px 6px rgba(255, 255, 255, 0.1)',
      },
    },
  },
  plugins: [],
}

