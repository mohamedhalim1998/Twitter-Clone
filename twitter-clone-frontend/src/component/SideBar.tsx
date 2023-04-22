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
import { useAppDispatch, useAppSelector } from "../store/hooks";
import { logout } from "../store/AuthReducer";
import DropDownMenu, { MenuItemParam } from "./DropDownMenu";
import { SideBarItem } from "./SideBarItem";
import { TweetDialog } from "./TweetDialog";
import {
  loadProfile,
  ProfileState,
  updateProfileLoading,
} from "../store/ProfileReducer";
import { RootState } from "../store/Store";
import Profile from "../model/Profile";

export function SideBar() {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const [dialogOpen, setDialogOpen] = useState<boolean>(false);
  const profile: ProfileState = useAppSelector(
    (state: RootState) => state.profile
  );
  useEffect(() => {
    if (!profile.loading && profile.profile == undefined) {
      dispatch(updateProfileLoading(true));
      dispatch(loadProfile());
    }
  }, []);
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
  if (profile.loading || profile.profile == undefined) {
    return <div>loading</div>;
  }
  return (
    <div className="fixed">
      <div className="flex flex-col my-5 w-full gap-3">
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
        <SideBarItem
          name="Profile"
          url={"/".concat(profile.profile?.username)}
          icon={ProfileIcon()}
        />
        <TweetButton
          onClick={() => {
            setDialogOpen(true);
          }}
        />
        <TweetDialog
          onSubmit={() => {
            setDialogOpen(false);
          }}
          isOpen={dialogOpen}
          onClose={() => {
            setDialogOpen(false);
          }}
        />
        <ProfileMenu
          profile={profile.profile}
          logout={() => {
            console.log("loging out");
            dispatch(logout());
            navigate("/login");
          }}
        />
      </div>
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

const ProfilePanal = (profile: Profile) => {
  return (
    <div className="flex flex-row gap-2 p-3 hover:bg-gray-300 cursor-pointer rounded-4xl ">
      <div
        className="rounded-full bg-center w-10 h-10 bg-cover"
        style={{
          backgroundImage: ` url(${profile.profileImageUrl})`,
        }}
      />
      <div className="flex flex-col ">
        <h4 className="font-semibold text-sm text-gray-800">
          {profile.fullname}
        </h4>
        <h5 className="text-sm text-gray-500">@{profile.username}</h5>
      </div>
      <MoreIconWithoutCircle />
    </div>
  );
};

function ProfileMenu(props: { logout: () => void; profile: Profile }) {
  const items: MenuItemParam[] = [
    {
      name: "logout",
      onClick: () => {
        props.logout();
      },
    },
  ];
  return <DropDownMenu source={ProfilePanal(props.profile)} items={items} />;
}

export default SideBar;
