import React from "react";
import produce from "immer";
import { TweetDialogForm } from "./TweetDialog";
import { SelectChangeEvent, FormControl, InputLabel, Select, MenuItem, TextField } from "@mui/material";
import _ from "lodash";

export function PollForm(
  state: TweetDialogForm,
  setState: React.Dispatch<React.SetStateAction<TweetDialogForm>>): React.ReactNode {
  return (
    <div className="w-full flex flex-col gap-5 border rounded-xl p-3 my-4 pb-0-">
      <PollOption
        label="Choice 1"
        value={state.pollOptions!![0]}
        onChange={(e) => {
          setState(
            produce((state) => {
              state.pollOptions!![0] = e.target.value;
            })
          );
        }} />{" "}
      <PollOption
        label="Choice 2"
        value={state.pollOptions!![1]}
        onChange={(e) => {
          setState(
            produce((state) => {
              state.pollOptions!![1] = e.target.value;
            })
          );
        }} />{" "}
      <PollOption
        label="Choice 3 (Optional)"
        value={state.pollOptions!![2]}
        onChange={(e) => {
          setState(
            produce((state) => {
              state.pollOptions!![2] = e.target.value;
            })
          );
        }} />{" "}
      <PollOption
        label="Choice 4 (Optional)"
        value={state.pollOptions!![3]}
        onChange={(e) => {
          setState(
            produce((state) => {
              state.pollOptions!![3] = e.target.value;
            })
          );
        }} />
      {PollLength(state, setState)}
      <button
        className="text-red-400 hover:text-white py-4 hover:bg-red-400 flex-grow-1 h-full w-full rounded-lg"
        onClick={() => {
          setState(
            produce((state) => {
              state.isPoll = false;
              state.pollLengthDays = undefined;
              state.pollLengthHours = undefined;
              state.pollLengthMinute = undefined;
              state.pollOptions = undefined;
            })
          );
        }}
      >
        Remove Poll
      </button>
    </div>
  );
}
export function PollLength(
  state: TweetDialogForm,
  setState: React.Dispatch<React.SetStateAction<TweetDialogForm>>
): React.ReactNode {
  return (
    <div>
      <p className="text-gray-800  text-xs py-4">Poll Length</p>
      <div className="flex flex-row gap-1">
        <PollLengthSelect
          range={_.range(1, 8)}
          label="Day"
          value={1}
          onChange={(e) => setState(
            produce((state) => {
              state.pollLengthDays = e.target.value as number;
            })
          )} />
        <PollLengthSelect
          range={_.range(0, 24)}
          label="Hour"
          value={0}
          onChange={(e) => setState(
            produce((state) => {
              state.pollLengthHours = e.target.value as number;
            })
          )} />
        <PollLengthSelect
          range={_.range(0, 60)}
          label="Minute"
          value={0}
          onChange={(e) => setState(
            produce((state) => {
              state.pollLengthMinute = e.target.value as number;
            })
          )} />
      </div>
    </div>
  );
}
function PollLengthSelect(props: {
  label: string;
  range: number[];
  value: number;
  onChange: (e: SelectChangeEvent<number>) => void;
}) {
  return (
    <FormControl
      sx={{
        width: "100%",
      }}
    >
      <InputLabel>{props.label}</InputLabel>
      <Select
        label={props.label}
        defaultValue={props.value}
        value={props.value}
        onChange={props.onChange}
      >
        {props.range.map((i) => {
          return (
            <MenuItem key={i} value={i}>
              {i}
            </MenuItem>
          );
        })}
      </Select>
    </FormControl>
  );
}
export function PollOption({
  label, value, onChange,
}: {
  label: string;
  value: string;
  onChange: (
    e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ) => void;
}) {
  return (
    <TextField
      label={label}
      variant="outlined"
      value={value}
      onChange={onChange}
      fullWidth
      type={"text"} />
  );
}