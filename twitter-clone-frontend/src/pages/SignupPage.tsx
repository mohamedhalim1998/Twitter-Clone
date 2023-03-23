import {
  Button,
  FormControl,
  InputLabel,
  MenuItem,
  Select,
  TextField,
} from "@mui/material";
import React from "react";
import _, { range } from "lodash";
import { start } from "repl";

function SignupPage() {
  return (
    <div className="w-1/3 h-fit mx-auto my-40 p-10 border-2  border-gray-200 rounded-md">
      <h1 className="font-bold text-3xl">Create your account</h1>
      <div className="py-5">
        <TextField label="Username" variant="outlined" fullWidth />
      </div>
      <div className="py-5">
        <TextField label="Email" variant="outlined" fullWidth />
      </div>
      <div className="py-5">
        <TextField
          label="Password"
          variant="outlined"
          fullWidth
          type={"password"}
        />
      </div>
      <div className="flex flex-row gap-1">
        <FormControl
          sx={{
            width: "100%",
          }}
        >
          <InputLabel>Day</InputLabel>
          <Select label="day">
            {_.range(1, 32).map((i) => {
              return <MenuItem value={i}>{i}</MenuItem>;
            })}
          </Select>
        </FormControl>
        <FormControl
          sx={{
            width: "100%",
          }}
        >
          <InputLabel>Month</InputLabel>
          <Select label="month">
            {_.range(1, 13).map((i) => {
              return <MenuItem value={i}>{i}</MenuItem>;
            })}
          </Select>
        </FormControl>
        <FormControl
          sx={{
            width: "100%",
          }}
        >
          <InputLabel>Year</InputLabel>
          <Select label="Age">
            {_.range(1920, 2021).map((i) => {
              return <MenuItem value={i}>{i}</MenuItem>;
            })}
          </Select>
        </FormControl>
      </div>
      <div className="mt-10 flex ml-auto">
      <Button variant="contained">Sign up</Button>
      </div>
    </div>
  );
}

export default SignupPage;
