import moment from "moment";
import { FollowButton } from "../component/FollowButton";
import { BackIcon, CalendarIcon, LocationIcon } from "../component/Icons";
import Profile from "../model/Profile";
import { useState } from "react";
import { EditProfileDialog } from "./EditProfileDialog";

export const ProfileInfo = ({
  profile, isUserProfile,
}: {
  profile: Profile;
  isUserProfile: boolean;
}) => {
  const [showEditDialog, setShowEditDialog] = useState<boolean>(false);
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
          alt="" />
      </div>
      <div className="relative mx-4 flex flex-row my-2">
        <div
          className="rounded-full bg-center w-40 h-40 bg-cover absolute -top-1/2 left-0 -translate-y-1/2 border-white border-4"
          style={{
            backgroundImage: ` url(${profile.profileImageUrl})`,
          }} />
        <div className="ml-auto">
          {isUserProfile ? (
            <div
              className="bg-gray-900 text-white text-center p-2  px-4 rounded-3xl  hover:bg-gray-800 cursor-pointer font-semibold"
              onClick={() => {
                setShowEditDialog(true);
              }}
            >
              Edit Profile
            </div>
          ) : (
            <FollowButton />
          )}
        </div>
      </div>
      <h1 className="text-2xl font-bold text-gray-800 mx-6 mt-6 ">
        {profile.fullname}
      </h1>
      <p className="text-sm text-gray-500 mx-6">@{profile.username}</p>
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
          <span className="text-gray-800">{profile.followers}</span> Followers
        </p>
      </div>
      <EditProfileDialog
        profile={profile}
        isOpen={showEditDialog}
        onClose={() => {
          setShowEditDialog(false);
        }} />
    </div>
  );
};
function getMonth(date: number) {
  return moment(date).format("MMMM");
}
function getYear(date: number) {
  return moment(date).year();
}
