import { AxiosInstance } from 'axios';
import {buildQueryByPage, createAxiosService, PageType} from "../../util";

export type ProductoType = {
  id?: string;
  name: string;
  codigo?: string;
  activo: true;
  empresaId?: string;
  tipo: string;
  codigoIva: number;
  unidadVentaId: string;
  categoriaId?: number;
  control: string;
  vendido: boolean;
  comprado: boolean;
};

export function productoEmpty(defaultValues: Partial<ProductoType>): ProductoType {
  const {empresaId, categoriaId} = defaultValues;

  return {
    empresaId,
    categoriaId,
    name: '',
    codigo: undefined,
    activo: true,
    tipo: 'BIEN',
    codigoIva: 2,
    unidadVentaId: 'UN',
    control: 'ALMACENABLE',
    vendido: false,
    comprado: false
  };
}

class ProductoService {
  private service: AxiosInstance;

  constructor() {
    this.service = createAxiosService('/productos');
  }

  async getById(id: string|undefined, options?: { defaultValues: Partial<ProductoType> }): Promise<ProductoType> {
    if (id) {
      const { data } = await this.service.get(`/${id}`);
      return data;
    }
    else {
      return productoEmpty(options?.defaultValues ?? {});
    }
  }

  async get(empresaId: string, page: number, size: number, sort: string[]): Promise<PageType<ProductoType>> {
    const { data } = await this.service.get(buildQueryByPage(page, size, sort)+`&empresa_id=${empresaId}`);
    return data;
  }

  async create(body: ProductoType): Promise<ProductoType> {
    return await this.service.post('/', body);
  }

  async update(id: string, body: ProductoType): Promise<ProductoType> {
    return await this.service.put(`/${id}`, body);
  }

  async delete(id: string): Promise<string> {
    return await this.service.delete(`/${id}`);
  }

}

export default new ProductoService();
