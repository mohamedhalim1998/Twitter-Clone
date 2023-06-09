import { configureStore } from "@reduxjs/toolkit";
import apiMiddleware from "./ApiMiddleware";
import AuthReducer from "./AuthReducer";
import FeedReducer from "./FeedReducer";
import ProfileReducer from "./ProfileReducer";

export const store = configureStore({
  reducer: {
    auth: AuthReducer,
    profile: ProfileReducer,
    feed: FeedReducer
  },
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({
      serializableCheck: false,
    }).concat(apiMiddleware),
});


export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
