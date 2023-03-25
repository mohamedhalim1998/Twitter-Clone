import { useEffect } from "react";
import { Navigate } from "react-router";
import { Outlet, useLocation } from "react-router-dom";
import AuthState from "../model/AuthState";
import { updateAuthLoading, verifyToken } from "../store/AuthReducer";
import { useAppDispatch, useAppSelector } from "../store/hooks";
import { RootState } from "../store/Store";

const ProtectedRoute: React.FC = () => {
  const dispatch = useAppDispatch();
  const auth: AuthState = useAppSelector((state: RootState) => state.auth);
  const location = useLocation();
  useEffect(() => {
    if (!auth.verified) {
      dispatch(updateAuthLoading(true));
      dispatch(verifyToken());
    }
  }, [auth]);
  console.log(auth);
  if (auth.loading) {
    return <div>loading...</div>;
  }
  return auth.verified == true ? (
    <Outlet />
  ) : (
    <Navigate to="/signup" state={{ from: location }} />
  );
};
export default ProtectedRoute;
