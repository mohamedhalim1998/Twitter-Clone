import React from "react";
import { Link } from "react-router-dom";

export const SideBarItem = (props: {
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
