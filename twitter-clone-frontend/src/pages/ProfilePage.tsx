import { useParams } from "react-router-dom";
import RightBar from "../component/RightBar";
import SideBar from "../component/SideBar";
import { TweetCard } from "../component/TweetCard";
import Profile from "../model/Profile";
import { useAppDispatch, useAppSelector } from "../store/hooks";
import {
  getPinnedTweet,
  loadGuestProfile,
  ProfileState,
  updateProfileLoading,
} from "../store/ProfileReducer";
import { RootState } from "../store/Store";
import { TweetNavBar } from "../component/TweetNavBar";
import { useEffect } from "react";
import { ProfileInfo } from "./ProfileInfo";
import { FeedState, getUserTweets, updateFeedLoading } from "../store/FeedReducer";

function ProfilePage() {
  const { username } = useParams();
  const dispatch = useAppDispatch();
  const profileState: ProfileState = useAppSelector(
    (state: RootState) => state.profile
  );
  let profile: Profile | undefined =
    profileState.profile?.username == username
      ? profileState.profile
      : profileState.guestProfile;
  const feed: FeedState = useAppSelector((state: RootState) => state.feed);

  useEffect(() => {
    if (!profileState.loading && profile == undefined) {
      dispatch(updateProfileLoading(true));
      dispatch(loadGuestProfile(username!!));
    }
  }, []);
  useEffect(() => {
    if (profile && profile.pinnedTweetId != undefined) {
      dispatch(updateProfileLoading(true));
      dispatch(getPinnedTweet(profile.pinnedTweetId));
    }
  }, [profile]);

  useEffect(() => {
    if (!feed.loading && feed.tweets.length === 0) {
      dispatch(updateFeedLoading(true));
      dispatch(getUserTweets(username!));
    }
  }, []);
  if (profileState.loading || profile == undefined) {
    return <div>loading</div>;
  }
  return (
    <div className="w-2/3 mx-auto grid grid-cols-11">
      <div className="col-span-2">
        <SideBar />
      </div>
      <div className="flex flex-col col-span-6 border-x border-gray-100 gap-3 ">
        <ProfileInfo
          profile={profile!}
          isUserProfile={
            username != undefined && username == profileState.profile?.username
          }
        />
        {TweetNavBar()}
        <TweetCard tweet={profileState.pinnedTweet} pinned />
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
export default ProfilePage;
