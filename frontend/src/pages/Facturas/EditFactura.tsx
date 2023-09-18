import React, {useEffect, useState} from 'react';

import {useParams, useNavigate} from "react-router-dom";
import {FormProvider, useForm} from "react-hook-form";
import { useMutation } from '@tanstack/react-query';
import {yupResolver} from "@hookform/resolvers/yup";
import IconButton from "@mui/material/IconButton";
import RequestQuoteOutlinedIcon from '@mui/icons-material/RequestQuoteOutlined';
import LockIcon from '@mui/icons-material/Lock';
import LockOpenIcon from '@mui/icons-material/LockOpen';

import withAuth from "../../services/auth/withAuth";
import {facturaSchema} from "./factura.types";
import {useRouterQuery} from "../../util";
import facturaService, {facturaEmpty, FacturaType} from "../../services/factura/facturaService";
import {useGetCliente, useGetFactura} from "../../services";

import {useAuth} from "../../services/auth/useAuth";
import TopToolbar from "../../components/ui/TopToolbar/TopToolbar";
import {ToolbarTitle} from "../../components/ui/ToolbarTitle/ToolbarTitle";
import SearchCliente from "../../components/SearchCliente/SearchCliente";
import DetallesForm from "./DetallesForm";

// import NotInterestedIcon from "@mui/icons-material/NotInterested";
// import InformationIcon from "../../components/icons/InformationIcon";

const EditFactura: React.FC = () => {
  const auth = useAuth();
  const navigate = useNavigate();
  const { id } = useParams();
  const { isNew, query } = useRouterQuery();

  // Cliente
  const { data: cliente } = useGetCliente(query.get("clienteId") ?? undefined);

  const [isClienteDialogOpen, setClienteDialogOpen] = useState(false);
  const [isReadMode, setReadMode] = useState(!isNew);

  //const [isEditImpuesto, setEditImpuesto] = useState(false);

  // const {
  //   reset,
  //   handleSubmit,
  // } = useForm<FacturaType>({resolver: yupResolver(facturaSchema)});

  const methods = useForm<FacturaType>({resolver: yupResolver(facturaSchema)});
  const { reset, handleSubmit } = methods;

  const { data: factura } = useGetFactura(id);
  const { mutate: saveFactura } = useMutation({
      mutationFn: (body: FacturaType) => facturaService.create(body),
    }
  );

  useEffect(() => {
    const puntoVentaId = auth.puntoVenta.id;
    const moneda = auth.puntoVenta.empresa.moneda;
    const {empresaId} = auth.puntoVenta ?? {};
    const newFactura = facturaEmpty({
      empresaId,
      puntoVentaId,
      moneda,
      clienteId: cliente?.id,
      clienteName: cliente?.name,
      tipoIdentificacionComprador: cliente?.tipo,
      razonSocialComprador: cliente?.name,
      identificacionComprador: cliente?.identidad,
      direccionComprador: cliente?.direccion
    });
    reset(isNew ? newFactura : {...factura});
  }, [factura, cliente, reset]);

  //const watchDetalle = watch("detalles");

  const onFormSubmit = handleSubmit((data) => {
    console.log('submit...', data);
    saveFactura(data);
    navigate('/facturas');
  });

  return (
    <div>
      <TopToolbar>
        <ToolbarTitle Icon={RequestQuoteOutlinedIcon}>
          Factura <b>{isNew ? 'Nueva' : factura?.name}</b>
        </ToolbarTitle>
        {!isNew &&
          <IconButton onClick={() => setReadMode(!isReadMode)}>
            {isReadMode ? <LockIcon /> : <LockOpenIcon />}
          </IconButton>
        }
      </TopToolbar>

      <FormProvider {...methods}>
        <DetallesForm
          isReadMode={isReadMode}
          setClienteDialogOpen={setClienteDialogOpen}
          onSubmit={onFormSubmit}
        />
      </FormProvider>

      <SearchCliente
        onSelect={(cliente) => {console.log(cliente);}}
        isVisible={isClienteDialogOpen}
        setVisible={setClienteDialogOpen}
      />
    </div>
  )
};

export default withAuth(EditFactura);
