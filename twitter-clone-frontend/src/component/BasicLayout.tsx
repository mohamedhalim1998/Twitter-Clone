import { Divider } from "@mui/material";
import React from "react";
import { Outlet } from "react-router-dom";
import RightBar from "./RightBar";
import SideBar from "./SideBar";
import { TweetCard } from "./TweetCard";
import TweetForm from "./TweetForm";

const BaiscLayout = ({ hideRightBar }: { hideRightBar?: boolean }) => {
  return (
    <div className="w-7/10 mx-auto grid grid-cols-11">
      <div className="col-span-2">
        <SideBar />
      </div>
      <Outlet />
      <div className="col-span-3" hidden={hideRightBar}>
        <RightBar />
      </div>
    </div>
  );
};

export default BaiscLayout;
