import {
  TextField,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  Button,
} from "@mui/material";
import _ from "lodash";
import React, { useEffect } from "react";
import { useForm } from "react-hook-form";
import { useNavigate, useLocation, Link } from "react-router-dom";
import { toast } from "react-toastify";
import {
  updateAuthLoading,
  verifyToken,
  signup,
  login,
} from "../store/AuthReducer";
import { useAppDispatch, useAppSelector } from "../store/hooks";
import { RootState } from "../store/Store";
import * as Yup from "yup";
import LoginForm from "../model/LoginForm";



function LoginPage() {
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

  const initDate: LoginForm = {
    username: "",
    password: "",
  };
  const schema = Yup.object({
    username: Yup.string().required(),
    password: Yup.string().required(),
  });
  const { register, handleSubmit } = useForm<LoginForm>({
    defaultValues: initDate,
  });

  const onSubmit = (data: LoginForm) => {
    console.log(data);
    schema
      .validate(data)
      .then((_) => {
        dispatch(login(data.username, data.password));
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
    <div className="w-1/3 h-fit mx-auto my-40 p-10 border-2  border-gray-200 rounded-md shadow-md">
      <form onSubmit={handleSubmit(onSubmit)}>
        <h1 className="font-bold text-3xl py-10">Sign in to Twitter</h1>
        <div className="flex flex-col gap-10">
          <TextField
            label="Username"
            variant="outlined"
            fullWidth
            {...register("username")}
          />

          <TextField
            label="Password"
            variant="outlined"
            fullWidth
            type={"password"}
            {...register("password")}
          />
          <Button variant="contained" type="submit" fullWidth>
            Log In
          </Button>
        </div>
      </form>
      <h2 className="text-center font-normal mt-16 text-gray-600">
        Don't have an account? 
        <Link className="text-blue-500 underline px-2" to={"/signup"}>
            Sign up
        </Link>
      </h2>
    </div>
  );
}

export default LoginPage;
