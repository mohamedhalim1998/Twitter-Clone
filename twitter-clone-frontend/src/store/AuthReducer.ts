import { createAction, createReducer, PayloadAction } from "@reduxjs/toolkit";
import Cookies from "js-cookie";
import { apiCall } from "./ApiMiddleware";

export const signup = (username: string, password: string, email: string, birthDay: number) =>
  apiCall({
    url: "http://localhost:8080/api/v1/auth/register",
    onSuccess: saveJwtTokenFromResponse.toString(),
    method: "POST",
    body: {
      username,
      password,
      email,
      birthDay
    },
  });

export const saveJwtTokenFromResponse = createAction<any>(
  "saveJwtTokenFromResponse"
);
export default createReducer(
  {},
  {
    [saveJwtTokenFromResponse.type]: (
      state: any,
      action: PayloadAction<any>
    ) => {
      console.log(action.payload.headers);
      const token = "access_token" as keyof typeof action.payload.headers;
      Cookies.set("access_token", action.payload.headers[token]);
    },
  }
);
