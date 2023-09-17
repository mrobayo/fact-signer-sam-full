import React, {useEffect, useMemo, useRef, useState} from 'react';
import withAuth from "../../services/auth/withAuth.tsx";
import Box from '@mui/material/Box';
import {useParams, useNavigate} from "react-router-dom";
import {GridItem} from "../../components/ui";
import {formatAmount, formatCurrency, useRouterQuery} from "../../util";
import {Controller, useFieldArray, useForm} from "react-hook-form";
import isEmpty from "lodash/isEmpty";
import { useMutation } from '@tanstack/react-query';

import {facturaSchema, getFacturaLabel} from "./factura.types.ts";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import {yupResolver} from "@hookform/resolvers/yup";

import Grid from '@mui/material/Grid';
import Paper from '@mui/material/Paper';
import TableRow from "@mui/material/TableRow";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableHead from "@mui/material/TableHead";
import Checkbox from "@mui/material/Checkbox";
import TableContainer from "@mui/material/TableContainer";

import {TextFieldProps} from "@mui/material/TextField/TextField";

import facturaService, {facturaEmpty, FacturaType, ImpuestoType} from "../../services/factura/facturaService.ts";
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
import {DetailTable} from "./EditFactura.style";
import {DetailCell, DetailToolCell} from "../EditFacturaPage/EditFactura.style.tsx";
import FormHelperText from "@mui/material/FormHelperText";
// import NotInterestedIcon from "@mui/icons-material/NotInterested";
// import InformationIcon from "../../components/icons/InformationIcon.tsx";
import EditIcon from "@mui/icons-material/Edit";
import DetalleToolbar from "../EditFacturaPage/DetalleToolbar.tsx";

const ColDefinition: {
  label: string;
  align: "right" | "left" | "center" | "inherit" | "justify" | undefined;
  style: object
}[] = [
  { label: '', align: 'right', style: {width: '10px'} },
  { label: 'Descripcion', align: 'left', style: {minWidth: '300px', flexGrow: 1} },
  { label: 'Cantidad', align: 'right', style: {width: '120px'} },
  { label: 'Precio', align: 'right', style: {width: '180px'} },
  { label: 'Dscto.', align: 'right', style: {width: '200px'} },
  { label: 'Subtotal', align: 'right', style: {width: '180px'} },
  { label: '', align: 'left', style: {width: '1px'} },
  { label: '', align: 'center', style: {width: '1px'} },
];

const EditFactura: React.FC = () => {
  const auth = useAuth();

  const navigate = useNavigate();
  const { id } = useParams();
  const { isNew, query } = useRouterQuery();

  // Cliente
  const { data: cliente } = useGetCliente(query.get("clienteId") ?? undefined);
  const [isClienteDialogOpen, setClienteDialogOpen] = useState(false);

  const [isReadMode, setReadMode] = useState(!isNew);

  const [selected, setSelected] = React.useState<readonly number[]>([]);
  const [editDetalleId, setEditDetalleId] = useState<number>();
  //const [isEditImpuesto, setEditImpuesto] = useState(false);

  const {
    control,
    getValues,
    setValue,
    register,
    watch,
    clearErrors,
    setError,
    reset,
    trigger,
    handleSubmit,
      formState: { errors }
    } = useForm<FacturaType>({resolver: yupResolver(facturaSchema)});

  const {
    fields,
    append,
    remove,
  } = useFieldArray({
    control,
    name: "detalles",
    rules: { validate: (data) => {
      if (data.length === 0) {
        return "requerido";
      }
      return true;
    }}
  });

  const formRef = useRef<HTMLFormElement>(null);
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
    // console.log(factura, newFactura);
    reset(isNew ? newFactura : {...factura});
  }, [factura, cliente, reset]);

  const watchDetalle = watch("detalles");

  const onFormSubmit = handleSubmit((data) => {
    console.log('submit...', data);
    saveFactura(data);
    navigate('/facturas');
  });

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

  const handleAppendRow = () => {
    const detalles = getValues("detalles");
    //const inconsistentEditRow = detalles.length === 0 || detalles.length-1 < editRow;
    //if (inconsistentEditRow || editRow < 0 || computePrecioSinImpuestos()) {
      const linea = 1 + detalles.reduce(
      (acc, { linea }) => linea && linea > acc ? linea: acc, 0);

      setEditDetalleId(linea);
      append({
        linea,
        codigoPrincipal: "",
        descripcion: "",
        cantidad: 1,
        precioUnitario: 0,
        descuento: 0,
        precioTotalSinImpuesto: 0,
        iva: {} as ImpuestoType
      });
      setSelected([]);
    //}
  }

  const handleSelected = (linea: number) => {
    const selectedIndex = selected.indexOf(linea);
    let newSelected: readonly number[] = [];
    if (selectedIndex === -1) {
      newSelected = newSelected.concat(selected, linea);
    } else if (selectedIndex === 0) {
      newSelected = newSelected.concat(selected.slice(1));
    } else if (selectedIndex === selected.length - 1) {
      newSelected = newSelected.concat(selected.slice(0, -1));
    } else if (selectedIndex > 0) {
      newSelected = newSelected.concat(
        selected.slice(0, selectedIndex),
        selected.slice(selectedIndex + 1)
      );
    }
    setSelected(newSelected);
  }

  const handleRemove = () => {
    const indexList = getValues('detalles')
      .map(({ linea }, index) => selected.includes(linea) ? index : null)
      .filter(index => index !== null) as number[];

    if (editDetalleId && selected.includes(editDetalleId)) {
      setEditDetalleId(undefined);
    }
    setSelected([]);
    remove(indexList);
  }

  const handleEditRow = (index: number) => {
    const detalles = getValues("detalles");
    setEditDetalleId(detalles[index].linea);
  }

  useEffect(() => {
    if (editDetalleId) {
      const detalles = getValues("detalles");
      const index = detalles.findIndex(({linea}) => linea === editDetalleId);

      const descripcionInput = document.getElementById(`detalles.${index}.descripcion`) as HTMLInputElement;
      //const descripcionInput = formRef.current?.querySelector(`#detalles.${editDetalleId}.descripcion`) as HTMLInputElement;
      descripcionInput && descripcionInput.focus();
    }
  }, [editDetalleId]);

  const computePrecioSinImpuestos = (): boolean => {
    const detalles = getValues("detalles");
    const detallesLen = detalles.length;
    const index = detalles.findIndex(({linea}) => linea === editDetalleId);
    if (index === -1) {
      console.log('index', -1);
      return false;
    }
    const detalle = detalles[index];

    const valor = (+detalle.precioUnitario) * (+detalle.cantidad);
    setValue(`detalles.${index}.precioTotalSinImpuesto`, +valor.toFixed(2),
      //{shouldDirty: true, shouldTouch: true, shouldValidate: true}
    );

    let isRowValid = true;
    // VALIDATE ROW
    if (valor === 0) {
      console.log('2 isRowValid');
      isRowValid = false;
      setError(`detalles.${index}.precioTotalSinImpuesto`,
        { type: 'manual', message:'Subtotal Sin Impuesto debe ser mayor a cero' }
      );
    }
    if (isEmpty(detalle.descripcion)) {
      console.log('3 isRowValid');
      isRowValid = false;
      trigger(`detalles.${index}.descripcion`);
    }
    if (isRowValid) {
      console.log('4 isRowValid', isRowValid);
      clearErrors(`detalles.${index}.precioTotalSinImpuesto`);
      if (!detalles[detallesLen-1].descripcion) {
        remove(detallesLen-1);
      }
    }
    return isRowValid;
  };

  const handleKeyEnter = (event: React.KeyboardEvent<HTMLFormElement>) => {
    if (event.key !== "Enter") {
      return;
    }
    event.preventDefault();
    const target= event.target as HTMLFormElement;

    if (target.id.endsWith('descuento')) {

      if (computePrecioSinImpuestos()) {
        console.log('3');
        handleAppendRow();
      }
    }
    if (target.id.startsWith('detalles')) {
      const form = target.form;
      if (form) {
        const index = [...form].indexOf(event.target);
        const nextElement = form[index + 2];
        nextElement && nextElement.focus();
      }
    }
  };

  // const handleUpdateImpuestos = (data: ImpuestoType) => {
  //   const detalles = getValues("detalles");
  //   const index = detalles.findIndex(({linea}) => linea === editDetalleId);
  //   if (index !== -1) {
  //     console.log(`index: ${index}`, [data]);
  //     // setValue(`detalles.${index}.impuestos`, [data]);
  //   }
  //   setEditImpuesto(false);
  // };

  // const [_detalleIndex, detalle] = useMemo(() => {
  //   const detalles = watchDetalle;
  //   const index = detalles.findIndex(({linea}) => linea === editDetalleId);
  //   const detalle = index !== -1 ? detalles[index] : null;
  //   return [index, detalle];
  // }, [watchDetalle, editDetalleId]);

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
        ref={formRef}
        component="form"
        noValidate
        autoComplete="off"
        onSubmit={onFormSubmit}
        sx={{ width: '100%', backgroundColor: 'white' }}
        onKeyDown={(e) => handleKeyEnter(e)}
        data-test-id="factura-form">

        <Paper variant="outlined">
        <Grid container spacing={3} sx={{ p: 4 }}>
          <GridItem sm={1} sx={{textAlign: 'right'}}>
            <Tooltip title="Buscar Cliente">
              <IconButton onClick={() => setClienteDialogOpen(true)}><SearchIcon fontSize={"large"} /></IconButton>
            </Tooltip>
          </GridItem>
          <GridItem sm={2}>{buildTextField('tipoIdentificacionComprador', {disabled: true, inputProps: {readOnly: true}})}</GridItem>
          <GridItem sm={3}>{buildTextField('identificacionComprador', {disabled: true, inputProps: {readOnly: true}})}</GridItem>
          <GridItem sm={6}>{buildTextField('razonSocialComprador', {disabled: true, inputProps: {readOnly: true}})}</GridItem>

          <GridItem sm={12} data-test-id="factura-detalles">
            <DetalleToolbar error={errors?.detalles?.root?.message} numSelected={selected.length} appendNew={handleAppendRow} removeSelected={handleRemove} />
            <TableContainer sx={{ maxHeight: 440 }}>
              <DetailTable sx={{ minWidth: 650 }} stickyHeader aria-label="Factura Detalle">
                <TableHead>
                <TableRow>
                  {ColDefinition.map((col, index) =>
                    <TableCell key={`det-head-${index}`} align={col.align} style={col.style}>{col.label}</TableCell>
                  )}
                </TableRow>
              </TableHead>
              <TableBody>
                {fields.map((item, index) => {
                  const isEditMode = editDetalleId === item.linea;
                  const isItemSelected = selected.indexOf(item.linea) !== -1;
                  const labelId = `detcell.${index}.descripcion`;
                  return (
                    <TableRow
                      hover
                      role="checkbox"
                      aria-checked={isItemSelected}
                      tabIndex={-1}
                      selected={isItemSelected}
                      key={item.id}
                      sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                    >
                      <DetailCell align="right" onClick={() => handleSelected(item.linea)} sx={{ cursor: "pointer" }}>
                        <Checkbox color="primary" checked={isItemSelected} inputProps={{'aria-labelledby': labelId}} />
                      </DetailCell>
                      <DetailCell align="left" id={labelId}>
                        {isEditMode &&
                          <TextField
                            margin="normal"
                            fullWidth
                            id={`detalles.${index}.descripcion`}
                            label=""
                            error={errors?.detalles?.[index]?.descripcion !== undefined}
                            helperText={errors?.detalles?.[index]?.descripcion?.message}
                            {...register(`detalles.${index}.descripcion`,
                              { required: true })}
                          />
                        }
                        {!isEditMode && item.descripcion}
                      </DetailCell>
                      <DetailCell align="right">
                          {isEditMode &&
                            <TextField
                              margin="normal"
                              fullWidth
                              id={`detalles.${index}.cantidad`}
                              label=""
                              type="number"
                              error={errors?.detalles?.[index]?.cantidad !== undefined}
                              helperText={errors?.detalles?.[index]?.cantidad?.message}
                              {...register(`detalles.${index}.cantidad`, { required: true, min: 0.0001 })}
                            />
                          }
                          {!isEditMode && item.cantidad && formatAmount(item.cantidad)}
                        </DetailCell>
                        <DetailCell align="right">
                          {isEditMode &&
                            <Controller
                              render={({ field }) =>
                                <TextField
                                  margin="normal"
                                  fullWidth
                                  id={`detalles.${index}.precioUnitario`}
                                  label=""
                                  type="number"
                                  {...field}
                                />
                              }
                              defaultValue={0}
                              name={`detalles.${index}.precioUnitario`}
                              control={control}
                            />
                          }
                          {!isEditMode && item.precioUnitario && formatCurrency(item.precioUnitario)}
                        </DetailCell>
                        <DetailCell align="right">
                          {isEditMode &&
                            <TextField
                              margin="normal"
                              fullWidth
                              id={`detalles.${index}.descuento`}
                              label=""
                              type="number"
                              error={errors?.detalles?.[index]?.descuento !== undefined}
                              helperText={errors?.detalles?.[index]?.descuento?.message}
                              {...register(`detalles.${index}.descuento`)}
                            />
                          }
                          {!isEditMode && item.descuento && formatAmount(item.cantidad)}
                        </DetailCell>
                        <DetailCell align="right">
                          {item.precioTotalSinImpuesto && formatCurrency(item.precioTotalSinImpuesto)}
                          {errors?.detalles?.[index]?.precioTotalSinImpuesto && <FormHelperText error>{errors?.detalles?.[index]?.precioTotalSinImpuesto?.message}</FormHelperText>}
                        </DetailCell>
                        <DetailCell align="center">
                          {/*<Tooltip*/}
                          {/*  title={isEditMode ? "Actualizar Tarifa del IVA" : `Impuestos IVA ${item?.impuestos?.[0]?.tarifa}%`}*/}
                          {/*>*/}
                          {/*  <span><IconButton*/}
                          {/*    disabled={!isEditMode}*/}
                          {/*    color="info"*/}
                          {/*    size="small"*/}
                          {/*    onClick={() => handleImpuestos()}>*/}
                          {/*    {item?.impuestos?.[0]?.tarifa == 0 ? <NotInterestedIcon sx={{ fontSize: 12 }} /> : <InformationIcon sx={{ fontSize: 12 }} />}*/}
                          {/*  </IconButton></span>*/}
                          {/*</Tooltip>*/}
                        </DetailCell>
                        <DetailToolCell align="right">
                          <IconButton disabled={isEditMode} color="primary" onClick={() => handleEditRow(index)}><EditIcon /></IconButton>
                        </DetailToolCell>
                     </TableRow>
                  );
                })
              }
              </TableBody>



              </DetailTable>
            </TableContainer>
          </GridItem>

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
        onSelect={(cliente) => {console.log(cliente);}}
        isVisible={isClienteDialogOpen}
        setVisible={setClienteDialogOpen}
      />
    </div>
  )
};

export default withAuth(EditFactura);
