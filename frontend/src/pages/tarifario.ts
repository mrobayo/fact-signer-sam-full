
// TABLA 16
export const CodigosImpuesto = {
  IVA: 2,
  ICE: 3,
  IRBPNR: 5,
};

// TABLA 17
export const TarifasIVA = {
  '0%': 0,
  '12%': 2,
  '14%': 3,
  'No Objeto de Impuesto': 6,
  'Exento de IVA': 7,
  'IVA diferenciado': 8
};

export const TARIFAS_IVA = [
  {label: '0%', codigoPorcentaje: 0, active: true, tarifa: 0},
  {label: '12%', codigoPorcentaje: 2, active: true, tarifa: 12},
  {label: '14%', codigoPorcentaje: 3, active: false, tarifa: 14},
  {label: 'No Objeto de Impuesto', codigoPorcentaje: 6, active: false, tarifa: 0},
  {label: 'Exento de IVA', codigoPorcentaje: 7, active: false, tarifa: 0},
  {label: 'IVA diferenciado', codigoPorcentaje: 8, active: false}
];

// TABLA 21
export const TotalesConImpuesto = [
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

export const FORMAS_PAGO = [
  { codigo: "01", label: "SIN UTILIZACION DEL SISTEMA FINANCIERO"},
  { codigo: "15", label: "COMPENSACION DE DEUDAS"},
  { codigo: "16", label: "TARJETA DE DEBITO"},
  { codigo: "17", label: "DINERO ELECTRONICO"},
  { codigo: "18", label: "TARJETA PREPAGO"},
  { codigo: "19", label: "TARJETA DE CREDITO"},
  { codigo: "20", label: "OTROS CON UTILIZACION DEL SISTEMA FINANCIERO"},
  { codigo: "21", label: "ENDOSO DE TITULOS"}
];

export const TipoIdentidad = [
  { value: "RUC", label: "RUC", codigo: '04'},
  { value: "CED", label: "CED", codigo: '05'},
  { value: "PAS", label: "PAS", codigo: '06'},
  { value: "FIN", label: "C.FINAL", codigo: '07'},
  { value: "EXT", label: "ID.EXT", codigo: '08'},
];