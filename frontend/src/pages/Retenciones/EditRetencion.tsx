import React from 'react';
import withAuth from "../../services/auth/withAuth.tsx";
import {useParams} from "react-router-dom";

const EditRetencion: React.FC = () => {
  const { id} = useParams();

  return (
    <div>Edit Retencion {id}</div>
  )
};

export default withAuth(EditRetencion);
