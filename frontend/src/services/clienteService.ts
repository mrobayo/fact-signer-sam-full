import { AxiosInstance } from 'axios';
import {buildQueryByPage, createAxiosService, PageType} from "../util";

export type ClienteType = {
  id: string;
  name: string;
  activo: boolean;
  tipo: string;
  identidad: string;
  nombres: string;
  apellidos: string;
  birthday: Date;
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

class ClienteService {
  private service: AxiosInstance;

  constructor() {
    this.service = createAxiosService('/clientes');
  }

  async get(page: number, size: number, sort: string[]): Promise<PageType<ClienteType>> {
    const { data } = await this.service.get(buildQueryByPage(page, size, sort));
    return data;
  }

}

export default new ClienteService();
