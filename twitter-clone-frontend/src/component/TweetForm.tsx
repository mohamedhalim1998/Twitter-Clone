import produce from "immer";
import React, { useState } from "react";
import { number } from "yup";
import { XMarkIcon, PollIcon } from "./Icons";
import { MediaButton } from "./MediaButton";
import { PollForm } from "./PollForm";
import { TweetButton } from "./SideBar";
import _ from "lodash";
import { useAppSelector } from "../store/hooks";
import { ProfileState } from "../store/ProfileReducer";
import { RootState } from "../store/Store";
export interface TweetFormParams {
  text: string;
  isPoll: boolean;
  hasMedia: boolean;
  pollOptions?: string[];
  pollLengthDays?: number;
  pollLengthHours?: number;
  pollLengthMinute?: number;
  media?: FileList;
  mediaSrc?: string;
  mediaType?: string;
}
function TweetForm(props: {
  onSubmit: (date: TweetFormParams) => void;
  disablePoll?: boolean;
}) {
  const [state, setState] = useState<TweetFormParams>({
    text: "",
    isPoll: false,
    hasMedia: false,
  });
  const profile: ProfileState = useAppSelector(
    (state: RootState) => state.profile
  );
  return (
    <div className="px-6">
      {" "}
      <div className="flex flex-row gap-5">
        <div
          className="rounded-full bg-center w-12 h-12 bg-cover flex-shrink-0"
          style={{
            backgroundImage: ` url(${profile.profile?.profileImageUrl})`,
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
          {!props.disablePoll && state.isPoll && PollForm(state, setState)}
          {state.hasMedia && (
            <div className="w-full  rounded-md my-4 overflow-hidden relative">
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
              {state.mediaType!.match("video.*") ? (
                <video
                  className="object-cover w-auto max-h-60 mx-auto"
                  controls
                  src={state.mediaSrc}
                ></video>
              ) : (
                <img
                  className="object-cover w-auto max-h-72 mx-auto"
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
        <MediaButton
          disabled={state.isPoll}
          onClick={(e) => {
            if (!state.hasMedia) e.value = "";
          }}
          onChange={(e: HTMLInputElement) => {
            if (e.files !== null && e.files.length != 0) {
              setState(
                produce((state) => {
                  state.media = e.files as FileList;
                  state.hasMedia = true;
                  state.mediaType = e.files![0].type;
                })
              );
              const fileReader = new FileReader();
              fileReader.onloadend = (e) => {
                setState(
                  produce((state) => {
                    state.mediaSrc = e.target!.result as string;
                  })
                );
              };
              fileReader.readAsDataURL(e.files![0]);
            }
          }}
        />
        {!props.disablePoll && (
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
        )}
        <TweetButton
          className="w-fit ml-auto text-white bg-theme hover:bg-blue-400 rounded-3xl px-6 py-2 cursor-pointer"
          onClick={() => {
            props.onSubmit(state);
            setState({
              text: "",
              isPoll: false,
              hasMedia: false,
            });
          }}
        />
      </div>
    </div>
  );
}

export default TweetForm;
