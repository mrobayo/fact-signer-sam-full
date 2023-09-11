import {useAuth} from "./useAuth.ts";

function AuthStatus() {
  const auth = useAuth();

  if (!auth.user?.login) {
    return <p>You are not logged in.</p>;
  }

  return (
    <p>
      Welcome {auth.user?.login}!{" "}
    </p>
  );
}

export default AuthStatus;
