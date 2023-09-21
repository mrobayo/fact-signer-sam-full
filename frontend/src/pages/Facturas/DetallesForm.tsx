import React, {useRef, useState} from "react";
import isEmpty from "lodash/isEmpty";
import {useNavigate} from "react-router-dom";
import {useFormContext} from "react-hook-form";

import Box from '@mui/material/Box';
import Grid from "@mui/material/Grid";
import Tooltip from "@mui/material/Tooltip";
import IconButton from "@mui/material/IconButton";
import SearchIcon from "@mui/icons-material/Search";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import TableCell from "@mui/material/TableCell";
import TableBody from "@mui/material/TableBody";
import Checkbox from "@mui/material/Checkbox";
import TextField, {TextFieldProps} from "@mui/material/TextField";
import FormHelperText from "@mui/material/FormHelperText";
import EditIcon from "@mui/icons-material/Edit";
import Button from "@mui/material/Button";
import Paper from "@mui/material/Paper";
import {useTheme} from "@mui/material";

import {DetailCell, DetailToolCell} from "../EditFacturaPage/EditFactura.style";
import DetalleToolbar from "../EditFacturaPage/DetalleToolbar";
import {DetailTable} from "./EditFactura.style";
import {GridItem} from "../../components/ui";
import {formatAmount, formatCurrency} from "../../util";
import {DetalleFacturaType, FacturaType} from "../../services";
import {getFacturaLabel} from "./factura.types";
import useDetallesForm from "./useDetallesForm.tsx";

import EditImpuestosModal from "../EditFacturaPage/EditImpuestosModal.tsx";
import {TipoIdentidad} from "../tarifario.ts";
import FormasDePago from "./FormasDePago.tsx";
import FacturaSummary from "./FacturaSummary.tsx";
import {EmpresaType} from "../../services/auth/types.ts";

const ColDefinition: {
  label: string;
  align: "right" | "left" | "center" | "inherit" | "justify" | undefined;
  style: object }[] = [
  { label: '', align: 'right', style: {width: '10px'} },
  { label: 'Descripción', align: 'left', style: {minWidth: '300px', flexGrow: 1, fontWeight: 700} },
  { label: 'Cantidad', align: 'right', style: {width: '120px'} },
  { label: 'Precio', align: 'right', style: {width: '180px'} },
  { label: 'Dscto.', align: 'right', style: {width: '200px'} },
  { label: 'Subtotal', align: 'right', style: {width: '180px'} },
  { label: 'IVA', align: 'left', style: {width: '1px'} },
  { label: '', align: 'center', style: {width: '1px'} },
];

interface DetallesFormProps {
  empresa: EmpresaType,
  isReadMode: boolean;
  setClienteDialogOpen: React.Dispatch<React.SetStateAction<boolean>>;
  onSubmit: React.FormEventHandler<HTMLFormElement>;
}

const DetallesForm: React.FC<DetallesFormProps> = ({
    empresa,
    isReadMode,
    setClienteDialogOpen,
    onSubmit,
}) => {
  const theme = useTheme();
  const navigate = useNavigate();
  const {
    register,
    formState: { errors },
    watch,
  } = useFormContext<FacturaType>();
  const {
    onKeyEnter,
    selected,
    handleSelected,
    currentLinea,
    fields,
    appendNew,
    editRow,
    removeSelected,
    updateImpuestos,
    getCurrentRow
  } = useDetallesForm();
  const formRef = useRef<HTMLFormElement>(null);
  const [isEditImpuesto, setEditImpuesto] = useState(false);
  const detalles = watch('detalles');

  const buildTextField = (name: keyof FacturaType, textFieldProps?: TextFieldProps) => {
    const label = getFacturaLabel(name);
    return <TextField
              id={`id-${name}`}
              label={label}
              error={!isEmpty(errors[name])}
              helperText={errors[name]?.message as string}
              disabled={isReadMode}
              inputProps={isReadMode ? {readOnly: true} : {}}
              InputLabelProps={{disableAnimation: true, shrink: true}}
              fullWidth
              {...textFieldProps}
              {...register(name)}
          />;
  }

  const buildDetalleField = (index: number, name: keyof DetalleFacturaType, textFieldProps?: TextFieldProps) => {
    const error = errors?.detalles?.[index];
    return <TextField
            id={`detalles.${index}.${name}`}
            label=""
            margin="normal"
            fullWidth
            error={!isEmpty(error?.[name])}
            helperText={error?.[name]?.message as string}
            disabled={isReadMode}
            inputProps={isReadMode ? {readOnly: true} : {}}
            InputLabelProps={{disableAnimation: true, shrink: true}}
            {...textFieldProps}
            {...register(`detalles.${index}.${name}`)}
          />;
  }

  return (
    <Box
      ref={formRef}
      component="form"
      noValidate
      autoComplete="off"
      onSubmit={onSubmit}
      sx={{ width: '100%', backgroundColor: 'white' }}
      onKeyDown={onKeyEnter}
      data-test-id="factura-form">

      <Paper variant="outlined">
        <Grid container spacing={2} sx={{ p: 4 }}>

          <GridItem sm={1} sx={{textAlign: 'right'}}>
            <Tooltip title="Buscar Cliente"><span style={{backgroundColor: theme.palette.grey[200]}}>
                <IconButton disabled={isReadMode} onClick={() => setClienteDialogOpen(true)}><SearchIcon fontSize={"large"} /></IconButton>
            </span></Tooltip>
          </GridItem>

          <GridItem sm={2}>{buildTextField('tipoIdentificacionComprador', {
            select: true, SelectProps: { native: true },
            children: TipoIdentidad.map((option) => (
              <option key={option.value} value={option.value}>{option.label}</option>
            ))
          })}</GridItem>
          <GridItem sm={3}>{buildTextField('identificacionComprador')}</GridItem>
          <GridItem sm={6}>{buildTextField('razonSocialComprador')}</GridItem>

          <GridItem sm={12} data-test-id="factura-detalles">
            <DetalleToolbar isReadMode={isReadMode} error={errors?.detalles?.root?.message} numSelected={selected.length} appendNew={appendNew} removeSelected={removeSelected} />

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
                    const isEditMode = currentLinea === item.linea;
                    const isItemSelected = selected.indexOf(item.linea) !== -1;
                    const labelId = `detalle.${index}.descripcion`;
                    const detalle = detalles?.[index];
                    const error = errors?.detalles?.[index];
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
                          {!isReadMode &&
                            <Checkbox color="primary" checked={isItemSelected} inputProps={{'aria-labelledby': labelId}} />}
                        </DetailCell>
                        <DetailCell align="left" id={labelId}>
                          {isEditMode && buildDetalleField(index, 'descripcion')}
                          {!isEditMode && <>
                            {detalle.descripcion}
                            <FormHelperText error>{error?.descripcion?.message}</FormHelperText>
                          </>}
                        </DetailCell>
                        <DetailCell align="right">
                            {isEditMode && buildDetalleField(index, 'cantidad', {type:"number"})}
                            {!isEditMode && <>
                              {formatAmount(detalle.cantidad)}
                              <FormHelperText error>{error?.cantidad?.message}</FormHelperText>
                            </>}
                          </DetailCell>
                          <DetailCell align="right">
                            {isEditMode && buildDetalleField(index, 'precioUnitario', {type:"number"})}
                            {!isEditMode && detalle.precioUnitario && <>
                              {formatCurrency(detalle.precioUnitario)}
                              <FormHelperText error>{error?.precioUnitario?.message}</FormHelperText>
                            </>}
                          </DetailCell>
                          <DetailCell align="right">
                            {isEditMode && buildDetalleField(index, 'descuento', {type:"number"})}
                            {!isEditMode && detalle.descuento && <>
                              {formatAmount(detalle.descuento)}
                              <FormHelperText error>{error?.descuento?.message}</FormHelperText>
                            </>}
                          </DetailCell>
                          <DetailCell align="right">
                            {item.precioTotalSinImpuesto && formatCurrency(item.precioTotalSinImpuesto)}
                            {<FormHelperText error>{error?.precioTotalSinImpuesto?.message}</FormHelperText>}
                          </DetailCell>
                          <DetailCell align="center">
                            <Checkbox inputProps={{ 'aria-label': 'IVA' }} defaultChecked />
                          </DetailCell>
                          <DetailToolCell align="right">
                            {!isReadMode &&
                              <IconButton disabled={isEditMode} color="primary" onClick={() => editRow(index)}><EditIcon /></IconButton>}
                          </DetailToolCell>
                       </TableRow>
                    );
                  })
                }
                </TableBody>

              </DetailTable>
            </TableContainer>

          </GridItem>

          <GridItem sm={12}>
            <FacturaSummary tarifaIva={empresa.tarifaIva} summary={[]} />
          </GridItem>

          <GridItem sm={10}><FormasDePago isReadMode={isReadMode} /></GridItem>
          <GridItem sm={2}></GridItem>

          <GridItem sm={12}>{buildTextField('observacion', {multiline: true, rows: 4})}</GridItem>

          <GridItem>
            <Grid container direction="row" justifyContent="space-between" alignItems="center">
            <Button onClick={() => navigate(-1)}>Cancel</Button>
            <Button variant="contained"
                type="submit"
                //onClick={() => { formRef?.current?.requestSubmit(); }}
                disabled={isReadMode}
                //{isLoading || isSaving || !isEmpty(errorLoading)}
                //startIcon={isSaving && <CircularProgress size={16}/>}
            >Submit</Button>
            </Grid>
          </GridItem>

        </Grid>
      </Paper>

      <EditImpuestosModal
        impuesto={getCurrentRow()?.iva}
        onUpdate={updateImpuestos}
        isVisible={isEditImpuesto}
        setVisible={setEditImpuesto}
      />

    </Box>
  );
}

export default DetallesForm;
