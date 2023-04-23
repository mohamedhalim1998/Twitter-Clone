import React from "react";
import { SearchIcon } from "./Icons";

export const SearchBar = ({ label }: { label: string }) => {
  return (
    <div className="flex flex-row rounded-3xl bg-gray-100 w-full py-1 border h-fit">
      <SearchIcon />
      <input
        type="text"
        placeholder={label}
        className="border-none focus:outline-none text-base w-full bg-gray-100"
      />
    </div>
  );
};
