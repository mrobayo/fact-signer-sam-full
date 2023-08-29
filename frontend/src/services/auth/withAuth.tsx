import RequireAuth from "./RequireAuth.tsx";

const withAuth = (Component: React.ComponentType | React.FC<any>) => (props: object) => {
  return <RequireAuth><Component {...props} /></RequireAuth>;
};

export default withAuth;