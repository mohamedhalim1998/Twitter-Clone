import React, { useState } from "react";
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
import Profile from "../model/Profile";
import Media from "../model/Media";
import moment from "moment";
import { useAppDispatch } from "../store/hooks";
import { likeTweet, replayTweet, reTweet } from "../store/TweetReducer";
import ReplayDialog from "./ReplayDialog";
import { TweetFormParams } from "./TweetForm";

export const TweetCard = ({
  tweet,
  pinned = false,
}: {
  tweet?: Tweet;
  pinned?: boolean;
}) => {
  const profile =
    tweet?.tweetRefrence?.refType === "RETWEET"
      ? tweet.includes?.users[1]
      : tweet?.includes?.users[0];
  return (
    <div>
      {tweet?.tweetRefrence?.refType == "REPLAY" && (
        <TweetInfo
          tweet={tweet.includes?.tweets[0]}
          profile={tweet.includes?.users[1]}
          showReplayLine
          media={tweet?.includes?.media[tweet?.includes?.media.length - 1]}
        />
      )}
      <TweetInfo
        tweet={tweet}
        profile={profile}
        pinned={pinned}
        media={tweet?.includes?.media[0]}
      />
    </div>
  );
};
const TweetActionBar = ({ tweet, profile }: { tweet?: Tweet, profile?: Profile }) => {
  const dispatch = useAppDispatch();
  const [showReplayDialog, setShowReplayDialog] = useState<boolean>(false);
  return (
    <div className="flex flex-row w-4/5 text-gray-500 py-2 justify-between">
      <div className="flex flex-row cursor-pointer ">
        <ReplayIcon
          className="pr-2 w-6 hover:bg-gray-300 mx-auto px-auto rounded-full"
          onClick={() => {
            setShowReplayDialog(true);
          }}
        />
        <p className="text-sm">{tweet?.replays}</p>
      </div>
      <div className="flex flex-row cursor-pointer">
        <RetweetIcon
          className="pr-2 w-6 text-gray-500 hover:bg-gray-300 mx-auto px-auto rounded-full"
          onClick={() => {
            dispatch(reTweet(tweet?.id));
          }}
        />
        <p className="text-sm">{tweet?.retweet}</p>
      </div>
      <div className="flex flex-row cursor-pointer ">
        <LikeIcon
          className="pr-2 w-6 hover:bg-gray-300 mx-auto px-auto rounded-full"
          onClick={() => {
            dispatch(likeTweet(tweet?.id));
          }}
        />
        <p className="text-sm">{tweet?.likes}</p>
      </div>
      <ReplayDialog
        tweet={tweet}
        profile={profile}
        isOpen={showReplayDialog}
        onClose={() => {
          setShowReplayDialog(false);
        }}
        onSubmit={(data) => {
          dispatch(
            replayTweet(
              tweet!.id,
              data.text,
              data.hasMedia ? data.media : undefined
            )
          );
          setShowReplayDialog(false);
        }}
      />
    </div>
  );
};

export const TweetInfo = ({
  tweet,
  media,
  profile,
  pinned,
  showReplayLine,
}: {
  tweet?: Tweet;
  profile?: Profile;
  media?: Media;
  pinned?: boolean;
  showReplayLine?: boolean;
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
        <div className="flex flex-col">
          <div
            className="rounded-full bg-center w-10 h-10 bg-cover"
            style={{
              backgroundImage: ` url(${profile?.profileImageUrl})`,
            }}
          />
          {showReplayLine && (
            <div className="w-0.5 flex-grow bg-gray-300 mx-auto"> </div>
          )}
        </div>
        <div className="flex flex-col w-full px-5 gap-2">
          <div className="flex flex-row gap-1">
            <h4 className="font-semibold text-sm text-gray-800">
              {profile?.fullname}
            </h4>
            <h5 className="text-sm text-gray-500">@{profile?.username}</h5>
            <h5 className="text-sm text-gray-500">.</h5>
            <h5 className="text-sm text-gray-500">
              {moment(tweet?.createdDate).format("YYYY-MM-DD")}
            </h5>
            <div className="ml-auto">
              <DropDownMenu
                source={<MoreIconWithoutCircle />}
                items={[
                  {
                    icon: <UnFollowIcon className="w-8 pr-4" />,
                    name: "unfollow @".concat("" + profile?.username),
                    onClick: () => {},
                  },
                  {
                    icon: <BlockIcon className="w-8 pr-4" />,
                    name: "Block @".concat("" + profile?.username),
                    onClick: () => {},
                  },
                ]}
              />
            </div>
          </div>
          <p className="text-base text-gray-700">{tweet?.text}</p>
          {tweet?.attachment?.type == "MEDIA" &&
            (tweet.includes?.media[0].type == "IMAGE" ? (
              <img
                className="rounded-lg w-full max-h-96 mx-auto object-cover object-center"
                src={media?.url}
              />
            ) : (
              <video
                className="object-cover w-auto max-h-96 mx-auto "
                controls
                src={media?.url}
              ></video>
            ))}

          <TweetActionBar tweet={tweet} profile={profile} />
        </div>
      </div>
    </div>
  );
};
