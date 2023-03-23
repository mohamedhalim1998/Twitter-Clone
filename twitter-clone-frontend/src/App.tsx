import React from "react";
import logo from "./logo.svg";
import "./App.css";
import { Route, Routes } from "react-router-dom";
import SignupPage from "./pages/SignupPage";

function App() {
  return (
    <Routes>
      <Route path="/signup" element={<SignupPage />}></Route>
    </Routes>
  );
}

export default App;
