import { AxiosInstance } from 'axios';
import {buildQueryByPage, createAxiosService} from "../util";

class ClienteService {
  private service: AxiosInstance;

  constructor() {
    this.service = createAxiosService('/clientes');
  }

  async get(page: number, size: number, sort: string[]) {
    const { data } = await this.service.get(buildQueryByPage(page, size, sort));
    return data;
  }

}

export default new ClienteService();
