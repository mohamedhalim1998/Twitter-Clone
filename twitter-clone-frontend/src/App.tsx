import React, { Fragment } from "react";
import logo from "./logo.svg";
import "./App.css";
import { Outlet, Route, Routes } from "react-router-dom";
import SignupPage from "./pages/SignupPage";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import ProtectedRoute from "./component/ProtectedRoute";
import Home from "./pages/Home";
import LoginPage from "./pages/LoginPage";
import ProfilePage from "./pages/ProfilePage";
import MessagesPage from "./pages/MessagesPage";
import BasicLayout from "./component/BasicLayout";

function App() {
  return (
    <Fragment>
      <Routes>
        <Route path="/signup" element={<SignupPage />}></Route>
        <Route path="/login" element={<LoginPage />}></Route>
        <Route element={<ProtectedRoute />}>
          <Route element={<BasicLayout />}>
            <Route path="/:username" element={<ProfilePage />} />
            <Route path="/" element={<Home />} />
          </Route>
        </Route>
      </Routes>
      <ToastContainer />
    </Fragment>
  );
}

export default App;
