import React, { useEffect, useState } from "react";
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
import { useNavigate } from "react-router-dom";
import { useAppDispatch } from "../store/hooks";
import { logout } from "../store/AuthReducer";
import DropDownMenu, { MenuItemParam } from "./DropDownMenu";
import { stat } from "fs";
import { SideBarItem } from "./SideBarItem";
import { TweetDialog, TweetDialogForm } from "./TweetDialog";
import { postTweet } from "../store/TweetReducer";
import moment from "moment";

export function SideBar() {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const [dialogOpen, setDialogOpen] = useState<boolean>(false);
  useEffect(() => {
    const handleKeyDown = (event: KeyboardEvent) => {
      if (event.keyCode === 27) {
        setDialogOpen(false);
      }
    };

    document.addEventListener("keydown", handleKeyDown);

    return () => {
      document.removeEventListener("keydown", handleKeyDown);
    };
  }, []);
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
      <TweetButton
        onClick={() => {
          setDialogOpen(true);
        }}
      />
      <TweetDialog
        isOpen={dialogOpen}
        onClose={() => {
          setDialogOpen(false);
        }}
        onSubmit={(data: TweetDialogForm) => {
          dispatch(
            postTweet(
              data.text,
              data.isPoll
                ? {
                    options: data.pollOptions,
                    duration: moment()
                      .day(data.pollLengthDays!!)
                      .hour(data.pollLengthHours!!)
                      .minute(data.pollLengthMinute!!)
                      .valueOf(),
                  }
                : undefined,
              data.hasMedia ? data.media : undefined
            )
          );
        }}
      />
      <ProfileMenu
        logout={() => {
          dispatch(logout());
          navigate("/login");
        }}
      />
    </div>
  );
}

export const TweetButton: React.FC<React.HTMLProps<HTMLDivElement>> = (
  props
) => {
  return (
    <div
      className="bg-theme text-white text-center text-2xl p-2 rounded-3xl w-4/5 hover:bg-theme-hover cursor-pointer"
      {...props}
    >
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
  const items: MenuItemParam[] = [{ name: "logout", onClick: logout }];
  return <DropDownMenu source={Profile()} items={items} />;
}

export default SideBar;
