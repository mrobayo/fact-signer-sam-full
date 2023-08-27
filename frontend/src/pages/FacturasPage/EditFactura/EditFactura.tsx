import React, {useRef} from 'react';
import {useFieldArray, useForm} from "react-hook-form";
import { type FacturaType} from "../factura";

import Box from '@mui/material/Box';
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import Paper from '@mui/material/Paper';

import TableRow from "@mui/material/TableRow";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableHead from "@mui/material/TableHead";
import Checkbox from "@mui/material/Checkbox";
import TableContainer from "@mui/material/TableContainer";
import AddBoxIcon from '@mui/icons-material/AddBox';

import {DetailCell, DetailTable, editFacturaCss} from "./EditFactura.style.tsx";
import InfoFacturaEdit from "../../../components/InfoFacturaEdit.tsx";
import TextField from "@mui/material/TextField";
import DetalleToolbar from "./DetalleToolbar.tsx";
import {TableFooter} from "@mui/material";
import InformationIcon from "../../../components/icons/InformationIcon.tsx";
import Tooltip from "@mui/material/Tooltip";

const ColDefinition: {
  label: string;
  align: "right" | "left" | "center" | "inherit" | "justify" | undefined;
  style: object
}[] = [
  { label: '', align: 'right', style: {width: '10px'} },
  { label: 'Descripcion', align: 'left', style: { } },
  { label: 'Cantidad', align: 'left', style: {width: '120px'} },
  { label: 'Precio', align: 'left', style: {width: '180px'} },
  { label: 'Subtotal', align: 'left', style: {width: '180px'} },
  //{ label: 'Descuento', align: 'left', style: {width: '200px'} },
  { label: '', align: 'left', style: {width: '1px'} },
];


// https://codesandbox.io/s/react-hook-form-usefieldarray-ssugn?file=/src/index.tsx
const EditFactura: React.FC<{
  isNew: boolean;
}> = ({ isNew }) => {
  const [selected, setSelected] = React.useState<readonly number[]>([]);
  const {
    control,
    getValues,
    register,
    //reset,
    handleSubmit,
    formState: { errors }
  } = useForm<FacturaType>();
  const {
    fields,
    append,
    remove,
  } = useFieldArray({
    control,
    name: "detalles"
  });
  const formRef = useRef<HTMLFormElement>(null);

  const handleAppendRow = () => {
    const detalleId = 1 + getValues("detalles").reduce(
                (acc, { detalleId }) => detalleId && detalleId > acc ? detalleId: acc, 0);
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
    console.log(selected);
    console.log('handleRemove', indexList);
    setSelected([]);
    remove(indexList);
  }

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
        >
          <InfoFacturaEdit register={register} errors={errors} />

          <DetalleToolbar numSelected={selected.length} appendNew={handleAppendRow} removeSelected={handleRemove} />

          {/*<Typography component="h1" variant="h5">Detalle</Typography>*/}
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
                        <TextField
                          margin="normal"
                          fullWidth
                          id={`detalles.${index}.descripcion`}
                          label=""
                          {...register(`detalles.${index}.descripcion`, { required: true })}
                        />
                    </DetailCell>
                      <DetailCell align="right">
                        <TextField
                          margin="normal"
                          fullWidth
                          id={`detalles.${index}.cantidad`}
                          label=""
                          type="number"
                          {...register(`detalles.${index}.cantidad`, { required: true })}
                        />
                      </DetailCell>
                      <DetailCell align="right">
                        <TextField
                          margin="normal"
                          fullWidth
                          id={`detalles.${index}.precioUnitario`}
                          label=""
                          type="number"
                          {...register(`detalles.${index}.precioUnitario`, { required: true })}
                        />
                      </DetailCell>
                      <DetailCell align="right">
                        <TextField
                          margin="normal"
                          fullWidth
                          id={`detalles.${index}.precioTotalSinImpuesto`}
                          label=""
                          type="number"
                          {...register(`detalles.${index}.precioTotalSinImpuesto`, { required: true })}
                        />
                      </DetailCell>
                      <DetailCell align="center">
                        <Tooltip title="Impuestos: IVA">
                          <InformationIcon sx={{ fontSize: 12 }} />
                        </Tooltip>
                      </DetailCell>
                   </TableRow>
                );
              })
            }
              </TableBody>
              <TableFooter>
                <TableRow>
                  <TableCell>*** summary ***</TableCell>
                </TableRow>
              </TableFooter>
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
