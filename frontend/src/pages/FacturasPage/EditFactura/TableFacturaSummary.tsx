import { useMemo } from 'react';
import TableRow from "@mui/material/TableRow";
import TableCell from "@mui/material/TableCell";
import {TableFooter} from "@mui/material";
import React from "react";
import {DetalleFacturaType} from "../factura";
import {computeFacturaSummary} from "../facturaUtil.ts";

const TableFacturaSummary: React.FC<{
  detalles: DetalleFacturaType[]
}> = ({ detalles }) => {

  const summary = useMemo(
    () => computeFacturaSummary(detalles ?? []), [detalles]
  );

  return (
    <TableFooter>
        <TableRow>
          <TableCell></TableCell>
          <TableCell align="center">{detalles?.length} items</TableCell>
          <TableCell colSpan={2} align="right"> Subtotal </TableCell>
          <TableCell align="right">$ {summary?.totalSinImpuestos?.toFixed(2)}</TableCell>
          <TableCell></TableCell>
        </TableRow>
        {summary.totalConImpuestos.map(total => (
          <TableRow key={`totalImpuesto.${total.codigo}.${total.codigoPorcentaje}`}>
          <TableCell></TableCell>
          <TableCell></TableCell>
          <TableCell colSpan={2} align="right">Subtotal {total.codigo}.{total.codigoPorcentaje} </TableCell>
          <TableCell align="right">$ {total.baseImponible}</TableCell>
          <TableCell></TableCell>
        </TableRow>
        ))}

      <TableRow>
          <TableCell></TableCell>
          <TableCell></TableCell>
          <TableCell colSpan={2} align="right"> Total Descuento </TableCell>
          <TableCell align="right">$ {summary.totalDescuento}</TableCell>
          <TableCell></TableCell>
        </TableRow>
        <TableRow>
          <TableCell></TableCell>
          <TableCell></TableCell>
          <TableCell colSpan={2} align="right"> Total </TableCell>
          <TableCell align="right">$ {summary.importeTotal?.toFixed(2)}</TableCell>
          <TableCell></TableCell>
        </TableRow>
      </TableFooter>
  );
}

export default TableFacturaSummary;
