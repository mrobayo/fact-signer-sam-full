import * as Yup from "yup";
import {ObjectSchema} from "yup";
import { yupLocaleEs } from '../../util';
import {capitalize} from "lodash";
import {type FacturaType} from "../../services";
Yup.setLocale(yupLocaleEs);

export const facturaSchema = Yup.object().shape({
  id: Yup.string(),
  empresaId: Yup.string(),
  puntoVentaId: Yup.string(),
  name: Yup.string().nullable(),
  secuencia: Yup.number().nullable(),
  estadoDoc: Yup.string().nullable(),
  aprobado: Yup.boolean().nullable(),
  aprobadoPorId: Yup.string().nullable(),
  loteId: Yup.string().nullable(),
  claveAcceso: Yup.string().nullable(),
  tipoDoc: Yup.string().nullable(),
  fechaEmision: Yup.string().nullable(),
  fechaHora: Yup.string().nullable(),
  moneda: Yup.string().nullable(),
  autorizacion: Yup.string().nullable(),
  mensajeSri: Yup.string().nullable(),
  emailEnviado: Yup.boolean().nullable(),
  sujetoEmail: Yup.string().nullable(),
  tipoFactura: Yup.string().nullable(),
  totalDescuento: Yup.number().nullable(),
  propina: Yup.number().nullable(),
  totalSinImpuestos: Yup.number().nullable(),
  importeTotal: Yup.number().nullable(),
  valorRetIva: Yup.number().nullable(),
  valorRetRenta: Yup.number().nullable(),
  guiaRemision: Yup.string().nullable(),
  clienteId: Yup.string().nullable(),
  detalles: Yup.array().ensure(),
  observacion: Yup.string().nullable(),
}) as ObjectSchema<FacturaType>;

export const getFacturaLabel = (field: string): string => {
  const labels = {
    clienteId: 'Cliente',
    clienteName: 'Paciente',
    tipoIdentificacionComprador: 'Tipo',
    razonSocialComprador: 'Razon Social',
    identificacionComprador: 'Identificación',
    direccionComprador: 'Dirección'
  } as Record<string, string>;
  if (field in labels) {
    return labels[field];
  }
  return capitalize(field);
};
