import React, {useEffect, useRef, useState} from 'react';
import withAuth from "../../services/auth/withAuth.tsx";
import Box from '@mui/material/Box';
import {useParams, useNavigate} from "react-router-dom";
import {GridItem} from "../../components/ui";
import PeopleAltTwoToneIcon from "@mui/icons-material/PeopleAltTwoTone";
import {useRouterQuery} from "../../util";
import {useForm, Controller} from "react-hook-form";
import isEmpty from "lodash/isEmpty";
import { useMutation } from '@tanstack/react-query';

import {clienteSchema, getClienteLabel} from "./cliente.types.ts";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import {yupResolver} from "@hookform/resolvers/yup";

import Grid from '@mui/material/Grid';
import Paper from '@mui/material/Paper';
import {TextFieldProps} from "@mui/material/TextField/TextField";
import {DateField} from "@mui/x-date-pickers";
import {TipoIdentidad} from "../tarifario.ts";
import {useGrupos} from "../../services/grupo/useGrupos";
import clienteService, {clienteEmpty, ClienteType} from "../../services/cliente/clienteService.ts";
import {PaisesIso2} from "../../util/paisesIso.ts";

import {useGetCliente} from "../../services";
import dayjs from "dayjs";
import IconButton from "@mui/material/IconButton";
import LockIcon from '@mui/icons-material/Lock';
import LockOpenIcon from '@mui/icons-material/LockOpen';
import RequestQuoteOutlinedIcon from '@mui/icons-material/RequestQuoteOutlined';

import TopToolbar from "../../components/ui/TopToolbar/TopToolbar.tsx";
import {ToolbarTitle} from "../../components/ui/ToolbarTitle/ToolbarTitle.tsx";

const EditCliente: React.FC = () => {
  const { id } = useParams();
  const { isNew } = useRouterQuery();
  const navigate = useNavigate();
  const [isReadMode, setReadMode] = useState(!isNew);
  const { data: gruposCliente } = useGrupos();
  const {
      control,
      register,
      reset,
      handleSubmit,
      formState: { errors }
    } = useForm<ClienteType>({resolver: yupResolver(clienteSchema)});
  const formRef = useRef<HTMLFormElement>(null);

  const { data: cliente } = useGetCliente(id);
  const { mutate: saveCliente } = useMutation({
      mutationFn: (body: ClienteType) => clienteService.create(body),
    }
  );

  useEffect(() => {
    const birthday = cliente?.birthday && dayjs(cliente.birthday);
    reset(cliente != null ? {...cliente, birthday} : clienteEmpty());
  }, [cliente, reset]);

  const onFormSubmit = handleSubmit((data) => {
    console.log('submit...', data);
    saveCliente(data);
    navigate('/clientes');
  })

  const buildTextField = (name: keyof ClienteType, textFieldProps?: TextFieldProps) => {
    const label = getClienteLabel(name);
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
          <ToolbarTitle Icon={PeopleAltTwoToneIcon} disabled={!cliente?.activo}>
            Cliente <b>{isNew ? 'Nuevo' : cliente?.name}</b>
          </ToolbarTitle>

          {!isNew && <IconButton onClick={() => navigate(`/facturas/new?clienteId=${cliente?.id}`)} disabled={!cliente?.activo}>
            <RequestQuoteOutlinedIcon />
          </IconButton>}

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
          <GridItem sm={5}>{buildTextField('nombres')}</GridItem>
          <GridItem sm={7}>{buildTextField('apellidos')}</GridItem>
          <GridItem sm={2}>{buildTextField('tipo', {
            select: true, SelectProps: { native: true },
            children: TipoIdentidad.map((option) => (
              <option key={option.value} value={option.value}>{option.label}</option>
            ))
          })}</GridItem>
          <GridItem sm={3}>{buildTextField('identidad')}</GridItem>

          <GridItem sm={4}>{buildTextField('telefono')}</GridItem>
          <GridItem sm={3}>
            <Controller
              name="birthday"
              control={control}
              render={({ field }) =>
                <DateField
                  label="Fecha Nacimiento"
                  disabled={isReadMode}
                  helperText={errors.birthday?.message}
                  fullWidth {...field}
                />
              }
            />
          </GridItem>

          <GridItem sm={5}>{buildTextField('email')}</GridItem>
          <GridItem sm={7}>{buildTextField('direccion')}</GridItem>

          <GridItem sm={5}>{buildTextField('grupoId', {
            select: true, SelectProps: { native: true },
            children: (gruposCliente ?? [{id: '', name:'---'}]).map((option) => (
              <option key={option.id} value={option.id}>{option.name}</option>
            ))
          })}</GridItem>
          <GridItem sm={3}>{buildTextField('pais', {
            select: true, SelectProps: { native: true },
            children: PaisesIso2.map((option) => (
              <option key={option.id} value={option.id}>{option.name}</option>
            ))
          })}</GridItem>
          <GridItem sm={4}>{buildTextField('ciudad')}</GridItem>

          <GridItem sm={12}>{buildTextField('contacto')}</GridItem>
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
    </div>
  )
};

export default withAuth(EditCliente);
