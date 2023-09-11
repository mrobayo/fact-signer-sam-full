import React from 'react';
import withAuth from "../../services/auth/withAuth.tsx";

const Clientes: React.FC = () => {
  return (
    <div>Clientes Page</div>
  )
};

export default withAuth(Clientes);
