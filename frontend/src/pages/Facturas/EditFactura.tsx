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
import {ClienteType, useGetCliente, useGetFactura} from "../../services";

import {useAuth} from "../../services/auth/useAuth";
import TopToolbar from "../../components/ui/TopToolbar/TopToolbar";
import {ToolbarTitle} from "../../components/ui/ToolbarTitle/ToolbarTitle";
import SearchCliente from "../../components/SearchCliente/SearchCliente";
import DetallesForm from "./DetallesForm";
import {SummaryType} from "./FacturaSummary.tsx";

const EditFactura: React.FC = () => {
  const auth = useAuth();
  const {id: puntoVentaId, empresa} = auth.puntoVenta ?? {};

  const navigate = useNavigate();
  const { id } = useParams();
  const { isNew, query } = useRouterQuery();

  // Cliente
  const { data: cliente } = useGetCliente(query.get("clienteId") ?? undefined);

  const [isClienteDialogOpen, setClienteDialogOpen] = useState(false);
  const [isReadMode, setReadMode] = useState(!isNew);

  const methods = useForm<FacturaType>(
    {resolver: yupResolver(facturaSchema)}
  );
  const { reset, setValue, handleSubmit } = methods;

  const { data: factura } = useGetFactura(id);
  const { mutate: saveFactura } = useMutation({
      mutationFn: (body: FacturaType) => facturaService.create(body),
    }
  );

  const [currentRow, setCurrentRow] = useState(0);
  const [subtotal, setSubtotal] = useState<number[]>([]);
  const [summary, setSummary] = useState<SummaryType>({});
  const detalles = methods.watch('detalles', []);

  useEffect(() => {
    const newSubTotal = [] as number[];

    let subTotal = 0;
    let subTotalIVA = 0;
    let valorTOTAL = 0;
    detalles.forEach((detalle, index) => {
      newSubTotal[index] =
        (+detalle.precioUnitario) * (+detalle.cantidad) - (+detalle.descuento);
      subTotalIVA += detalle.iva ? newSubTotal[index] : 0;
      subTotal += newSubTotal[index];
    });

    valorTOTAL = subTotal + 0;

    setSubtotal(newSubTotal);
    setSummary({ subTotal, subTotalIVA, valorTOTAL });

  }, [detalles, currentRow]);

  useEffect(() => {
    const newFactura = facturaEmpty({
      empresaId: empresa.id,
      puntoVentaId,
      moneda: empresa.moneda,
      clienteId: cliente?.id,
      clienteName: cliente?.name,
      tipoIdentificacionComprador: cliente?.tipo,
      razonSocialComprador: cliente?.name,
      identificacionComprador: cliente?.identidad,
      direccionComprador: cliente?.direccion
    });
    reset(isNew ? newFactura : {...factura});
  }, [isNew, factura, cliente, reset]);

  const onFormSubmit = handleSubmit((data, event) => {
    event?.preventDefault();
    console.log('submit...', data);
    //saveFactura(data);
    //navigate('/facturas');
  });

  const handleSelectCliente = (cliente: ClienteType) => {
    setValue(`razonSocialComprador`, cliente.name);
    setValue(`tipoIdentificacionComprador`, cliente.tipo);
    setValue(`direccionComprador`, cliente.direccion);
    setValue(`identificacionComprador`, cliente.identidad); //{shouldDirty: true, shouldTouch: true, shouldValidate: true}
  };

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
          empresa={empresa}
          subtotal={subtotal}
          summary={summary}
          isReadMode={isReadMode}
          setClienteDialogOpen={setClienteDialogOpen}
          onSubmit={onFormSubmit}
          setCurrentRow={setCurrentRow}
        />
      </FormProvider>

      <SearchCliente
        onSelect={handleSelectCliente}
        isVisible={isClienteDialogOpen}
        setVisible={setClienteDialogOpen}
      />
    </div>
  )
};

export default withAuth(EditFactura);
