import moment from "moment";
import React, { useEffect } from "react";
import { useParams } from "react-router-dom";
import { date } from "yup";
import { FollowButton } from "../component/FollowButton";
import { BackIcon, CalendarIcon, LocationIcon } from "../component/Icons";
import RightBar from "../component/RightBar";
import SideBar from "../component/SideBar";
import { TweetCard } from "../component/TweetCard";
import Profile from "../model/Profile";
import { useAppDispatch, useAppSelector } from "../store/hooks";
import {
  getPinnedTweet,
  loadProfile,
  ProfileState,
  updateProfileLoading,
} from "../store/ProfileReducer";
import { RootState } from "../store/Store";
import { TweetNavBar } from "../component/TweetNavBar";

function ProfilePage() {
  const { username } = useParams();
  const dispatch = useAppDispatch();
  const profile: ProfileState = useAppSelector(
    (state: RootState) => state.profile
  );
  useEffect(() => {
    if (!profile.loading) {
      dispatch(updateProfileLoading(true));
      dispatch(loadProfile(username!!));
    }
  }, []);
  useEffect(() => {
    if (profile.profile && profile.profile.pinnedTweetId != undefined) {
      dispatch(updateProfileLoading(true));
      dispatch(getPinnedTweet(profile.profile.pinnedTweetId));
    }
  }, [profile.profile]);
  if (profile.loading || profile.profile == undefined) {
    return <div>loading</div>;
  }
  return (
    <div className="w-2/3 mx-auto grid grid-cols-11">
      <div className="col-span-2">
        <SideBar />
      </div>
      <div className="flex flex-col col-span-6 border-x border-gray-100 gap-3 ">
        <ProfileInfo profile={profile.profile!} />
        <TweetCard tweet={profile.pinnedTweet} pinned />
      </div>
      <div className="col-span-3">
        <RightBar />
      </div>
    </div>
  );
}
const ProfileInfo = ({ profile }: { profile: Profile }) => {
  return (
    <div>
      <div id="cover">
        <div className="flex flex-row">
          <BackIcon className="w-10 p-2 rounded-full hover:bg-gray-100" />
          <div className="flex flex-col pl-4">
            <h1 className="text-2xl font-bold text-gray-800">
              {profile.fullname}
            </h1>
            <p className="text-sm text-gray-500">{profile.tweets} Tweets</p>
          </div>
        </div>
        <img
          className="max-h-52 outline-none min-h-52 border-0 object-cover object-center w-full"
          src={profile.coverImageUrl}
          alt=""
        />
      </div>
      <div className="relative mx-4 flex flex-row my-2">
        <div
          className="rounded-full bg-center w-40 h-40 bg-cover absolute -top-1/2 left-0 -translate-y-1/2 border-white border-4"
          style={{
            backgroundImage: ` url(${profile.profileImageUrl})`,
          }}
        />
        <div className="ml-auto">
          <FollowButton />
        </div>
      </div>
      <h1 className="text-2xl font-bold text-gray-800 mx-6 mt-6 ">
        {profile.fullname}
      </h1>
      <p className="text-sm text-gray-500 mx-6">{profile.username}</p>
      <p className="text-sm text-gray-800 mx-6">{profile.bio}</p>
      <div className="flex flex-row text-gray-500 gap-5 mx-2">
        <div className="flex flex-row gap-1">
          <LocationIcon className="w-10 p-2 text-gray-500" />
          <p className="my-auto">{profile.location}</p>
        </div>
        <div className="flex flex-row gap-1">
          <CalendarIcon className="w-10 p-2 " />
          <p className="my-auto">
            Joined {getMonth(profile.createdAt)} {getYear(profile.createdAt)}
          </p>
        </div>
      </div>
      <div className="flex flex-row text-gray-500 gap-5 text-sm mx-6">
        <p className="my-auto hover:underline cursor-pointer">
          <span className="text-gray-800">{profile.following}</span> Following
        </p>
        <p className="my-auto hover:underline cursor-pointer">
          {" "}
          <span className="text-gray-800">{profile.followers}</span> Followers
        </p>
      </div>

      {TweetNavBar()}
    </div>
  );
};
function getMonth(date: number) {
  return moment(date).format("MMMM");
}
function getYear(date: number) {
  return moment(date).year();
}

export default ProfilePage;
