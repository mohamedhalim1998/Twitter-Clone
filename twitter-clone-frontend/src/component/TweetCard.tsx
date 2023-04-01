import React from "react";
import Tweet from "../model/Tweet";
import { Link } from "react-router-dom";
import {
  BlockIcon,
  LikeIcon, MoreIconWithoutCircle,
  ReplayIcon,
  RetweetIcon, UnFollowIcon,
  ViewsIcon
} from "./Icons";
import DropDownMenu from "./DropDownMenu";
import { Divider } from "./Divider";

export const TweetCard = ({ tweet }: { tweet?: Tweet; }) => {
  return (
    <div className="py-5 hover:bg-gray-100 px-6">
      <Divider />
      <Link to="#" className="flex flex-row text-gray-400 gap-3 mx-6 py-2">
        <RetweetIcon className="w-4" />
        <p className="text-xs font-semibold">mohamedhalim98 Retweeted</p>
      </Link>
      <div className="flex flex-row">
        <div
          className="rounded-full bg-center w-10 h-10 bg-cover"
          style={{
            backgroundImage: ` url(https://pbs.twimg.com/profile_images/772538396682092545/OmC7OaLV_400x400.jpg)`,
          }} />
        <div className="flex flex-col w-full px-5 gap-2">
          <div className="flex flex-row gap-1">
            <h4 className="font-semibold text-sm text-gray-800">
              Mohamed Halim
            </h4>
            <h5 className="text-sm text-gray-500">@mohamedhalim98</h5>
            <h5 className="text-sm text-gray-500">.</h5>
            <h5 className="text-sm text-gray-500">11h</h5>
            <div className="ml-auto">
              <DropDownMenu
                source={<MoreIconWithoutCircle />}
                items={[
                  {
                    icon: <UnFollowIcon className="w-8 pr-4" />,
                    name: "unfollow @mohamedhalim98",
                    onClick: () => { },
                  },
                  {
                    icon: <BlockIcon className="w-8 pr-4" />,
                    name: "Block @mohamedhalim98",
                    onClick: () => { },
                  },
                ]} />
            </div>
          </div>
          <p className="text-base text-gray-700">
            This Caribbean Island Has a Famous Luxury Hotel, a Waterfront
            Airport, and the 'Most Extreme Beach in the World'
          </p>

          <img
            className="rounded-lg w-full max-h-96 mx-auto object-cover object-center"
            src="https://pbs.twimg.com/media/FsoMrnAWwAIvntV?format=jpg&name=medium" />
          <TweetActionBar />
        </div>
      </div>
    </div>
  );
};
const TweetActionBar = () => {
  return (
    <div className="flex flex-row w-4/5 text-gray-500 py-2 justify-between">
      <div className="flex flex-row cursor-pointer ">
        <ReplayIcon className="pr-2 w-6 hover:bg-gray-300 mx-auto px-auto rounded-full" />
        <p className="text-sm">10</p>
      </div>
      <div className="flex flex-row cursor-pointer">
        <RetweetIcon className="pr-2 w-6 text-gray-500 hover:bg-gray-300 mx-auto px-auto rounded-full" />
        <p className="text-sm">10</p>
      </div>
      <div className="flex flex-row cursor-pointer ">
        <LikeIcon className="pr-2 w-6 hover:bg-gray-300 mx-auto px-auto rounded-full" />
        <p className="text-sm">10</p>
      </div>
      <div className="flex flex-row cursor-pointer ">
        <ViewsIcon className="pr-2 w-6 hover:bg-gray-300 mx-auto px-auto rounded-full" />
        <p className="text-sm">10</p>
      </div>
    </div>
  );
};
