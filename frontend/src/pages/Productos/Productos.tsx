import React from 'react';
import withAuth from "../../services/auth/withAuth.tsx";

const Productos: React.FC = () => {
  return (
    <div>Productos Page</div>
  )
};

export default withAuth(Productos);
