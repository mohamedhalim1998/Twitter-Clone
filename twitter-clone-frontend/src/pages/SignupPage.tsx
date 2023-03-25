import {
  Button,
  FormControl,
  InputLabel,
  MenuItem,
  Select,
  TextField,
} from "@mui/material";
import React, { useEffect } from "react";
import _, { range } from "lodash";
import { start } from "repl";
import { useForm } from "react-hook-form";
import { signup, updateAuthLoading, verifyToken } from "../store/AuthReducer";
import { useAppDispatch, useAppSelector } from "../store/hooks";
import moment from "moment";
import { Link, useLocation, useNavigate } from "react-router-dom";
import * as Yup from "yup";
import { toast, ToastContainer } from "react-toastify";
import { RootState } from "../store/Store";

interface RegisterForm {
  username: string;
  email: string;
  password: string;
  day: string;
  month: string;
  year: string;
}

function SignupPage() {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const verified = useAppSelector((state: RootState) => state.auth.verified);
  let location = useLocation();
  let state = location.state as { from: Location };
  let from = state ? state.from.pathname : "/";
  useEffect(() => {
    if (verified) {
      navigate(from, { replace: true });
    }
    if (!verified) {
      dispatch(updateAuthLoading(true));
      dispatch(verifyToken());
    }
  }, [verified]);

  const initDate: RegisterForm = {
    username: "",
    email: "",
    password: "",
    day: "1",
    month: "1",
    year: "2020",
  };
  const schema = Yup.object({
    username: Yup.string().required(),
    email: Yup.string().email().required(),
    password: Yup.string().required(),
    day: Yup.string().required(),
    month: Yup.string().required(),
    year: Yup.string().required(),
  });
  const { register, handleSubmit } = useForm<RegisterForm>({
    defaultValues: initDate,
  });

  const onSubmit = (data: RegisterForm) => {
    console.log(data);
    schema
      .validate(data)
      .then((_) => {
        const bday = calculateBirthDay(+data.day, +data.month, +data.year);
        dispatch(signup(data.username, data.password, data.email, bday));
      })
      .catch((e) => {
        toast.error("Some Fileds Are Missing", {
          position: "top-center",
          autoClose: 2000,
          hideProgressBar: true,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
          theme: "light",
        });
      });
  };
  return (
    <div className="w-1/3 h-fit mx-auto my-40 p-10 border-2  border-gray-200 rounded-md">
      <form onSubmit={handleSubmit(onSubmit)}>
        <h1 className="font-bold text-3xl">Create your account</h1>
        <div className="flex flex-col gap-10">
          <TextField
            label="Username"
            variant="outlined"
            fullWidth
            {...register("username")}
          />
          <TextField
            label="Email"
            variant="outlined"
            fullWidth
            {...register("email")}
          />
          <TextField
            label="Password"
            variant="outlined"
            fullWidth
            type={"password"}
            {...register("password")}
          />
          <div className="flex flex-row gap-1">
            <FormControl
              sx={{
                width: "100%",
              }}
            >
              <InputLabel>Day</InputLabel>
              <Select label="day" {...register("day")} defaultValue="1">
                {_.range(1, 32).map((i) => {
                  return (
                    <MenuItem key={i} value={i}>
                      {i}
                    </MenuItem>
                  );
                })}
              </Select>
            </FormControl>
            <FormControl
              sx={{
                width: "100%",
              }}
            >
              <InputLabel>Month</InputLabel>
              <Select label="month" {...register("month")} defaultValue="1">
                {_.range(1, 13).map((i) => {
                  return (
                    <MenuItem key={i} value={i}>
                      {i}
                    </MenuItem>
                  );
                })}
              </Select>
            </FormControl>
            <FormControl
              sx={{
                width: "100%",
              }}
            >
              <InputLabel>Year</InputLabel>
              <Select label="Year" {...register("year")} defaultValue="2020">
                {_.range(1920, 2021).map((i) => {
                  return (
                    <MenuItem key={i} value={i}>
                      {i}
                    </MenuItem>
                  );
                })}
              </Select>
            </FormControl>
          </div>
          <Button variant="contained" type="submit" fullWidth>
            Sign up
          </Button>
        </div>
      </form>
      <h2 className="text-center font-normal mt-16 text-gray-600">
        Have an account already?{" "}
        <Link className="text-blue-500 underline" to={"/login"}>
          Log in
        </Link>
      </h2>
    </div>
  );
}

export default SignupPage;

function calculateBirthDay(day: number, month: number, year: number) {
  return moment()
    .date(day)
    .month(month - 1)
    .year(year)
    .valueOf();
}
