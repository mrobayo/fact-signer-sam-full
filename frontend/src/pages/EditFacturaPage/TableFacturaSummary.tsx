import { useMemo } from 'react';
import TableRow from "@mui/material/TableRow";

import {TableFooter} from "@mui/material";
import React from "react";
import {DetalleFacturaType} from "../FacturasPage/factura";
import {computeFacturaSummary} from "../FacturasPage/facturaUtil.ts";
import {DetailSummaryCell} from "./EditFactura.style.tsx";

const TableFacturaSummary: React.FC<{
  detalles: DetalleFacturaType[]
}> = ({ detalles }) => {

  const summary = useMemo(
    () => computeFacturaSummary(detalles ?? []), [detalles]
  );

  return (
    <TableFooter>
        <TableRow>
          <DetailSummaryCell></DetailSummaryCell>
          <DetailSummaryCell align="center">{detalles?.length} items</DetailSummaryCell>
          <DetailSummaryCell colSpan={2} align="right"> Subtotal </DetailSummaryCell>
          <DetailSummaryCell align="right">$ {summary?.totalSinImpuestos?.toFixed(2)}</DetailSummaryCell>
          <DetailSummaryCell></DetailSummaryCell>
        </TableRow>
        {summary.totalConImpuestos.map(total => (
          <TableRow key={`totalImpuesto.${total.codigo}.${total.codigoPorcentaje}`}>
          <DetailSummaryCell></DetailSummaryCell>
          <DetailSummaryCell></DetailSummaryCell>
          <DetailSummaryCell colSpan={2} align="right">Subtotal {total.codigo}.{total.codigoPorcentaje} </DetailSummaryCell>
          <DetailSummaryCell align="right">$ {total.baseImponible}</DetailSummaryCell>
          <DetailSummaryCell></DetailSummaryCell>
        </TableRow>
        ))}

      <TableRow>
          <DetailSummaryCell></DetailSummaryCell>
          <DetailSummaryCell></DetailSummaryCell>
          <DetailSummaryCell colSpan={2} align="right"> Total Descuento </DetailSummaryCell>
          <DetailSummaryCell align="right">$ {summary.totalDescuento}</DetailSummaryCell>
          <DetailSummaryCell></DetailSummaryCell>
        </TableRow>
        <TableRow>
          <DetailSummaryCell></DetailSummaryCell>
          <DetailSummaryCell></DetailSummaryCell>
          <DetailSummaryCell colSpan={2} align="right"> Total </DetailSummaryCell>
          <DetailSummaryCell align="right">$ {summary.importeTotal?.toFixed(2)}</DetailSummaryCell>
          <DetailSummaryCell></DetailSummaryCell>
        </TableRow>
      </TableFooter>
  );
}

export default TableFacturaSummary;
