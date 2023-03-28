import React from "react";
import RightBar from "../component/RightBar";
import SideBar from "../component/SideBar";

function Home() {
  return (
    <div className="w-7/10 mx-auto flex flex-row">
      <div className="w-1/5 fixed h-full">
        <SideBar />
      </div>
      <div className="flex-grow"></div>
      <div className="w-1/4 h-full ml-auto">
        <RightBar />
      </div>
    </div>
  );
}

export default Home;
