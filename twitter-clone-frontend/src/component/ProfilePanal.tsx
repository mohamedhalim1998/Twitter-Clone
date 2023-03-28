import React from "react";
import { MoreIconWithoutCircle } from "./Icons";

function ProfilePanal(props: {
  icon?: JSX.Element;
  actionElement?: JSX.Element;
}) {
  return (
    <div className="flex flex-row gap-2 p-3 hover:bg-gray-300 cursor-pointer rounded-4xl">
      <div
        className="rounded-full bg-center w-10 h-10 bg-cover"
        style={{
          backgroundImage: ` url(https://pbs.twimg.com/profile_images/772538396682092545/OmC7OaLV_400x400.jpg)`,
        }}
      />
      <div className="flex flex-col ">
        <h4 className="font-semibold text-sm text-gray-800">
          Mohamed Halim {props.icon}
        </h4>
        <h5 className="text-sm text-gray-500">@mohamedhalim98</h5>
      </div>
      {props.actionElement}
    </div>
  );
}

export default ProfilePanal;
