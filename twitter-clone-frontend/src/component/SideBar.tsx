import React from "react";
import {
  BookmarkIcon,
  ExploreIcon,
  HomeIcon,
  Logo,
  MessageIcon,
  MoreIcon,
  MoreIconWithoutCircle,
  NotificationIcon,
  ProfileIcon,
} from "./Icons";
import {
  Button,
  ClickAwayListener,
  Grow,
  MenuItem,
  MenuList,
  Paper,
  Popper,
  Stack,
} from "@mui/material";

function SideBar() {
  return (
    <div className="flex flex-col m-5 gap-3 h-full">
      <Logo />
      <SideBarItem name="Home" icon={HomeIcon()} selected />
      <SideBarItem name="Explore" icon={ExploreIcon()} />
      <SideBarItem name="Notification" icon={NotificationIcon()} />
      <SideBarItem name="Message" icon={MessageIcon()} />
      <SideBarItem name="Bookmarks" icon={BookmarkIcon()} />
      <SideBarItem name="Profile" icon={ProfileIcon()} />
      <SideBarItem name="More" icon={MoreIcon()} />
      <TweetButton />
      <MenuListComposition element={Profile()} />
      {/* <Profile /> */}
    </div>
  );
}

const SideBarItem = (props: {
  name: string;
  icon: JSX.Element;
  selected?: boolean;
}) => {
  return (
    <div className="flex flex-row gap-5 p-3 w-fit rounded-3xl text-gray-900 hover:bg-gray-300 cursor-pointer">
      {props.icon}
      <h2
        className={"text-xl py-auto my-auto".concat(
          props.selected ? "font-semibold" : ""
        )}
      >
        {props.name}
      </h2>
    </div>
  );
};

const TweetButton = () => {
  return (
    <div className="bg-theme text-white text-center text-2xl p-2 rounded-3xl w-full hover:bg-theme-hover cursor-pointer">
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

const MenuListComposition = (props: { element: JSX.Element }) => {
  const [open, setOpen] = React.useState(false);
  const anchorRef = React.useRef<HTMLButtonElement>(null);

  const handleToggle = () => {
    setOpen((prevOpen) => !prevOpen);
  };

  const handleClose = (event: Event | React.SyntheticEvent) => {
    if (
      anchorRef.current &&
      anchorRef.current.contains(event.target as HTMLElement)
    ) {
      return;
    }

    setOpen(false);
  };

  function handleListKeyDown(event: React.KeyboardEvent) {
    if (event.key === "Tab") {
      event.preventDefault();
      setOpen(false);
    } else if (event.key === "Escape") {
      setOpen(false);
    }
  }

  const prevOpen = React.useRef(open);
  React.useEffect(() => {
    if (prevOpen.current === true && open === false) {
      anchorRef.current!.focus();
    }

    prevOpen.current = open;
  }, [open]);

  return (
    <Stack direction="row" spacing={2}>
      <div>
        <Button
          ref={anchorRef}
          id="composition-button"
          aria-controls={open ? "composition-menu" : undefined}
          aria-expanded={open ? "true" : undefined}
          aria-haspopup="true"
          onClick={handleToggle}
        >
          {props.element}
        </Button>
        <Popper
          open={open}
          anchorEl={anchorRef.current}
          role={undefined}
          placement="bottom-start"
          transition
          disablePortal
        >
          {({ TransitionProps, placement }) => (
            <Grow
              {...TransitionProps}
              style={{
                transformOrigin:
                  placement === "bottom-start" ? "left top" : "left bottom",
              }}
            >
              <Paper>
                <ClickAwayListener onClickAway={handleClose}>
                  <MenuList
                    autoFocusItem={open}
                    id="composition-menu"
                    aria-labelledby="composition-button"
                    onKeyDown={handleListKeyDown}
                  >
                    <MenuItem>Log out @mohamedhalim98</MenuItem>
                  </MenuList>
                </ClickAwayListener>
              </Paper>
            </Grow>
          )}
        </Popper>
      </div>
    </Stack>
  );
};
export default SideBar;
