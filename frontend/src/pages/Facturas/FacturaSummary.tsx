import TableRow from "@mui/material/TableRow";
import TableFooter from "@mui/material/TableFooter";
import TableCell from "@mui/material/TableCell";
import {formatAmount} from "../../util";
import {DetailTable} from "../EditFacturaPage/EditFactura.style.tsx";
import TableHead from "@mui/material/TableHead";

export type SummaryType = {
  subTotal?: number|null,
  subTotalIVA?: number|null,
  subTotalCero?: number|null,
  subTotalNoObjeto?: number|null,
  subTotalExento?: number|null,
  subTotalSinImpuestos?: number|null,
  totalDescuento?: number|null,
  valorICE?: number|null,
  valorIVA?: number|null,
  tarifaEspecial?: number|null,
  valorIRBPNR?: number|null,
  propina?: number|null,
  valorTOTAL?: number|null
}

//export type SummaryType = typeof Summary;
export type SummaryKey = keyof SummaryType;

type SummaryEntry = { code: SummaryKey, label: string, order: number };

const SummaryLabels = [
  { code: "", label: '', order: 1},
  { code: "", label: '', order: 1},
  { code: "subTotalIVA", label: 'Subtotal {IVA}%', order: 1},
  { code: "subTotalCero", label: 'Subtotal 0%', order: 1},
  { code: "subTotalNoObjeto", label: 'Subtotal No objeto de IVA', order: 1},
  { code: "subTotalExento", label: 'Subtotal Exento de IVA', order: 1},
  { code: "subTotal", label: 'SUBTOTAL', order: 2},
  { code: "totalDescuento", label: 'Total Descuento', order: 2},
  { code: "subTotalSinImpuestos", label: 'Subtotal sin impuestos', order: 2},
  { code: "valorICE", label: 'ICE', order: 2},
  { code: "valorIVA", label: 'IVA {IVA}%', order: 2},
  //{ code: "tarifaEspecial", label: 'IVA Tarifa Especial', order: 2},
  //{ code: "valorIRBPNR", label: 'Valor IRBPNR', order: 2},
  //{ code: "propina", label: 'Propina 10%', order: 2},
  { code: "valorTOTAL", label: 'VALOR TOTAL', order: 2},
] as SummaryEntry[];

interface FacturaSummaryProps {
  tarifaIva: number,
  summary: SummaryType
}
function FacturaSummary({tarifaIva, summary}: FacturaSummaryProps) {
  const getAmount = (entry: SummaryEntry) => {
    if (summary && entry?.code && entry.code in summary) {
      return formatAmount(summary[entry?.code] ?? 0);
    }
    return (entry?.label) ? <span>&mdash;</span> : '';
  }

  const formatLabel = (entry: SummaryEntry) => {
    return entry?.label?.replace('{IVA}', formatAmount(tarifaIva, 0));
  }
  const groupLeft = SummaryLabels.filter(({order}) => order === 1);
  const groupRight = SummaryLabels.filter(({order}) => order === 2);

  return (
    <DetailTable size="small">
      <TableHead>
        <TableRow>
          <TableCell style={{ fontWeight: 700 }} colSpan={5}>
            Total a Pagar
          </TableCell>
        </TableRow>
      </TableHead>
      <TableFooter sx={{'& td': { }}}>
        {groupRight.map((entry, index) => {
          return (
            <TableRow key={entry.label}>
              <TableCell style={{ minWidth: '100px', flexGrow: 1}}></TableCell>
              <TableCell style={{ width: '200px'}}align="right">{formatLabel(groupLeft?.[index])}</TableCell>
              <TableCell align="right" style={{ width: '140px'}}>{getAmount(groupLeft?.[index])}</TableCell>
              <TableCell style={{ width: '200px', fontWeight: '700' }} align="right">{formatLabel(groupRight?.[index])}</TableCell>
              <TableCell align="right" style={{ width: '140px', fontWeight: '700'}}>{getAmount(groupRight?.[index])}</TableCell>
            </TableRow>
          )
        })}
      </TableFooter>
    </DetailTable>
  );
}

export default FacturaSummary;
