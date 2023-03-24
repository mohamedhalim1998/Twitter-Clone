import { createAction } from "@reduxjs/toolkit";
import axios, { AxiosError, AxiosResponse, Method } from "axios";
import Cookies from "js-cookie";
import _ from "lodash";
import { AnyAction, Middleware } from "redux";

export interface ApiCallParams {
  url: string;
  method?: Method;
  onSuccess?: AnyAction | string;
  onError?: string;
  body?: object;
  headers?: object;
  useJwtToken?: boolean;
}

export const apiCall = createAction<ApiCallParams>("apiCall");

const apiMiddleware: Middleware =
  (store) => (next) => async (action: AnyAction) => {
    console.log(action);
    if (action.type !== apiCall.type) {
      console.log("next", next);

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
          if (_.isString(payload.onSuccess))
            store.dispatch({
              type: payload.onSuccess,
              payload: {
                headers: response.headers,
                data: response.data,
                status: response.status,
              },
            });
          else store.dispatch(payload.onSuccess as AnyAction);
        }
      })
      .catch((error: AxiosError) => {
        const response = error.response!!;
        if (payload.onError != null) {
          store.dispatch({
            type: payload.onError,
            payload: {
              headers: response.headers,
              data: response.data,
              status: response.status,
            },
          });
        }
      });
  };
export default apiMiddleware;
