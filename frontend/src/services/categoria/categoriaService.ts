import { AxiosInstance } from 'axios';
import {createAxiosService} from "../../util";

export type CategoriaType = {
  id?: number;
  activo: boolean;
  name: string;
};

class CategoriaService {
  private service: AxiosInstance;

  constructor() {
    this.service = createAxiosService('/categorias');
  }

  async get(empresaId: string): Promise<CategoriaType[]> {
    const { data } = await this.service.get(`?empresaId=${empresaId}`);
    return data;
  }

}

export default new CategoriaService();
