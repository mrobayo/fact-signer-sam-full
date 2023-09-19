import TableRow from "@mui/material/TableRow";
import TableFooter from "@mui/material/TableFooter";
import TableCell from "@mui/material/TableCell";
import {formatAmount} from "../../util";
import {DetailTable} from "../EditFacturaPage/EditFactura.style.tsx";
import TableHead from "@mui/material/TableHead";

const SummaryLabels = [
  { label: 'Subtotal sin impuestos', order: 1},
  { label: 'Subtotal {IVA}', order: 1},
  { label: 'Subtotal 0%', order: 1},
  { label: 'Subtotal No objeto de IVA', order: 1},
  { label: 'Subtotal Exento de IVA', order: 1},
  { label: 'Total Descuento', order: 1},
  { label: 'Valor ICE', order: 2},
  { label: 'IVA {IVA}', order: 2},
  { label: 'IVA Tarifa Especial', order: 2},
  { label: 'Valor IRBPNR', order: 2},
  { label: 'Propina 10%', order: 2},
  { label: 'VALOR TOTAL', order: 2},
];

interface FacturaSummaryProps {
  summary: { 'label': string, amount: number }[]
}
function FacturaSummary({summary}: FacturaSummaryProps) {
  const getAmount = (label: string) => {
    const sum = summary.find(item => item.label === label);
    return formatAmount(sum?.amount ?? 0);
  }
  const groupLeft = SummaryLabels.filter(({order}) => order === 1);
  const groupRight = SummaryLabels.filter(({order}) => order === 2);

  return (
    <DetailTable>
      <TableHead>
        <TableRow>
          <TableCell style={{ fontWeight: 700 }} colSpan={5}>
            Total a Pagar
          </TableCell>
        </TableRow>
      </TableHead>
      <TableFooter sx={{'& td': {fontSize: 'large'}}}>
        {groupLeft.map((entry, index) => {
          return (
            <TableRow>
              <TableCell style={{ minWidth: '200px', flexGrow: 1}}></TableCell>
              <TableCell style={{ width: '300px'}}align="right">{entry.label}</TableCell>
              <TableCell align="right" style={{ width: '200px'}}>{getAmount(entry.label)}</TableCell>
              <TableCell style={{ width: '300px'}} align="right">{groupRight?.[index]?.label}</TableCell>
              <TableCell align="right" style={{ width: '200px'}}>{getAmount(groupRight?.[index]?.label)}</TableCell>
            </TableRow>
          )
        })}
      </TableFooter>
    </DetailTable>
  );
}

export default FacturaSummary;
