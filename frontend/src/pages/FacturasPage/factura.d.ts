export type ImpuestoType = {
  codigo: number,
  codigoPorcentaje: number,
  tarifa: number,
  baseImponible: number,
  valor: number,
};

export type DetAdicionalType = {
  nombre: string,
  valor: string
};

export type DetalleFacturaType = {
  detalleId: number,
  codigoPrincipal: string,
  codigoAuxiliar?: string,
  descripcion: string,
  cantidad: number,
  precioUnitario: number,
  descuento: number,
  precioTotalSinImpuesto: number,
  detallesAdicionales?: DetAdicionalType[],
  impuestos: ImpuestoType[]
};

export type InfoTributariaType = {
  ambiente: number,
  tipoEmision: number,
  razonSocial: string,
  nombreComercial: string,
  ruc: string,
  claveAcceso: string,
  codDoc: string,
  estab: string,
  ptoEmi: string,
  secuencial: string,
  dirMatriz: string,
};

export type TotalImpuestoType = {
  codigo: string,
  codigoPorcentaje: string,
  baseImponible: number,
  descuentoAdicional?: number,
  valor: number
};

export type PagoType = {
  formaPago: string,
  total: number,
  plazo: number,
  unidadTiempo: string
};

export type InfoFacturaType = {
  fechaEmision: string,
  dirEstablecimiento: string,
  contribuyenteEspecial: string,
  obligadoContabilidad: string,
  tipoIdentificacionComprador: string,
  guiaRemision?: string,
  razonSocialComprador: string,
  identificacionComprador: string,
  direccionComprador: string,
  totalSinImpuestos:  number,
  totalDescuento: number,
  totalConImpuestos: TotalImpuestoType[],
  propina?: number,
  importeTotal: number,
  moneda: string,
  pagos: PagoType[],
  valorRetIva?: number,
  valorRetRenta?: number,
}

export type CampoAdicional = {
  nombre: string,
  valor: string,
};

export type FacturaType = {
  version: string,
  infoTributaria: InfoTributariaType,
  infoFactura: InfoFacturaType,
  detalles: DetalleFacturaType[],
  infoAdicional: CampoAdicional[]
};
