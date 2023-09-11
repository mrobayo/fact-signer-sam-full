import React from 'react';
import withAuth from "../../services/auth/withAuth.tsx";
import {useParams} from "react-router-dom";

const EditProducto: React.FC = () => {
  const { id} = useParams();

  return (
    <div>Edit Producto {id}</div>
  )
};

export default withAuth(EditProducto);
