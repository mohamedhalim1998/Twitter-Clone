import React from "react";
import { number } from "yup";
import { FollowButton } from "./FollowButton";
import { MoreIconWithoutCircle, SearchIcon } from "./Icons";
import ProfilePanal from "./ProfilePanal";

function RightBar() {
  return (
    <div className="flex flex-col my-5 w-full gap-3">
      <SearchBar />
      <WhatHappening />
      <WhoToFollow />
    </div>
  );
}
const SearchBar = () => {
  return (
    <div className="flex flex-row rounded-3xl bg-gray-100 w-full py-1 border h-fit">
      <SearchIcon />
      <input
        type="text"
        placeholder="Search Twitter"
        className="border-none focus:outline-none text-base w-full bg-gray-100"
      />
    </div>
  );
};

const WhatHappening = () => {
  return (
    <div className="flex flex-col bg-gray-100 gap-5 p-3 rounded-sm">
      <h2 className="font-bold text-gray-800 text-2xl">What&#39;s happening</h2>
      <HashTrend tag="خالد يوسف" location="Egypt" tweetNumber={100} />
      <HashTrend tag="خالد يوسف" location="Egypt" tweetNumber={100} />
      <HashTrend tag="خالد يوسف" location="Egypt" tweetNumber={100} />
      <HashTrend tag="خالد يوسف" location="Egypt" tweetNumber={100} />
      <p className="cursor-pointer text-theme hover:text-theme-hover w-full py-2 hover:bg-gray-200 p-2">
        Show More
      </p>
    </div>
  );
};

const HashTrend = (props: {
  tag: string;
  location: string;
  tweetNumber: number;
}) => {
  return (
    <div className="flex flex-col text-gray-700 text-sm hover:bg-gray-200 cursor-pointer p-2">
      <div className="flex flex-row w-full">
        <p>{`Trending in ${props.location}`}</p>
        <MoreIconWithoutCircle fill="#374151" />
      </div>
      <p dir="auto" className="text-gray-900 font-semibold  text-base">
        {props.tag}
      </p>
      <p>{`${props.tweetNumber} Tweets`}</p>
    </div>
  );
};

const WhoToFollow = () => {
  return (
    <div className="flex flex-col bg-gray-100 gap-5 p-3 rounded-sm">
      <h2 className="font-bold text-gray-800 text-2xl">Who to follow</h2>
      <ProfilePanal actionElement={FollowButton()} />
      <ProfilePanal actionElement={FollowButton()} />
      <p className="cursor-pointer text-theme hover:text-theme-hover w-full py-2 hover:bg-gray-200 p-2">
        Show More
      </p>
    </div>
  );
};

export default RightBar;
