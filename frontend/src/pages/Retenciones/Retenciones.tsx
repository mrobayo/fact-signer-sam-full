import React from 'react';
import withAuth from "../../services/auth/withAuth.tsx";

const Retenciones: React.FC = () => {
  return (
    <div>Retenciones Page</div>
  )
};

export default withAuth(Retenciones);
