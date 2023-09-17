import { AxiosInstance } from 'axios';
import {buildQuery, createAxiosService, PageType} from "../../util";
import dayjs from "dayjs";

export type ClienteType = {
  id?: string;
  name: string;
  activo: boolean;
  tipo: string;
  identidad: string;
  nombres: string;
  apellidos: string;
  birthday?: dayjs.Dayjs|Date|string;
  telefono: string;
  email: string;
  direccion: string;
  pais: string;
  ciudad: string;
  observacion: string;
  ultimaVenta: string;
  contacto: string;
  aseguradora: boolean;
  grupoId: string;
};

export function clienteEmpty(): ClienteType {
  return {
    name: '',
    activo: true,
    tipo: 'CED',
    identidad: '',
    nombres: '',
    apellidos: '',
    birthday: undefined,
    telefono: '',
    email: '',
    direccion: '',
    pais: '',
    ciudad: '',
    observacion: '',
    ultimaVenta: '',
    contacto: '',
    aseguradora: false,
    grupoId: 'PARTICULAR'
  };
}

class ClienteService {
  private service: AxiosInstance;

  constructor() {
    this.service = createAxiosService('/clientes');
  }

  async getById(id?: string): Promise<ClienteType> {
    if (id) {
      const { data } = await this.service.get(`/${id}`);
      return data;
    }
    else {
      return clienteEmpty();
    }
  }

  async get(search: string, activo: boolean|undefined, page: number, size: number, sort: string[]): Promise<PageType<ClienteType>> {
    const url = "?"+buildQuery({search, activo: `${activo ?? 'all'}`, page, size, sort});
    console.log(url, {search, activo: `${activo ?? 'all'}`, page, size, sort})
    const { data } = await this.service.get(url);
    return data;
  }

  async create(body: ClienteType): Promise<ClienteType> {
    return await this.service.post('/', body);
  }

  async update(id: string, body: ClienteType): Promise<ClienteType> {
    return await this.service.put(`/${id}`, body);
  }

  async delete(id: string): Promise<string> {
    return await this.service.delete(`/${id}`);
  }

}

export default new ClienteService();
