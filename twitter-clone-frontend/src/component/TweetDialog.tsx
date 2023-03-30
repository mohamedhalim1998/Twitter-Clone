import React, { useState } from "react";
import { PhotoIcon, PollIcon, XMarkIcon } from "./Icons";
import { useAppDispatch } from "../store/hooks";
import produce from "immer";
import {
  FormControl,
  InputLabel,
  MenuItem,
  Select,
  SelectChangeEvent,
  TextField,
} from "@mui/material";
import _ from "lodash";
import { postTweet } from "../store/TweetReducer";
import moment from "moment";
import { TweetButton, SideBar } from "./SideBar";
import { PollForm } from "./PollForm";

export interface TweetDialogForm {
  text: string;
  isPoll: boolean;
  hasMedia: boolean;
  pollOptions?: string[];
  pollLengthDays?: number;
  pollLengthHours?: number;
  pollLengthMinute?: number;
  media?: FileList;
  mediaSrc?: string;
}
export const TweetDialog = (props: {
  onSubmit: (date: TweetDialogForm) => void;
  onClose: () => void;
  isOpen?: boolean;
}) => {
  const [state, setState] = useState<TweetDialogForm>({
    text: "",
    isPoll: false,
    hasMedia: false,
  });

  if (!props.isOpen) return null;
  return (
    <div className="fixed w-full h-full before:bg-gray-900 before:w-full before:h-full before:fixed before:opacity-20 top-0 left-0 ">
      <div className="w-1/3 py-5 px-5 mx-auto my-0 fixed left-1/2 top-1/4 bg-white -translate-x-1/2 -translate-y-1/4 rounded-xl z-50">
        <XMarkIcon className="w-10 p-2 text-gray-800 rounded-full hover:bg-gray-200 my-2" onClick={props.onClose} />
        <div className="flex flex-row gap-5">
          <div
            className="rounded-full bg-center w-12 h-12 bg-cover flex-shrink-0"
            style={{
              backgroundImage: ` url(https://pbs.twimg.com/profile_images/772538396682092545/OmC7OaLV_400x400.jpg)`,
            }}
          />
          <div className="flex flex-col w-full">
            <textarea
              autoFocus
              value={state.text}
              onChange={(e) => {
                setState(
                  produce((state) => {
                    state.text = e.target.value;
                  })
                );
              }}
              id="tweet"
              className="placeholder:text-gray-400 rounded-md w-full h-24 px-2 py-3 text-xl text-gray-800 focus:outline-0 resize-none"
              placeholder={
                state.isPoll ? "Ask a question ..." : "What's happening ?"
              }
            />
            {state.isPoll && PollForm(state, setState)}
            {state.hasMedia && (
              <div className="w-full rounded-md my-4 overflow-hidden relative">
                <XMarkIcon
                  className="absolute top-0 left-0 cursor-pointer w-8 text-gray-800 z-50"
                  onClick={() => {
                    setState(
                      produce((state) => {
                        state.media = undefined;
                        state.mediaSrc = undefined;
                        state.hasMedia = false;
                      })
                    );
                  }}
                />
                {state.media![0].type.match("video.*") ? (
                  <video
                    className="object-cover w-auto max-h-60"
                    controls
                    src={state.mediaSrc}
                  ></video>
                ) : (
                  <img
                    className="object-cover w-full h-full"
                    src={state.mediaSrc}
                    alt=""
                  />
                )}
              </div>
            )}
          </div>
        </div>

        <div className="w-11/12 mx-auto border bg-gray-30" />
        <div className="flex flex-row my-2 w-11/12 mx-auto gap-3">
          {MediaButton(state, (e: HTMLInputElement) => {
            setState(
              produce((state) => {
                if (e.files !== null) {
                  state.media = e.files as FileList;
                  state.hasMedia = true;
                  const fileReader = new FileReader();
                  fileReader.onloadend = (e) => {
                    setState(
                      produce((state) => {
                        state.mediaSrc = e.target!.result as string;
                      })
                    );
                  };
                  fileReader.readAsDataURL(e.files[0]);
                }
              })
            );
          })}
          <PollIcon
            className={`w-5 cursor-pointer `.concat(
              state.hasMedia ? "text-gray-500" : "text-theme"
            )}
            onClick={() => {
              if (!state.hasMedia)
                setState(
                  produce((state) => {
                    state.isPoll = true;
                    state.pollLengthDays = 1;
                    state.pollLengthHours = 0;
                    state.pollLengthMinute = 0;
                    state.pollOptions = ["", "", "", ""];
                  })
                );
            }}
          />
          <TweetButton
            className="w-fit ml-auto text-white bg-theme hover:bg-blue-400 rounded-3xl px-6 py-2 cursor-pointer"
            onClick={() => {
              props.onSubmit(state);
            }}
          />
        </div>
      </div>
    </div>
  );
};

function MediaButton(
  state: TweetDialogForm,
  onChange: (e: HTMLInputElement) => void
) {
  return (
    <label className="my-auto">
      <PhotoIcon
        className={`w-5 cursor-pointer `.concat(
          state.isPoll ? "text-gray-500" : "text-theme"
        )}
      />
      <input
        type="file"
        accept="image/jpeg,image/png,image/webp,image/gif,video/mp4"
        className="hidden"
        onChange={(e) => {
          const target = e.target as HTMLInputElement;
          onChange(target);
        }}
      />
    </label>
  );
}
