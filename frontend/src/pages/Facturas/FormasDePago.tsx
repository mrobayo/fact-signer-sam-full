import React from 'react';

import Box from "@mui/material/Box";
import IconButton from '@mui/material/IconButton';
import DeleteIcon from '@mui/icons-material/Delete';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Tooltip from "@mui/material/Tooltip";
import AddBoxIcon from "@mui/icons-material/AddBox";
import TextField, {TextFieldProps} from "@mui/material/TextField";
import {useTheme} from "@mui/material";

import {FacturaType, PagoType} from "../../services";
import {FORMAS_PAGO} from "../tarifario";
import { isEmpty } from 'lodash';
import useFormasDePago from "./useFormasDePago";
import {useFormContext} from "react-hook-form";
import {formatAmount} from "../../util";

const FormasDePago: React.FC<{
  isReadMode: boolean;
}> = ({ isReadMode }) => {
  const theme = useTheme();
  const {
    getValues,
    register,
    formState: { errors },
  } = useFormContext<FacturaType>();
  const { fields, appendNew, removeRow } = useFormasDePago();

  const getFormaPago = (formaPago: string) => {
    return FORMAS_PAGO.find(forma => forma.codigo === formaPago)?.label;
  }

  const getFormasPago = () => {
    const pagos = getValues('pagos');
    return FORMAS_PAGO
      .map( option =>
        ({...option, disabled: pagos.some(pago => pago.formaPago === option.codigo) })
      );
  }

  const buildTextField = (index: number, name: keyof PagoType, textFieldProps?: TextFieldProps) => {
    const error = errors?.pagos?.[index];
    return <TextField
              id={`pagos.${index}.${name}`}
              label=""
              error={!isEmpty(error?.[name])}
              helperText={error?.[name]?.message as string}
              disabled={isReadMode}
              inputProps={isReadMode ? {readOnly: true} : {}}
              InputLabelProps={{disableAnimation: true, shrink: true}}
              fullWidth
              {...textFieldProps}
              {...register(`pagos.${index}.${name}`)}
          />;
  }

  return (
      <Table size="small" aria-label="Formas de Pago" sx={{ backgroundColor: theme.palette.grey[100], '& td': { p: 1 } }}>
        <TableHead>
          <TableRow>
            <TableCell><b>Formas de Pago</b></TableCell>
            <TableCell align="right">Valor</TableCell>
            <TableCell align="right">Plazo</TableCell>
            <TableCell align="center">Unidad</TableCell>
            <TableCell align="right">
              {!isReadMode && <Tooltip title="Añadir"><IconButton color="info" aria-label="Añadir" onClick={appendNew}><AddBoxIcon /></IconButton></Tooltip>}
            </TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {fields.map((item, index) => {
            const isEditMode = true; // currentRow === index;
            return (
              <TableRow key={`FormaPago-${index}`}>
                <TableCell>
                  {isEditMode && buildTextField(index, 'formaPago', {
                    select: true, SelectProps: { native: true },
                    children: getFormasPago()
                      .map(option => (
                      <option
                        key={option.codigo}
                        disabled={option.disabled}
                        value={option.codigo}>{option.label}</option>
                    ))
                  })}
                  {!isEditMode && getFormaPago(item.formaPago)}</TableCell>
                <TableCell align="right">
                  {isEditMode && buildTextField(index, 'total', { type: 'number'})}
                  {!isEditMode && formatAmount(item.total)}</TableCell>
                <TableCell align="right">
                  {isEditMode && buildTextField(index, 'plazo', { type: 'number'})}
                  {!isEditMode && item.plazo}</TableCell>
                <TableCell align="center">
                  {isEditMode && buildTextField(index, 'unidadTiempo')}
                  {!isEditMode && item.unidadTiempo}</TableCell>
                <TableCell>{!isReadMode && (
                  <Box sx={{ display: 'flex' }}>
                    {/*<IconButton aria-label="Edit" color="info" onClick={() => editRow(index)}><EditIcon fontSize="small" /></IconButton>*/}
                    <IconButton aria-label="Remove" color="info" onClick={() => removeRow(index)}><DeleteIcon fontSize="small" /></IconButton>
                  </Box>
                )}</TableCell>
              </TableRow>
            );
          })}
        </TableBody>
      </Table>
  );
};

export default FormasDePago;
