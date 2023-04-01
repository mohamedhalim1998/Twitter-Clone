import React from "react";
import { PhotoIcon } from "./Icons";
import { TweetFormParams } from "./TweetForm";

export function MediaButton(props: {
  disabled: boolean;
  onChange: (e: HTMLInputElement) => void;
  onClick?: (e: HTMLInputElement) => void;
}) {
  console.log("re render");
  return (
    <label className="my-auto">
      <PhotoIcon
        className={`w-5 cursor-pointer `.concat(
          props.disabled ? "text-gray-500" : "text-theme"
        )}
      />
      <input
        type="file"
        accept="image/jpeg,image/png,image/webp,image/gif,video/mp4"
        onClick={(e) => {
          const target = e.target as HTMLInputElement;
          if (props.onClick) props.onClick(target);
        }}
        disabled={props.disabled}
        hidden
        onChange={(e) => {
          const target = e.target as HTMLInputElement;
          console.log("file chancged");
          props.onChange(target);
          // e.target.value = "";
        }}
      />
    </label>
  );
}
