import { PhotoIcon, XMarkIcon } from "../component/Icons";
import Profile from "../model/Profile";
import { useAppDispatch } from "../store/hooks";
import { editProfile } from "../store/ProfileReducer";
import { TextField } from "@mui/material";
import produce from "immer";
import { useState } from "react";

export interface ProfileDialogState {
  name: string;
  bio: string;
  location: string;
  profileImage?: FileList;
  coverImage?: FileList;
  profileImageUrl: string;
  coverImageurl: string;
}
export const EditProfileDialog = ({
  profile,
  onClose,
  isOpen,
}: {
  profile: Profile;
  onClose?: () => void;
  isOpen?: boolean;
}) => {
  const dispatch = useAppDispatch();
  const initState: ProfileDialogState = {
    name: profile.fullname,
    bio: profile.bio ? profile.bio : "",
    location: profile.location ? profile.location : "",
    profileImageUrl: profile.profileImageUrl,
    coverImageurl: profile.coverImageUrl ? profile.coverImageUrl : "",
  };
  const [state, setState] = useState<ProfileDialogState>(initState);
  if (!isOpen) {
    return null;
  }
  return (
    <div className="fixed w-full h-full before:bg-gray-900 before:w-full before:h-full before:fixed before:opacity-20 top-0 left-0 ">
      <div className="w-1/3 py-5 px-5 mx-auto my-0 fixed left-1/2 top-1/4 bg-white -translate-x-1/2 -translate-y-1/4 rounded-xl z-50">
        <div className="flex flex-row my-2">
          <XMarkIcon
            className="w-10 p-2 text-gray-800 rounded-full hover:bg-gray-200 my-auto"
            onClick={onClose}
          />
          <h1 className="text-2xl font-bold text-gray-800 my-auto">
            Edit Profile
          </h1>
          <div
            className="bg-gray-900 text-white my-auto text-center p-2  px-4 rounded-3xl  hover:bg-gray-800 cursor-pointer font-semibold ml-auto"
            onClick={() => {
              dispatch(editProfile(state));
              onClose?.();
            }}
          >
            Save
          </div>
        </div>
        <div>
          <div id="cover">
            <div
              className="bg-center relative w-full min-h-52 max-h-52 bg-cover border-white border-4 text-center rounded-md flex justify-center items-center"
              style={{
                backgroundImage: ` url(${state.coverImageurl})`,
              }}
            >
              <div className="before:top-0 before:left-0 before:absolute before:w-full before:h-full before:bg-black opacity-20"></div>
              <ChangeImage
                onChange={(target) => {
                  if (target.files !== null && target.files.length != 0) {
                    setState(
                      produce((state) => {
                        state.coverImage = target.files as FileList;
                      })
                    );
                    const fileReader = new FileReader();
                    fileReader.onloadend = (e) => {
                      setState(
                        produce((state) => {
                          state.coverImageurl = e.target!.result as string;
                        })
                      );
                    };
                    fileReader.readAsDataURL(target.files![0]);
                  }
                }}
              />
            </div>
          </div>
          <div className="mx-4">
            <div
              className="rounded-full bg-center w-32 h-32 bg-cover  -top-1/2 left-0 -translate-y-1/2 border-white border-4 flex justify-center items-center"
              style={{
                backgroundImage: ` url(${state.profileImageUrl})`,
              }}
            >
              <div className="before:top-0 before:left-0 before:absolute before:w-full before:h-full before:bg-black opacity-20 before:rounded-full"></div>
              <ChangeImage
                onChange={(target) => {
                  if (target.files !== null && target.files.length != 0) {
                    setState(
                      produce((state) => {
                        state.profileImage = target.files as FileList;
                      })
                    );
                    const fileReader = new FileReader();
                    fileReader.onloadend = (e) => {
                      setState(
                        produce((state) => {
                          state.profileImageUrl = e.target!.result as string;
                        })
                      );
                    };
                    fileReader.readAsDataURL(target.files![0]);
                  }
                }}
              />{" "}
            </div>
          </div>
          <div>
            <div className="flex flex-col gap-5">
              <TextField
                label="Name"
                variant="outlined"
                fullWidth
                value={state.name}
                onChange={(e) => {
                  setState(
                    produce((state) => {
                      state.name = e.target.value;
                    })
                  );
                }}
                type={"text"}
              />
              <TextField
                label="Bio"
                variant="outlined"
                multiline
                value={state.bio}
                onChange={(e) => {
                  setState(
                    produce((state) => {
                      state.bio = e.target.value;
                    })
                  );
                }}
                rows={3}
                fullWidth
                type={"text"}
                size="medium"
              />
              <TextField
                label="Location"
                variant="outlined"
                value={state.location}
                onChange={(e) => {
                  setState(
                    produce((state) => {
                      state.location = e.target.value;
                    })
                  );
                }}
                fullWidth
                type={"text"}
              />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
const ChangeImage = ({
  onChange,
}: {
  onChange: (target: HTMLInputElement) => void;
}) => {
  return (
    <label className="z-10">
      <PhotoIcon className="w-12 p-2 bg-gray-500 rounded-full text-white my-auto mx-auto  cursor-pointer" />
      <input
        type="file"
        accept="image/jpeg,image/png,image/webp,image/gif"
        onClick={(e) => {
          const target = e.target as HTMLInputElement;
          if (target.value) target.value = "";
        }}
        hidden
        onChange={(e) => {
          const target = e.target as HTMLInputElement;
          onChange(target);
        }}
      />
    </label>
  );
};
