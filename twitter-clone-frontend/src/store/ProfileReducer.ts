import { createAction, createReducer, PayloadAction } from "@reduxjs/toolkit";
import { AxiosResponse } from "axios";
import Profile from "../model/Profile";
import Tweet from "../model/Tweet";
import { apiCall } from "./ApiMiddleware";

export interface ProfileState {
  profile?: Profile;
  pinnedTweet?: Tweet;
  loading: boolean;
}

const initState: ProfileState = {
  loading: false,
};
export const updateProfileLoading = createAction<boolean>(
  "updateProfileLoading"
);

export const updateProfile = createAction<AxiosResponse>("updateProfile");
export const updatePinnedTweet =
  createAction<AxiosResponse>("updatePinnedTweet");

export const loadProfile = (username?: string) =>
  apiCall({
    url: "http://localhost:8080/api/v1/users/".concat(username ? username : "logged"),
    onSuccess: updateProfile,
    useJwtToken: true,
  });
export const getPinnedTweet = (id: number) =>
  apiCall({
    url: `http://localhost:8080/api/v1/tweets/${id}`,
    useJwtToken: true,
    onSuccess: updatePinnedTweet,
  });
export default createReducer<ProfileState>(initState, {
  [updateProfileLoading.type]: (state, action: PayloadAction<boolean>) => {
    state.loading = action.payload;
  },
  [updateProfile.type]: (state, action: PayloadAction<AxiosResponse>) => {
    state.loading = false;
    state.profile = action.payload.data;
  },
  [updatePinnedTweet.type]: (state, action: PayloadAction<AxiosResponse>) => {
    state.loading = false;
    state.pinnedTweet = action.payload.data;
  },
});
