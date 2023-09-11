import React from 'react';
import withAuth from "../../services/auth/withAuth.tsx";
import {useParams} from "react-router-dom";

const EditCliente: React.FC = () => {
  const { id} = useParams();

  return (
    <div>Edit Cliente {id}</div>
  )
};

export default withAuth(EditCliente);
