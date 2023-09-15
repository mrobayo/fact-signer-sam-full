import * as Yup from "yup";
import {ObjectSchema} from "yup";
import { yupLocaleEs } from '../../util';
import {capitalize} from "lodash";
import {type ClienteType} from "../../services";
Yup.setLocale(yupLocaleEs);

export const clienteSchema = Yup.object().shape({
  id: Yup.string(),
  name: Yup.string(),
  activo: Yup.boolean(),
  tipo: Yup.string().required(),
  identidad: Yup.string().required(),
  nombres: Yup.string().required(),
  apellidos: Yup.string().required(),
  birthday: Yup.date()
    .min(new Date('1900-01-01'), 'Fecha Nacimiento es invalida')
    .max(new Date(), 'Fecha Nacimiento es invalida')
    .nullable(),
  telefono: Yup.string().required(),
  email: Yup.string().required().email(),
  direccion: Yup.string().required(),
  pais: Yup.string().required(),
  ciudad: Yup.string().required(),
  observacion: Yup.string().nullable(),
  contacto: Yup.string().nullable(),
  grupoId: Yup.string().required()
}) as ObjectSchema<ClienteType>;

export const getClienteLabel = (field: string): string => {
  const labels = {
    name: 'Nombre Completo',
    birthday: 'Fecha Nacimiento',
    contacto: 'Información de Contacto',
    grupoId: 'Grupo Clientes'
  } as Record<string, string>;
  if (field in labels) {
    return labels[field];
  }
  return capitalize(field);
};
