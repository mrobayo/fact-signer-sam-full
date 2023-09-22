import React, {useEffect, useState} from 'react';

import {useParams, useNavigate} from "react-router-dom";
import {FormProvider, useForm} from "react-hook-form";
import {useMutation, useQueryClient} from '@tanstack/react-query';
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
import Button from "@mui/material/Button";
import CheckCircleIcon from '@mui/icons-material/CheckCircle';
import {useNotify} from "../../notification";
import {AxiosError} from "axios";
// import ApproveIcon from "@mui/icons-material/CheckCircleOutline";

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

  //const addNotification = useAddNotificationContext();
  const notify = useNotify();

  const queryClient = useQueryClient();
  const { data: factura } = useGetFactura(id);
  const { mutate: saveFactura, status } = useMutation({
      mutationFn: (body: FacturaType) => facturaService.create(body),
      onSuccess: (data) => { // Invalidate and refetch
        queryClient.invalidateQueries({ queryKey: ['facturas'] })
        navigate(`/facturas/edit/${data.id}`);
      },
      onError: (err: AxiosError<{message: string}>) => {
        const message = err?.response?.data?.message ?? String(err);
        notify(message ?? String(err), { type: "error"})
      }
    }
  );
  const { mutate: approveFactura } = useMutation({
      mutationFn: (id: string) => facturaService.approve(id),
    }
  );

  console.log('status', status);
  console.log(`factura ${id}`, factura);

  const [currentRow, setCurrentRow] = useState(0);
  const [subtotal, setSubtotal] = useState<number[]>([]);
  const [summary, setSummary] = useState<SummaryType>({});
  const detalles = methods.watch('detalles', []);

  useEffect(() => {
    const newSubTotal = [] as number[];

    let subTotal = 0;
    let subTotalSinImpuestos = 0;
    let subTotalIVA = 0;
    let subTotalCero = 0;
    let valorIVA = 0;
    let valorTOTAL = 0;
    let totalDescuento = 0;

    detalles.forEach((detalle, index) => {
      const precioSinImpuestos = (+detalle.precioUnitario) * (+detalle.cantidad);
      const descuento = (+detalle.descuento);
      const baseImponible = precioSinImpuestos - descuento;
      const hasIva = detalle.hasIva ?? false;
      const tarifa = hasIva ? empresa.tarifaIva : 0;
      const valor = tarifa * baseImponible / 100;

      newSubTotal[index] = precioSinImpuestos;
      detalle.iva = {
        codigo: 2,
        codigoPorcentaje: hasIva ? 2 : 0,
        tarifa,
        baseImponible: baseImponible,
        valor
      };

      subTotalIVA += detalle.hasIva ? baseImponible : 0;
      subTotalCero += detalle.hasIva ? 0 : baseImponible;
      valorIVA += valor;
      totalDescuento += descuento;
    });

    subTotalSinImpuestos = subTotalIVA + subTotalCero;
    subTotal = subTotalSinImpuestos + totalDescuento;
    valorTOTAL = subTotalSinImpuestos + valorIVA;

    setSubtotal(newSubTotal);
    setSummary({ subTotal, subTotalSinImpuestos, subTotalIVA, subTotalCero, totalDescuento: -1 * totalDescuento, valorIVA, valorTOTAL });

  }, [detalles, currentRow]);

  useEffect(() => {
    const newFactura = facturaEmpty({
      empresaId: empresa.id,
      puntoVentaId,
      moneda: empresa.moneda,
      clienteId: cliente?.id,
      // clienteName: cliente?.name,
      tipoIdentificacionComprador: cliente?.tipo,
      razonSocialComprador: cliente?.name,
      identificacionComprador: cliente?.identidad,
      direccionComprador: cliente?.direccion,
      sujetoEmail: cliente?.email,
    });
    reset(isNew ? newFactura : {...factura});
  }, [isNew, factura, cliente, reset]);

  const onFormSubmit = handleSubmit((data, event) => {
    event?.preventDefault();
    console.log('submit...', data);
    saveFactura(data);
  });

  const handleSelectCliente = (cliente: ClienteType) => {
    setValue(`razonSocialComprador`, cliente.name);
    setValue(`tipoIdentificacionComprador`, cliente.tipo);
    setValue(`direccionComprador`, cliente.direccion);
    setValue(`identificacionComprador`, cliente.identidad); //{shouldDirty: true, shouldTouch: true, shouldValidate: true}
  };

  const aprobarFactura = () => {
    approveFactura("?");
    console.log('aprobar factura...');
  };

  return (
    <div>
      <TopToolbar>
        <ToolbarTitle Icon={RequestQuoteOutlinedIcon}>
          Factura <b>{isNew ? 'Nueva' : (factura?.name ?? `[${factura?.estadoDoc}]`)}</b>
        </ToolbarTitle>

        <Button
          onClick={aprobarFactura}
          startIcon={<CheckCircleIcon />}>Aprobar</Button>

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
