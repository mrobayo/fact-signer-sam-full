import {ImpuestoType as Impuesto2Type} from '../../services';
import {PagoType as Pago2Type } from '../../services';
export type DetAdicionalType = {
  nombre: string,
  valor: string
};

export type ImpuestoType = Impuesto2Type;

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

export type TotalImpuestoType = {
  codigo?: number,
  codigoPorcentaje?: number,
  baseImponible: number,
  descuentoAdicional?: number,
  valor: number
};

export type PagoType = Pago2Type;

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

export type FacturaType = {
  version: string,
  infoTributaria: InfoTributariaType,
  infoFactura: InfoFacturaType,
  detalles: DetalleFacturaType[],
  infoAdicional: CampoAdicional[]
};
