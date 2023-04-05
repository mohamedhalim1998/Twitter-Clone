import React from "react";
import { Link } from "react-router-dom";
import {
  BlockIcon,
  LikeIcon,
  MoreIconWithoutCircle,
  PinnedIcon,
  ReplayIcon,
  RetweetIcon,
  UnFollowIcon,
  ViewsIcon,
} from "./Icons";
import DropDownMenu from "./DropDownMenu";
import { Divider } from "./Divider";
import Tweet from "../model/Tweet";

export const TweetCard = ({
  tweet,
  pinned = false,
}: {
  tweet?: Tweet;
  pinned?: boolean;
}) => {
  return (
    <div className="py-5 hover:bg-gray-100 px-6">
      <Divider />
      {pinned && (
        <p className="text-xs font-semibold text-gray-500">
          <PinnedIcon className="w-4 inline-block m-2" />
          {tweet?.includes?.users[0].username} Retweeted
        </p>
      )}
      {tweet?.tweetRefrence?.refType === "RETWEET" && (
        <Link
          to={`tweet/${tweet.includes?.tweets[0].id}`}
          className="flex flex-row text-gray-400 gap-3 mx-6 py-2"
        >
          <RetweetIcon className="w-4" />
          <p className="text-xs font-semibold">
            {tweet.includes?.users[0].username} Retweeted
          </p>
        </Link>
      )}

      <div className="flex flex-row">
        <div
          className="rounded-full bg-center w-10 h-10 bg-cover"
          style={{
            backgroundImage: ` url(${tweet?.includes?.users[0].profileImageUrl})`,
          }}
        />
        <div className="flex flex-col w-full px-5 gap-2">
          <div className="flex flex-row gap-1">
            <h4 className="font-semibold text-sm text-gray-800">
              {tweet?.includes?.users[0].fullname}
            </h4>
            <h5 className="text-sm text-gray-500">
              @{tweet?.includes?.users[0].username}
            </h5>
            <h5 className="text-sm text-gray-500">.</h5>
            <h5 className="text-sm text-gray-500">11h</h5>
            <div className="ml-auto">
              <DropDownMenu
                source={<MoreIconWithoutCircle />}
                items={[
                  {
                    icon: <UnFollowIcon className="w-8 pr-4" />,
                    name: "unfollow @".concat(
                      "" + tweet?.includes?.users[0].username
                    ),
                    onClick: () => {},
                  },
                  {
                    icon: <BlockIcon className="w-8 pr-4" />,
                    name: "Block @".concat(
                      "" + tweet?.includes?.users[0].username
                    ),
                    onClick: () => {},
                  },
                ]}
              />
            </div>
          </div>
          <p className="text-base text-gray-700">{tweet?.text}</p>
          {tweet?.attachment?.type == "IMAGE" && (
            <img
              className="rounded-lg w-full max-h-96 mx-auto object-cover object-center"
              src={tweet.includes?.media[0].url}
            />
          )}

          <TweetActionBar tweet={tweet} />
        </div>
      </div>
    </div>
  );
};
const TweetActionBar = ({ tweet }: { tweet?: Tweet }) => {
  return (
    <div className="flex flex-row w-4/5 text-gray-500 py-2 justify-between">
      <div className="flex flex-row cursor-pointer ">
        <ReplayIcon className="pr-2 w-6 hover:bg-gray-300 mx-auto px-auto rounded-full" />
        <p className="text-sm">{tweet?.replays}</p>
      </div>
      <div className="flex flex-row cursor-pointer">
        <RetweetIcon className="pr-2 w-6 text-gray-500 hover:bg-gray-300 mx-auto px-auto rounded-full" />
        <p className="text-sm">{tweet?.retweet}</p>
      </div>
      <div className="flex flex-row cursor-pointer ">
        <LikeIcon className="pr-2 w-6 hover:bg-gray-300 mx-auto px-auto rounded-full" />
        <p className="text-sm">{tweet?.likes}</p>
      </div>
    </div>
  );
};
