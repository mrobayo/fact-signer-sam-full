import React, {useEffect, useRef, useState} from 'react';
import withAuth from "../../services/auth/withAuth.tsx";
import Box from '@mui/material/Box';
import {useParams, useNavigate} from "react-router-dom";
import {GridItem} from "../../components/ui";
import {useRouterQuery} from "../../util";
import {useForm} from "react-hook-form";
import isEmpty from "lodash/isEmpty";
import { useMutation } from '@tanstack/react-query';

import {facturaSchema, getFacturaLabel} from "./factura.types.ts";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import {yupResolver} from "@hookform/resolvers/yup";

import Grid from '@mui/material/Grid';
import Paper from '@mui/material/Paper';
import {TextFieldProps} from "@mui/material/TextField/TextField";

import facturaService, {facturaEmpty, FacturaType} from "../../services/factura/facturaService.ts";
import {useGetCliente, useGetFactura} from "../../services";

import IconButton from "@mui/material/IconButton";
import RequestQuoteOutlinedIcon from '@mui/icons-material/RequestQuoteOutlined';
import SearchIcon from '@mui/icons-material/Search';
import LockIcon from '@mui/icons-material/Lock';
import LockOpenIcon from '@mui/icons-material/LockOpen';
import {useAuth} from "../../services/auth/useAuth.ts";
import TopToolbar from "../../components/ui/TopToolbar/TopToolbar.tsx";
import {ToolbarTitle} from "../../components/ui/ToolbarTitle/ToolbarTitle.tsx";

import Tooltip from "@mui/material/Tooltip";
import SearchCliente from "../../components/SearchCliente/SearchCliente.tsx";

const EditFactura: React.FC = () => {
  const auth = useAuth();

  const { id } = useParams();
  const { isNew, query } = useRouterQuery();
  const navigate = useNavigate();

  const [clienteId, setClienteId] = useState<string>();
  const { data: cliente } = useGetCliente(clienteId);
  const [isClienteDialogOpen, setClienteDialogOpen] = useState(false);

  const [isReadMode, setReadMode] = useState(!isNew);
  const {
      //control,
      register,
      reset,
      handleSubmit,
      formState: { errors }
    } = useForm<FacturaType>({resolver: yupResolver(facturaSchema)});
  const formRef = useRef<HTMLFormElement>(null);

  // const queryClient = useQueryClient();
  const { data: factura } = useGetFactura(id);
  const { mutate: saveFactura } = useMutation({
      mutationFn: (body: FacturaType) => facturaService.create(body),
    }
  );

  useEffect(() => {
    const clienteId = query.get("clienteId") ?? undefined;
    setClienteId(clienteId);
  }, [query]);

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
    console.log(factura, newFactura);
    reset(isNew ? newFactura : {...factura});
  }, [factura, cliente, reset]);

  const onFormSubmit = handleSubmit((data) => {
    console.log('submit...', data);
    saveFactura(data);
    navigate('/facturas');
  })

  const buildTextField = (name: keyof FacturaType, textFieldProps?: TextFieldProps) => {
    const label = getFacturaLabel(name);
    return <TextField
              id={`id-${name}`}
              label={label}
              error={!isEmpty(errors[name])}
              helperText={errors[name]?.message}
              disabled={isReadMode}
              inputProps={isReadMode ? {readOnly: true} : {}}
              InputLabelProps={{disableAnimation: true, shrink: true}}
              fullWidth
              {...textFieldProps}
              {...register(name)}
          />;
  }

  return (
    <div>
      <TopToolbar>
        <ToolbarTitle Icon={RequestQuoteOutlinedIcon}>
          Factura <b>{isNew ? 'Nueva' : factura?.name}</b>
        </ToolbarTitle>
        {!isNew && <IconButton onClick={() => setReadMode(!isReadMode)}>
          {isReadMode ? <LockIcon /> : <LockOpenIcon />}
        </IconButton>}
      </TopToolbar>

      <Box
        component="form"
        ref={formRef} noValidate autoComplete="off" onSubmit={onFormSubmit}
        sx={{ width: '100%', backgroundColor: 'white' }}>

        <Paper variant="outlined">
        <Grid
          container
          spacing={3}
          sx={{ p: 4 }}
        >
          <GridItem sm={1} sx={{textAlign: 'right'}}>
            <Tooltip title="Buscar Cliente">
              <IconButton onClick={() => setClienteDialogOpen(true)}><SearchIcon fontSize={"large"} /></IconButton>
            </Tooltip>
          </GridItem>
          <GridItem sm={2}>{buildTextField('tipoIdentificacionComprador', {disabled: true, inputProps: {readOnly: true}})}</GridItem>
          <GridItem sm={3}>{buildTextField('identificacionComprador', {disabled: true, inputProps: {readOnly: true}})}</GridItem>
          <GridItem sm={6}>{buildTextField('razonSocialComprador', {disabled: true, inputProps: {readOnly: true}})}</GridItem>

          <GridItem sm={12}>{buildTextField('observacion', {multiline: true, rows: 4})}</GridItem>

          <GridItem>
            <Grid container direction="row" justifyContent="space-between" alignItems="center">
            <Button onClick={() => navigate(-1)}>Cancel</Button>
            <Button variant="contained"
                onClick={() => { formRef?.current?.requestSubmit(); }}
                disabled={isReadMode}
                //{isLoading || isSaving || !isEmpty(errorLoading)}
                //startIcon={isSaving && <CircularProgress size={16}/>}
            >Submit</Button>
            </Grid>
          </GridItem>
        </Grid>
        </Paper>

      </Box>
      <SearchCliente
        cliente={cliente}
        onSelect={(cliente) => {console.log(cliente);}}
        isVisible={isClienteDialogOpen}
        setVisible={setClienteDialogOpen}
      />
    </div>
  )
};

export default withAuth(EditFactura);
