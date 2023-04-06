import { createAction, createReducer, PayloadAction } from "@reduxjs/toolkit";
import { AxiosResponse } from "axios";
import Profile from "../model/Profile";
import Tweet from "../model/Tweet";
import { ProfileDialogState } from "../pages/EditProfileDialog";
import { apiCall } from "./ApiMiddleware";

export interface ProfileState {
  profile?: Profile;
  pinnedTweet?: Tweet;
  guestProfile?: Profile;
  loading: boolean;
}

const initState: ProfileState = {
  loading: false,
};
export const updateProfileLoading = createAction<boolean>(
  "updateProfileLoading"
);

export const updateProfile = createAction<AxiosResponse>("updateProfile");
export const updateGuestProfile =
  createAction<AxiosResponse>("updateGuestProfile");
export const updatePinnedTweet =
  createAction<AxiosResponse>("updatePinnedTweet");

export const loadProfile = (username?: string) =>
  apiCall({
    url: "http://localhost:8080/api/v1/users/".concat(
      username ? username : "logged"
    ),
    onSuccess: updateProfile,
    useJwtToken: true,
  });
export const loadGuestProfile = (username: string) =>
  apiCall({
    url: "http://localhost:8080/api/v1/users/".concat(username),
    onSuccess: updateGuestProfile,
    useJwtToken: true,
  });

export const getPinnedTweet = (id: number) =>
  apiCall({
    url: `http://localhost:8080/api/v1/tweets/${id}`,
    useJwtToken: true,
    onSuccess: updatePinnedTweet,
  });

export const editProfile = (profileInfo: ProfileDialogState) => {
  const data = new FormData();
  data.append(
    "profile_info",
    new Blob(
      [
        JSON.stringify({
          name: profileInfo.name,
          bio: profileInfo.bio,
          location: profileInfo.location,
        }),
      ],
      {
        type: "application/json",
      }
    )
  );
  if (profileInfo.profileImage !== undefined) data.append("profile_img", profileInfo.profileImage[0]);
  if (profileInfo.coverImage !== undefined) data.append("cover_img", profileInfo.coverImage[0]);

  return apiCall({
    url: `http://localhost:8080/api/v1/users`,
    useJwtToken: true,
    onSuccess: updateProfile,
    method: "PATCH",
    body: data,
  });};
export default createReducer<ProfileState>(initState, {
  [updateProfileLoading.type]: (state, action: PayloadAction<boolean>) => {
    state.loading = action.payload;
  },
  [updateProfile.type]: (state, action: PayloadAction<AxiosResponse>) => {
    state.loading = false;
    state.profile = action.payload.data;
  },
  [updateGuestProfile.type]: (state, action: PayloadAction<AxiosResponse>) => {
    state.loading = false;
    state.guestProfile = action.payload.data;
  },
  [updatePinnedTweet.type]: (state, action: PayloadAction<AxiosResponse>) => {
    state.loading = false;
    state.pinnedTweet = action.payload.data;
  },
});
