import React from 'react';
import withAuth from "../../services/auth/withAuth.tsx";

const Admin: React.FC = () => {
  return (
    <div>Admin Page</div>
  )
};

export default withAuth(Admin);
