import * as Yup from "yup";
import {ObjectSchema} from "yup";
import { yupLocaleEs } from '../../util';
import {capitalize} from "lodash";
import {ClienteType} from "../../services/clienteService";
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
    .min(new Date('1920-01-01'), 'Fecha debe ser mayor 01/01/1920')
    .max(new Date(), 'Fecha Nacimiento mayor a Hoy es invalida')
    .required('Fecha Nacimiento es obligatorio'),
  telefono: Yup.string().required(),
  email: Yup.string().required().email(),
  direccion: Yup.string().required(),
  pais: Yup.string().required(),
  ciudad: Yup.string().required(),
  observacion: Yup.string(),
  contacto: Yup.string(),
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