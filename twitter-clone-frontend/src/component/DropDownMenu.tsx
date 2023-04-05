import { Popper, Paper, MenuItem, Menu } from "@mui/material";
import React from "react";

export interface MenuItemParam {
  icon?: JSX.Element;
  name: JSX.Element | string;
  onClick: () => void;
}
function DropDownMenu(props: {
  source: JSX.Element;
  items?: MenuItemParam[];
  header?: JSX.Element;
}) {
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
        {props.source}
      </div>
      <Menu
        id="demo-positioned-menu"
        aria-labelledby="demo-positioned-button"
        anchorEl={anchorEl}
        open={open}
        onClose={handleClose}
        anchorOrigin={{
          vertical: "bottom",
          horizontal: "right",
        }}
        transformOrigin={{
          vertical: "top",

          horizontal: "right",
        }}
      >
        {props.header}
        {props.items?.map((item, i) => {
          return (
            <MenuItem
              key={i}
              onClick={() => {
                item.onClick();
                handleClose();
              }}
            >
              <div className="flex flex-row">
                {item.icon}
                {item.name}
              </div>
            </MenuItem>
          );
        })}
      </Menu>
    </div>
  );
}

export default DropDownMenu;
