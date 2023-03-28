import React from "react";
import {
  BookmarkIcon,
  ExploreIcon,
  HomeIcon,
  Logo,
  MessageIcon,
  MoreIconWithoutCircle,
  NotificationIcon,
  ProfileIcon,
} from "./Icons";
import { MenuItem, Paper, Popper } from "@mui/material";
import { Link, useNavigate } from "react-router-dom";
import { useAppDispatch } from "../store/hooks";
import { logout } from "../store/AuthReducer";

function SideBar() {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  return (
    <div className="flex flex-col m-5 gap-3 h-full">
      <Logo />
      <SideBarItem name="Home" url="/" icon={HomeIcon()} selected />
      <SideBarItem name="Explore" url="/explore" icon={ExploreIcon()} />
      <SideBarItem
        name="Notification"
        url="/notifications"
        icon={NotificationIcon()}
      />
      <SideBarItem name="Message" url="/messages" icon={MessageIcon()} />
      <SideBarItem name="Bookmarks" url="/bookmarks" icon={BookmarkIcon()} />
      <SideBarItem name="Profile" url="/mohamedhalim98" icon={ProfileIcon()} />
      <TweetButton />
      <ProfileMenu
        logout={() => {
          dispatch(logout());
          navigate("/login");
        }}
      />
    </div>
  );
}

const SideBarItem = (props: {
  name: string;
  url: string;
  icon: JSX.Element;
  selected?: boolean;
}) => {
  return (
    <Link
      to={props.url}
      className="flex flex-row gap-5 p-3 w-fit rounded-3xl text-gray-900 hover:bg-gray-300 cursor-pointer"
    >
      {props.icon}
      <h2
        className={"text-xl py-auto my-auto".concat(
          props.selected ? "font-semibold" : ""
        )}
      >
        {props.name}
      </h2>
    </Link>
  );
};

const TweetButton = () => {
  return (
    <div className="bg-theme text-white text-center text-2xl p-2 rounded-3xl w-4/5 hover:bg-theme-hover cursor-pointer">
      Tweet
    </div>
  );
};

const Profile = () => {
  return (
    <div className="flex flex-row gap-2 p-3 hover:bg-gray-300 cursor-pointer rounded-4xl">
      <div
        className="rounded-full bg-center w-10 h-10 bg-cover"
        style={{
          backgroundImage: ` url(https://pbs.twimg.com/profile_images/772538396682092545/OmC7OaLV_400x400.jpg)`,
        }}
      />
      <div className="flex flex-col ">
        <h4 className="font-semibold text-sm text-gray-800">Mohamed Halim</h4>
        <h5 className="text-sm text-gray-500">@mohamedhalim98</h5>
      </div>
      <MoreIconWithoutCircle />
    </div>
  );
};

function ProfileMenu(props: { logout: () => void }) {
  const [anchorEl, setAnchorEl] = React.useState<null | HTMLElement>(null);
  const open = Boolean(anchorEl);
  const handleClick = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorEl(anchorEl ? null : event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };

  return (
    <div className="">
      <div
        id="basic-button"
        aria-controls={open ? "basic-menu" : undefined}
        aria-haspopup="true"
        aria-expanded={open ? "true" : undefined}
        onClick={handleClick}
      >
        <Profile />
      </div>
      <Popper open={open} anchorEl={anchorEl}>
        <Paper>
          <MenuItem
            onClick={() => {
              handleClose();
              props.logout();
            }}
          >
            Logout
          </MenuItem>
        </Paper>
      </Popper>
    </div>
  );
}
export default SideBar;
