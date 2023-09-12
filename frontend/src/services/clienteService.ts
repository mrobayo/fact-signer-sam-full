import { AxiosInstance } from 'axios';
import {buildQueryByPage, createAxiosService, PageType} from "../util";
import {ClienteType} from "../pages/Clientes/cliente.types.ts";

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
