import React, {useState} from 'react';
import Box from '@mui/material/Box';
import Collapse from '@mui/material/Collapse';
import IconButton from '@mui/material/IconButton';
import EditIcon from '@mui/icons-material/Edit';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Typography from '@mui/material/Typography';
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown';
import KeyboardArrowUpIcon from '@mui/icons-material/KeyboardArrowUp';
import {PagoType} from "../FacturasPage/factura";
import {FORMAS_PAGO} from "../tarifario.ts";
import { isEmpty } from 'lodash';
import FormaPagoModalModal from "./FormaPagoModal.tsx";

// const examplePagos = [
//   {formaPago: '01', total: 50, plazo: 0, unidadTiempo: 'días'},
//   {formaPago: '16', total: 100, plazo: 30, unidadTiempo: 'días'},
// ];

const FormasDePago: React.FC<{
  pagos: PagoType[];
  onUpdatePagos?: PagoType[];
}> = ({ pagos }) => {
  const [openDetalle, setOpenDetalle] = useState(true);
  const [isEditFormas, setEditFormas] = useState(false);
  const [pagoIndex, setPagoIndex] = useState<number>(-1);
  const getFormaPago = (formaPago: string) => {
    return FORMAS_PAGO.find(forma => forma.codigo === formaPago)?.label;
  }
  return (
    <Box data-test-id="FormasPago" sx={{ width: '70%', marginTop: 3, backgroundColor: '#eee' }}>

      <Table size="small" aria-label="Formas de Pago">
        <TableHead>
          <TableRow>
            <TableCell>
              <Typography sx={{ flex: '1 1 100%' }} variant="h6" id="FormaPagoTitle" component="div">
                <IconButton aria-label="expand row" size="small" onClick={() => setOpenDetalle(!openDetalle)}>
                  {openDetalle ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
                </IconButton>
                Formas de Pago
              </Typography>
            </TableCell>
            <TableCell align="right">Valor</TableCell>
            <TableCell align="right">Plazo</TableCell>
            <TableCell align="right">
              <IconButton
                aria-label="Edit formas de pago"
                color="info"
                onClick={() => {
                  setPagoIndex(-1);
                  setEditFormas(true);
                }}>
                    <EditIcon />
                </IconButton>
            </TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          <TableRow>
            <TableCell style={{ paddingBottom: 0, paddingTop: 0 }} colSpan={4}>
              <Collapse in={openDetalle} timeout="auto" unmountOnExit>
                <Table size="small" aria-label="purchases">
                {isEmpty(pagos) && (
                  <TableRow>
                    <TableCell align="center">(Ingrese al menos 1 forma de pago)</TableCell>
                  </TableRow>
                )}
                {!isEmpty(pagos) && pagos?.map((row) => (
                <TableRow key={row.formaPago}>
                  <TableCell component="th" scope="row">{getFormaPago(row.formaPago)}</TableCell>
                  <TableCell>{row.total}</TableCell>
                  <TableCell align="right">{row.plazo}</TableCell>
                  <TableCell align="right">{row.unidadTiempo}</TableCell>
                </TableRow>
              ))}
                </Table>
              </Collapse>
            </TableCell>
          </TableRow>
        </TableBody>
      </Table>
      <FormaPagoModalModal
        pago={pagoIndex >= 0 && pagos.length > pagoIndex ? pagos[pagoIndex] : {} as PagoType}
        onUpdate={(data) => { console.log(data); }}
        isVisible={isEditFormas}
        setVisible={setEditFormas}
      />
    </Box>
  );
};

export default FormasDePago;
