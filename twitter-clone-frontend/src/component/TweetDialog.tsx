import React, { useState } from "react";
import {  XMarkIcon } from "./Icons";
import TweetForm, { TweetFormParams } from "./TweetForm";

export const TweetDialog = (props: {
  onClose: () => void;
  isOpen?: boolean;
}) => {
  if (!props.isOpen) return null;
  return (
    <div className="fixed w-full h-full before:bg-gray-900 before:w-full before:h-full before:fixed before:opacity-20 top-0 left-0 ">
      <div className="w-1/3 py-5 px-5 mx-auto my-0 fixed left-1/2 top-1/4 bg-white -translate-x-1/2 -translate-y-1/4 rounded-xl z-50">
        <XMarkIcon
          className="w-10 p-2 text-gray-800 rounded-full hover:bg-gray-200 my-2"
          onClick={props.onClose}
        />
        <TweetForm  />
      </div>
    </div>
  );
};

