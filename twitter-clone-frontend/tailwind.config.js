/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{js,jsx,ts,tsx}"],
  theme: {
    extend: {
      width: {
        "7/10": "70%",
      },
      minHeight: {
        "52": "13rem",
      },
      colors: {
        theme: "#1D9BF0",
        "theme-hover": "#1D9BF0",
      },
      borderRadius: {
        "4xl": "3rem",
      },
    },
  },
  plugins: [],
};
