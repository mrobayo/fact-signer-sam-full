import React, {useRef} from 'react';
import withAuth from "../../services/auth/withAuth.tsx";
import Box from '@mui/material/Box';
import {useParams, useNavigate} from "react-router-dom";
import {GridItem, Title} from "../../components/ui";
import PeopleAltTwoToneIcon from "@mui/icons-material/PeopleAltTwoTone";
import {useRouterQuery} from "../../util";
import {useForm, Controller} from "react-hook-form";
import isEmpty from "lodash/isEmpty";
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
import {ClienteType} from "../../services/clienteService";

const EditCliente: React.FC = () => {
  const { id } = useParams();
  const { isNew } = useRouterQuery();
  const navigate = useNavigate();
  const { data: gruposCliente } = useGrupos();
  const {
      control,
      register,
      // reset,
      handleSubmit,
      formState: { errors }
    } = useForm<ClienteType>({resolver: yupResolver(clienteSchema)});
    const formRef = useRef<HTMLFormElement>(null);

  const onFormSubmit = handleSubmit((data) => {
    console.log('submit...', data)
  })
  const isReadOnly = false;

  const buildTextField = (name: keyof ClienteType, textFieldProps?: TextFieldProps) => {
    const label = getClienteLabel(name);
    return <TextField
              id={`id-${name}`}
              label={label}
              error={!isEmpty(errors[name])}
              helperText={errors[name]?.message}
              disabled={isReadOnly}
              fullWidth
              {...textFieldProps}
              {...register(name)}
          />;
  }

  return (
    <div>
      <Title><PeopleAltTwoToneIcon sx={{ m: 2, mb: '-4px' }} /> Cliente {isNew ? 'Nuevo' : id}</Title>
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
                <DateField label="Fecha Nacimiento"
                  helperText={errors.birthday?.message}
                  sx={{ width: '100%' }} {...field} />
              }
            />
          </GridItem>

          <GridItem sm={5}>{buildTextField('email')}</GridItem>
          <GridItem sm={7}>{buildTextField('direccion')}</GridItem>

          <GridItem sm={5}>{buildTextField('grupoId', {
            select: true, SelectProps: { native: true },
            children: gruposCliente?.map((option) => (
              <option key={option.id} value={option.id}>{option.name}</option>
            ))
          })}</GridItem>
          <GridItem sm={3}>{buildTextField('pais')}</GridItem>
          <GridItem sm={4}>{buildTextField('ciudad')}</GridItem>

          <GridItem sm={12}>{buildTextField('contacto')}</GridItem>
          <GridItem sm={12}>{buildTextField('observacion', {multiline: true, rows: 4})}</GridItem>

          {/*<GridItem md={4}>{buildTextField('nombres')}</GridItem>*/}
          {/*<GridItem md={4}>{buildTextField('apellidos')}</GridItem>*/}
          {/*<GridItem md={1}>{buildTextField('tipo')}</GridItem>*/}
          {/*<GridItem md={3}>{buildTextField('identidad')}</GridItem>*/}

          {/*<GridItem md={3}>{buildTextField('telefono')}</GridItem>*/}
          {/*<GridItem md={6}>{buildTextField('email')}</GridItem>*/}
          {/*<GridItem md={3}>*/}
          {/*  <DatePicker label="Fecha Nacimiento" />*/}
          {/*</GridItem>*/}
          {/*<GridItem md={6}>{buildTextField('direccion')}</GridItem>*/}
          {/*<GridItem md={2}>{buildTextField('pais')}</GridItem>*/}
          {/*<GridItem md={4}>{buildTextField('ciudad')}</GridItem>*/}

          {/*<GridItem md={6}>{buildTextField('observacion', {multiline: true, rows: 4})}</GridItem>*/}
          {/*<GridItem md={6}>{buildTextField('contacto')}</GridItem>*/}
          {/*<GridItem md={6}>{buildTextField('grupoId')}</GridItem>*/}

          <GridItem md={12}>
            <Button onClick={() => navigate(-1)}>Cancel</Button>
            <Button variant="contained"
                onClick={() => { formRef?.current?.requestSubmit(); }}
                //disabled={isLoading || isSaving || !isEmpty(errorLoading)}
                //startIcon={isSaving && <CircularProgress size={16}/>}
            >Submit</Button>
          </GridItem>
        </Grid>
        </Paper>

      </Box>
    </div>
  )
};

export default withAuth(EditCliente);
