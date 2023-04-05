import React from "react";

const TweetNavItem = (props: { name: string; selected?: boolean; }) => {
  return (
    <div className="hover:bg-gray-100 w-full cursor-pointer flex-grow ">
      <p className={"w-fit mx-auto my-4 ".concat(props.selected ? "text-gray-800" : "")}>{props.name}</p>
      {props.selected && (
        <div className="bg-theme h-1 rounded-lg w-3/5 mx-auto  cursor-pointer"></div>
      )}
    </div>
  );
};
export function TweetNavBar() {
  return <div className="flex flex-row justify-between font-semibold text-gray-500 my-5 text-center gap-1">
    <TweetNavItem name="Tweets" selected />
    <TweetNavItem name="Replies" />
    <TweetNavItem name="Media" />
    <TweetNavItem name="Likes" />
  </div>;
}
