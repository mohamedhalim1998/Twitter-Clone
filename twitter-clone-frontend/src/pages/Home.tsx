import React, { useEffect } from "react";
import RightBar from "../component/RightBar";
import SideBar from "../component/SideBar";
import TweetForm from "../component/TweetForm";
import { useAppDispatch, useAppSelector } from "../store/hooks";
import { TweetCard } from "../component/TweetCard";
import { Divider } from "../component/Divider";
import { RootState } from "../store/Store";
import {
  FeedState,
  getUserFeed,
  updateFeedLoading,
} from "../store/FeedReducer";

function Home() {
  const dispatch = useAppDispatch();
  const feed: FeedState = useAppSelector((state: RootState) => state.feed);
  useEffect(() => {
    if (!feed.loading && feed.tweets.length === 0) {
      dispatch(updateFeedLoading(true));
      dispatch(getUserFeed());
    }
  }, []);

  return (
    <div className="w-7/10 mx-auto grid grid-cols-11">
      <div className="col-span-2">
        <SideBar />
      </div>
      <div className="flex flex-col col-span-6 border-x border-gray-100 gap-3 ">
        <h1 className="text-2xl font-bold text-gray-800">Home</h1>
        <Divider />
        <TweetForm
        />
        {feed.tweets.map((tweet, i) => {
          return <TweetCard tweet={tweet} key={i} />;
        })}
      </div>
      <div className="col-span-3">
        <RightBar />
      </div>
    </div>
  );
}
export default Home;
