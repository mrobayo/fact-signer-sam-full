import React, {useEffect, useRef, useState} from 'react';
import {Controller, useFieldArray, useForm} from "react-hook-form";
import { isEmpty } from "lodash";
import {type FacturaType} from "../factura";

import Box from '@mui/material/Box';
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import IconButton from '@mui/material/IconButton';
import Paper from '@mui/material/Paper';
import FormHelperText from '@mui/material/FormHelperText';

import TableRow from "@mui/material/TableRow";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableHead from "@mui/material/TableHead";
import Checkbox from "@mui/material/Checkbox";
import TableContainer from "@mui/material/TableContainer";
import AddBoxIcon from '@mui/icons-material/AddBox';
import EditIcon from '@mui/icons-material/Edit';

import {DetailCell, DetailTable, editFacturaCss} from "./EditFactura.style.tsx";
import InfoFacturaEdit from "../../../components/InfoFacturaEdit.tsx";
import TextField from "@mui/material/TextField";
import DetalleToolbar from "./DetalleToolbar.tsx";

import InformationIcon from "../../../components/icons/InformationIcon.tsx";
import Tooltip from "@mui/material/Tooltip";
import TableFacturaSummary from "./TableFacturaSummary.tsx";

const ColDefinition: {
  label: string;
  align: "right" | "left" | "center" | "inherit" | "justify" | undefined;
  style: object
}[] = [
  { label: '', align: 'right', style: {width: '10px'} },
  { label: 'Descripcion', align: 'left', style: { } },
  { label: 'Cantidad', align: 'right', style: {width: '120px'} },
  { label: 'Precio', align: 'right', style: {width: '180px'} },
  { label: 'Subtotal', align: 'right', style: {width: '180px'} },
  //{ label: 'Descuento', align: 'left', style: {width: '200px'} },
  { label: '', align: 'left', style: {width: '1px'} },
  { label: '', align: 'center', style: {width: '1px'} },
];


// https://codesandbox.io/s/react-hook-form-usefieldarray-ssugn?file=/src/index.tsx
const EditFactura: React.FC<{
  isNew: boolean;
}> = ({ isNew }) => {
   //const [_ignore, forceUpdate] = React.useReducer(x => x + 1, 0);
  const [selected, setSelected] = React.useState<readonly number[]>([]);
  const [editRow, setEditRow] = useState<number>(0);
  const {
    control,
    getValues,
    setValue,
    register,
    watch,
    clearErrors,
    setError,
    //reset,
    trigger,
    handleSubmit,
    formState: { errors }
  } = useForm<FacturaType>({
    defaultValues: {
      infoFactura: {

      },
      infoTributaria: {

      },
      detalles: [{
        detalleId: 1,
        codigoPrincipal: '',
        codigoAuxiliar: '',
        descripcion: '',
        cantidad: 1,
        precioUnitario: 0,
        descuento: 0,
        precioTotalSinImpuesto: 0,
        detallesAdicionales: [],
        impuestos: []
      }]
    }
  });
  const {
    fields,
    append,
    remove,
  } = useFieldArray({
    control,
    name: "detalles"
  });
  const formRef = useRef<HTMLFormElement>(null);
  const watchDetalle = watch("detalles");

  useEffect(() => {
    // watchDetalle((value, { name, type }) => console.log(value, name, type));
    console.log('watchDetalle', watchDetalle);
  }, [watchDetalle]);


  const handleAppendRow = () => {
    const detalles = getValues("detalles");
    const detalleId = 1 + detalles.reduce(
                (acc, { detalleId }) => detalleId && detalleId > acc ? detalleId: acc, 0);
    setEditRow(detalles.length);
    append({
      detalleId,
      codigoPrincipal: "",
      descripcion: "",
      cantidad: 1,
      precioUnitario: 0,
      descuento: 0,
      precioTotalSinImpuesto: 0,
      impuestos: []
    });
  }

  const onFormSubmit = handleSubmit((data) => {
    console.log('submit...', data)
  })

  const handleSelected = (detalleId: number) => {
    const selectedIndex = selected.indexOf(detalleId);
    let newSelected: readonly number[] = [];
    if (selectedIndex === -1) {
      newSelected = newSelected.concat(selected, detalleId);
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
      .map(({ detalleId }, index) => selected.includes(detalleId) ? index : null)
      .filter(index => index !== null) as number[];

    setSelected([]);
    remove(indexList);
  }

  const computePrecioSinImpuestos = () => {
    const detalles = getValues("detalles");
    const detallesLen = detalles.length;
    if (editRow >= detallesLen) {
      return;
    }

    const detalle = detalles[editRow];
    const valor = (+detalle.precioUnitario) * (+detalle.cantidad);
    setValue(`detalles.${editRow}.precioTotalSinImpuesto`, +valor.toFixed(2),
      //{shouldDirty: true, shouldTouch: true, shouldValidate: true}
    );

    let isRowValid = true;
    // VALIDATE ROW
    if (valor === 0) {
      isRowValid = false;
      setError(`detalles.${editRow}.precioTotalSinImpuesto`,
        { type: 'manual', message:'Subtotal Sin Impuesto debe ser mayor a cero' }
      );
    }
    if (isEmpty(detalle.descripcion)) {
      isRowValid = false;
      trigger(`detalles.${editRow}.descripcion`);
    }
    if (isRowValid) {
      clearErrors(`detalles.${editRow}.precioTotalSinImpuesto`);
      if (!detalles[detallesLen-1].descripcion) {
        remove(detallesLen-1);
      }
      handleAppendRow();
    }
  };

  const checkKeyDown = (event: React.KeyboardEvent<HTMLFormElement>) => {
    if (event.key !== "Enter") {
      return;
    }
    event.preventDefault();
    const target= event.target as HTMLFormElement;
    if (target.id.endsWith('precioUnitario')) {
      computePrecioSinImpuestos();
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

  return (
    <Paper variant="outlined" sx={{ my: { xs: 3, md: 6 }, p: { xs: 2, md: 3 } }}>
      <Typography component="h1" variant="h4" >
        {isNew && 'Nueva'} Factura
      </Typography>
      <Box sx={editFacturaCss}>
        <Box
          ref={formRef}
          component="form"
          onSubmit={onFormSubmit}
          noValidate
          sx={{ mt: 1 }}
          data-test-id="factura-form"
          onKeyDown={(e) => checkKeyDown(e)}
        >
          <InfoFacturaEdit register={register} errors={errors} />
          <DetalleToolbar numSelected={selected.length} appendNew={handleAppendRow} removeSelected={handleRemove} />

          <TableContainer sx={{ maxHeight: 440 }}>
            <DetailTable
              // size="medium"
              sx={{ minWidth: 650 }}
              stickyHeader
              aria-label="Factura Detalle">
              <TableHead>
                <TableRow>
                  {ColDefinition.map((col, index) =>
                    <TableCell key={`det-head-${index}`} align={col.align} style={col.style}>{col.label}</TableCell>
                  )}
                </TableRow>
              </TableHead>
              <TableBody>

            {fields.map((item, index) => {
                const isEditMode = editRow === index;
                const isItemSelected = selected.indexOf(item.detalleId) !== -1;
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
                    <DetailCell
                      align="right"
                      onClick={() => handleSelected(item.detalleId)}
                      sx={{ cursor: "pointer" }}
                    >
                      <Checkbox
                        color="primary"
                        checked={isItemSelected}
                        inputProps={{
                          'aria-labelledby': labelId,
                        }}
                      />
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
                        {!isEditMode && item.cantidad}
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
                        {!isEditMode && item.precioUnitario}
                      </DetailCell>
                      <DetailCell align="right">
                        {item.precioTotalSinImpuesto}
                        {errors?.detalles?.[index]?.precioTotalSinImpuesto && <FormHelperText error>{errors?.detalles?.[index]?.precioTotalSinImpuesto?.message}</FormHelperText>}
                      </DetailCell>
                      <DetailCell align="center">
                        <Tooltip title="Impuestos: IVA">
                          <InformationIcon sx={{ fontSize: 12 }} />
                        </Tooltip>
                      </DetailCell>
                      <DetailCell align="center">
                        <IconButton
                          disabled={isEditMode}
                          color="primary"
                          onClick={() => setEditRow(index)}>
                          <EditIcon />
                        </IconButton>
                      </DetailCell>
                   </TableRow>
                );
              })
            }
              </TableBody>
              <TableFacturaSummary detalles={getValues("detalles")} />
            </DetailTable>
          </TableContainer>

          <Button
            type="button"
            onClick={handleAppendRow}
            startIcon={<AddBoxIcon />}
          >
            Añadir
          </Button>

          <Button
            type="submit"
            fullWidth
            variant="contained"
            sx={{ mt: 3, mb: 2, width: 'inherit', alignSelf: 'right' }}
          >
            Guardar
          </Button>

        </Box>
      </Box>
    </Paper>
  );
};

export default EditFactura;
