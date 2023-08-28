import {DetalleFacturaType, ImpuestoType, TotalImpuestoType} from "./factura";

export type FacturaSummaryType = {
  totalSinImpuestos:  number,
  totalDescuento: number,
  totalConImpuestos: TotalImpuestoType[],
  propina?: number,
  importeTotal: number
};

export const computeFacturaSummary = (detalles: DetalleFacturaType[]): FacturaSummaryType => {
  const summary: FacturaSummaryType = {
    totalSinImpuestos: 0,
    totalDescuento: 0,
    totalConImpuestos: [],
    propina: 0,
    importeTotal: 0
  };
  // Calcular subtotal y descuento
  detalles.forEach((detalle) => {
    summary.totalSinImpuestos += +detalle.precioTotalSinImpuesto ?? 0;
    summary.totalDescuento += +detalle.descuento ?? 0;
  });

  //
  const impuestosMap = detalles
    .flatMap(detalle => detalle?.impuestos ?? [])
    .reduce((acc, impuesto) => {
      const grupo = `impuesto${impuesto.codigo}-porcentaje${impuesto.codigoPorcentaje}`;
      if (acc.has(grupo)) acc.get(grupo)?.push(impuesto); else acc.set(grupo, [impuesto]);
      return acc;
  }, new Map<string, ImpuestoType[]>);

  impuestosMap.forEach((impuestos) => {
    const totalImpuesto = impuestos.reduce((acc, impuesto) => {
      acc.codigo = impuesto.codigo;
      acc.codigoPorcentaje = impuesto.codigoPorcentaje;
      acc.valor += impuesto.valor;
      acc.baseImponible +=  impuesto.baseImponible;
      return acc;
    }, {
      baseImponible: 0,
      valor: 0
    } as TotalImpuestoType);
    summary.totalConImpuestos.push(totalImpuesto);
  });

  //console.log(detalles, summary);
  return summary;
}