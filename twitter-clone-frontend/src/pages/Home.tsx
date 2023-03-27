import React from "react";
import SideBar from "../component/SideBar";

function Home() {
  return (
    <div className="w-8/12 mx-auto">
      <div className="w-1/5 fixed h-full">
        <SideBar />
      </div>
    </div>
  );
}

export default Home;
