import React, { Fragment } from "react";
import logo from "./logo.svg";
import "./App.css";
import { Route, Routes } from "react-router-dom";
import SignupPage from "./pages/SignupPage";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import ProtectedRoute from "./component/ProtectedRoute";
import Home from "./pages/Home";

function App() {
  return (
    <Fragment>
      <Routes>
        <Route path="/signup" element={<SignupPage />}></Route>
        <Route element={<ProtectedRoute />}>
          <Route path="/" element={<Home />} />
        </Route>
      </Routes>
      <ToastContainer />
    </Fragment>
  );
}

export default App;
