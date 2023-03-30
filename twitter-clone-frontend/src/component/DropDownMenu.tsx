import { Popper, Paper, MenuItem } from "@mui/material";
import React from "react";

export interface MenuItemParam {
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
      <Popper open={open} anchorEl={anchorEl}>
        {props.header}
        <Paper>
          {props.items?.map((item) => {
            return <MenuItem onClick={item.onClick}>{item.name}</MenuItem>;
          })}
        </Paper>
      </Popper>
    </div>
  );
}

export default DropDownMenu;
