import { AxiosInstance } from 'axios';
import {createAxiosService} from "../../util";

export type GrupoType = {
  id: string;
  activo: boolean;
  name: string;
};

class GrupoService {
  private service: AxiosInstance;

  constructor() {
    this.service = createAxiosService('/grupos');
  }

  async get(): Promise<GrupoType[]> {
    const { data } = await this.service.get('');
    return data;
  }

}

export default new GrupoService();
