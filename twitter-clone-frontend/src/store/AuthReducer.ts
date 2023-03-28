import { createAction, createReducer, PayloadAction } from "@reduxjs/toolkit";
import { AxiosResponse } from "axios";
import Cookies from "js-cookie";
import AuthState from "../model/AuthState";
import { apiCall } from "./ApiMiddleware";

export const signup = (
  username: string,
  password: string,
  email: string,
  birthDay: number
) =>
  apiCall({
    url: "http://localhost:8080/api/v1/auth/register",
    onSuccess: saveJwtTokenFromResponse,
    method: "POST",
    body: {
      username,
      password,
      email,
      birthDay,
    },
  });

export const login = (username: string, password: string) =>
  apiCall({
    url: "http://localhost:8080/api/v1/auth/login",
    onSuccess: saveJwtTokenFromResponse,
    method: "POST",
    body: {
      username,
      password,
    },
  });
export const logout = () =>
  apiCall({
    url: "http://localhost:8080/api/v1/auth/logout",
    onSuccess: removeJwtToken,
    method: "POST",
    body: {},
  });
export const verifyToken = () => {
  const token = Cookies.get("access_token");
  return apiCall({
    url: "http://localhost:8080/api/v1/auth/verify_token",
    onSuccess: loadTokenFromCookies,
    onError: tokenVerificationFailed,
    method: "POST",
    body: {
      token,
    },
  });
};

export const saveJwtTokenFromResponse = createAction<any>(
  "saveJwtTokenFromResponse"
);
export const removeJwtToken = createAction<any>("removeJwtToken");

export const loadTokenFromCookies = createAction("loadTokenFromCookies");
export const updateAuthLoading = createAction<boolean>("updateAuthLoading");
export const updateAuthVerify = createAction<boolean>("updateAuthVerify");
export const tokenVerificationFailed = createAction("tokenVerificationFailed");
const initState: AuthState = {
  loading: false,
  verified: false,
};

export default createReducer<AuthState>(initState, {
  [saveJwtTokenFromResponse.type]: (
    state: AuthState,
    action: PayloadAction<AxiosResponse>
  ) => {
    const token = action.payload.data["token"];
    Cookies.set("access_token", token);
    state.token = token;
    state.verified = true;
    state.loading = false;
  },
  [updateAuthLoading.type]: (
    state: AuthState,
    action: PayloadAction<boolean>
  ) => {
    state.loading = action.payload;
  },
  [updateAuthVerify.type]: (
    state: AuthState,
    action: PayloadAction<boolean>
  ) => {
    state.verified = action.payload;
    state.loading = false;
  },
  [tokenVerificationFailed.type]: (
    state: AuthState,
    action: PayloadAction<AxiosResponse>
  ) => {
    state.verified = false;
    state.loading = false;
  },
  [loadTokenFromCookies.type]: (
    state: AuthState,
    action: PayloadAction<any>
  ) => {
    state.token = Cookies.get("access_token");
    state.loading = false;
    state.verified = true;
  },
  [removeJwtToken.type]: (
    state: AuthState,
    action: PayloadAction<AxiosResponse>
  ) => {
    state.token = undefined;
    state.loading = false;
    state.verified = false;
    Cookies.remove("access_token");
  },
});
