import moment from "moment";
import React, { useEffect } from "react";
import RightBar from "../component/RightBar";
import SideBar from "../component/SideBar";
import TweetForm, { TweetFormParams } from "../component/TweetForm";
import { useAppDispatch, useAppSelector } from "../store/hooks";
import { postTweet } from "../store/TweetReducer";
import { TweetCard } from "../component/TweetCard";
import { Divider } from "../component/Divider";
import { loadProfile, ProfileState, updateProfileLoading } from "../store/ProfileReducer";
import { RootState } from "../store/Store";

function Home() {
  const dispatch = useAppDispatch();


  return (
    <div className="w-7/10 mx-auto grid grid-cols-11">
      <div className="col-span-2">
        <SideBar />
      </div>
      <div className="flex flex-col col-span-6 border-x border-gray-100 gap-3 ">
        <h1 className="text-2xl font-bold text-gray-800">Home</h1>
        <Divider />
        <TweetForm
          onSubmit={(data: TweetFormParams) => {
            dispatch(
              postTweet(
                data.text,
                data.isPoll
                  ? {
                      options: data.pollOptions,
                      duration: moment()
                        .day(data.pollLengthDays!!)
                        .hour(data.pollLengthHours!!)
                        .minute(data.pollLengthMinute!!)
                        .valueOf(),
                    }
                  : undefined,
                data.hasMedia ? data.media : undefined
              )
            );
          }}
        />
      </div>
      <div className="col-span-3">
        <RightBar />
      </div>
    </div>
  );
}
export default Home;
