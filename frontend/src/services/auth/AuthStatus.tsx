import {useAuth} from "./useAuth.ts";

function AuthStatus() {
  const auth = useAuth();

  if (!auth.user) {
    return <p>You are not logged in.</p>;
  }

  return (
    <p>
      Welcome {auth.user}!{" "}
    </p>
  );
}

export default AuthStatus;
