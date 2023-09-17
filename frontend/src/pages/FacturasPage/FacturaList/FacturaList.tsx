import React, {useEffect} from 'react';
import Paper from '@mui/material/Paper';
import TableContainer from '@mui/material/TableContainer';
import Table from '@mui/material/Table';
import TableHead from "@mui/material/TableHead";
import TableRow from '@mui/material/TableRow';
import TableCell from "@mui/material/TableCell";
import TableBody from '@mui/material/TableBody';

const ColDefinition: {
  label: string;
  align: "right" | "left" | "center" | "inherit" | "justify" | undefined;
  style: object
}[] = [
  { label: '', align: 'right', style: {width: '10px'} },
  { label: 'Emisión', align: 'center', style: {width: '120px'} },
  { label: 'Numero', align: 'center', style: {width: '120px'} },
  { label: 'Identificación', align: 'left', style: {width: '120px'} },
  { label: 'Nombre Comprador', align: 'left', style: {} },
  { label: 'Estado', align: 'center', style: {width: '180px'} },
  { label: '', align: 'left', style: {width: '1px'} },
  { label: '', align: 'center', style: {width: '1px', border: 'none'} },
];

const FacturaList: React.FC = () => {
  // const service = facturaService;
  // const empresaId = '1100000000001';

  const getAll = async () => {
    // const test = await service.get(`facturas/${empresaId}`);
    // console.log(test);
  }
  useEffect(() => {
    getAll();
  }, []);

  return (
    <Paper variant="outlined" sx={{ my: { xs: 3, md: 6 }, p: { xs: 2, md: 3 } }}>
        <TableContainer sx={{ maxHeight: 440 }}>
          <Table
            sx={{ minWidth: 650 }}
            stickyHeader
            aria-label="Factura Detalle"
          >
            <TableHead>
              <TableRow>
                {ColDefinition.map((col, index) =>
                  <TableCell key={`det-head-${index}`} align={col.align} style={col.style}>{col.label}</TableCell>
                )}
              </TableRow>
            </TableHead>
            <TableBody>

            </TableBody>
          </Table>
        </TableContainer>
    </Paper>
  );
};

export default FacturaList;
