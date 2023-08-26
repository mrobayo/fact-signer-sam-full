import RequireAuth from "./RequireAuth.tsx";

const withAuth = (Component: React.ComponentType) => (props: object) => {
  return <RequireAuth><Component {...props} /></RequireAuth>;
};

export default withAuth;