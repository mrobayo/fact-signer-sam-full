import { AxiosInstance } from 'axios';
import { createAxiosService } from "../util";

class ClienteService {
  private service: AxiosInstance;

  constructor() {
    this.service = createAxiosService('/clientes');
  }

  async get() {
    const { data } = await this.service.get('?page=1');
    console.log(data);
    return data;
  }

}

export default new ClienteService();
