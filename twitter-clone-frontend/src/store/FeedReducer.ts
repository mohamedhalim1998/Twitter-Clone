import { createAction, createReducer, PayloadAction } from "@reduxjs/toolkit";
import { AxiosResponse } from "axios";
import Tweet from "../model/Tweet";
import { apiCall } from "./ApiMiddleware";

export interface FeedState {
  loading: boolean;
  tweets: Tweet[];
}
export const updateFeedLoading = createAction<boolean>("updateFeedLoading");

export const updateFeed = createAction<AxiosResponse>("updateFeed");

export const getUserFeed = () =>
  apiCall({
    url: "http://localhost:8080/api/v1/tweets/feed",
    useJwtToken: true,
    onSuccess: updateFeed,
  });
export const getUserTweets = (username: string) =>
  apiCall({
    url: "http://localhost:8080/api/v1/tweets/user/".concat(username),
    useJwtToken: true,
    onSuccess: updateFeed,
  });

const initState: FeedState = {
  loading: false,
  tweets: [],
};
export default createReducer<FeedState>(initState, {
  [updateFeedLoading.type]: (state, action: PayloadAction<boolean>) => {
    state.loading = action.payload;
  },
  [updateFeed.type]: (state, action: PayloadAction<AxiosResponse>) => {
    state.loading = false;
    state.tweets = action.payload.data;
  },
});
