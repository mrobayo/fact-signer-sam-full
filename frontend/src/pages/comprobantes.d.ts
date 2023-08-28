
// TABLA 16
const CodigosImpuesto = {
  IVA: 2,
  ICE: 3,
  IRBPNR: 5,
};

// TABLA 17
const TarifasIVA = {
  '0%': 0,
  '12%': 2,
  '14%': 3,
  'No Objeto de Impuesto': 6,
  'Exento de IVA': 7,
  'IVA diferenciado': 8
};

// TABLA 21
const TotalesConImpuesto = [
  { label: 'Subtotal Sin Impuestos', required: true},
  { label: 'Subtotal IVA _%', codigo: 2, codigoPorcentaje: 2, required: true},
  { label: 'Subtotal 0%', codigo: 2, codigoPorcentaje: 0, required: true},
  { label: 'Subtotal No Objeto de IVA', codigo: 2, codigoPorcentaje: 6, required: true},
  { label: 'Subtotal Exento de IVA', codigo: 2, codigoPorcentaje: 7, required: true},
  { label: 'Total Descuento', required: true},
  // { label: 'Valor ICE', codigo: 0, codigoPorcentaje: 0},
  // { label: 'Valor IRBPNR', codigo: 0, codigoPorcentaje: 0},
  { label: 'Valor IVA _%', codigo: 2, codigoPorcentaje: 2, required: true},
  { label: 'Propina', required: true},
  { label: 'Valor Total', required: true},
];
