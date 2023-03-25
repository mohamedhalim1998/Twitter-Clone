import { ActionCreatorWithPayload, createAction } from "@reduxjs/toolkit";
import axios, { AxiosError, AxiosResponse, Method } from "axios";
import Cookies from "js-cookie";
import _ from "lodash";
import { toast } from "react-toastify";
import { AnyAction, Middleware } from "redux";

export interface ApiCallParams {
  url: string;
  method?: Method;
  onSuccess?: ActionCreatorWithPayload<any, string>;
  onError?: ActionCreatorWithPayload<any, string>;
  body?: object;
  headers?: object;
  useJwtToken?: boolean;
}

export const apiCall = createAction<ApiCallParams>("apiCall");

const apiMiddleware: Middleware =
  (store) => (next) => async (action: AnyAction) => {
    if (action.type !== apiCall.type) {
      return next(action);
    }
    const payload = action.payload as ApiCallParams;
    next(action);
    if (payload.useJwtToken) {
      const token = Cookies.get("access_token");
      payload.headers = {
        ...payload.headers,
        Authorization: "Bearer " + token,
      };
    }
    axios
      .request({
        url: payload.url,
        method: payload.method ? payload.method : "GET",
        data: payload.body,
        headers: payload.headers,
      })
      .then((response: AxiosResponse<any, any>) => {
        if (payload.onSuccess != null) {
          store.dispatch(payload.onSuccess(response));
        }
      })
      .catch((error: AxiosError) => {
        const response = error.response!!;
        if (payload.onError != null) {
          store.dispatch(payload.onError(response));
        } else {
          toast.error("" + response.data, {
            position: "top-center",
            autoClose: 2000,
            hideProgressBar: true,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            progress: undefined,
            theme: "light",
          });
        }
      });
  };
export default apiMiddleware;
