import {buildQueryByPage, createAxiosService, PageType} from "../../util";

export type ImpuestoType = {
  codigo?: number,
  codigoPorcentaje?: number,
  tarifa?: number,
  baseImponible: number,
  valor: number,
};

export type PagoType = {
  formaPago: string,
  total: number,
  plazo: number,
  unidadTiempo?: string
};

export type DetalleFacturaType = {
  linea: number;
  itemId?: string|null;
  codigoPrincipal: string;
  codigoAuxiliar?: string;
  descripcion: string;
  cantidad: number;
  precioUnitario: number;
  descuento: number;
  precioTotalSinImpuesto: number;
  detallesAdicionales?: Record<string, string>;
  hasIva?: boolean;
  iva?: ImpuestoType
};

export type FacturaType = {
  id?: string;
  empresaId?: string;
  puntoVentaId?: string;
  name?: string;
  secuencia?: number,
  estadoDoc?: string;
  aprobado: boolean;
  aprobadoPorId?: string;
  loteId?: string;
  claveAcceso?: string;
  tipoDoc: string;
  fechaEmision?: string;
  fechaHora?: string;
  moneda?: string;
  autorizacion?: string;
  mensajeSri?: string;
  emailEnviado: boolean;
  sujetoEmail?: string;
  tipoFactura?: string;
  totalDescuento: number;
  propina: number;
  totalSinImpuestos: number;
  importeTotal: number;
  valorRetIva?: number;
  valorRetRenta?: number;
  guiaRemision?: string;
  clienteId?: string;
  // clienteName?: string;
  tipoIdentificacionComprador?: string;
  razonSocialComprador?: string;
  identificacionComprador?: string;
  direccionComprador?: string;
  detalles: DetalleFacturaType[];
  pagos: PagoType[];
  observacion?: string;
}

export function facturaEmpty(defaultValues: Partial<FacturaType>): FacturaType {
  const {
    empresaId,
    puntoVentaId,
    moneda,
    clienteId,
    // clienteName,
    tipoIdentificacionComprador,
    razonSocialComprador,
    identificacionComprador,
    direccionComprador,
    sujetoEmail
  } = defaultValues;

  return {
    empresaId,
    puntoVentaId,
    aprobado: false,
    //loteId?: string;
    //claveAcceso?: string;
    tipoDoc: "FACTURA",
    //fechaEmision: string;
    //fechaHora: string;
    moneda,
    //autorizacion: string;
    //mensajeSri?: string;
    emailEnviado: false,
    sujetoEmail,
    tipoFactura: "VENTA_LOCAL",
    totalDescuento: 0,
    propina: 0,
    totalSinImpuestos: 0,
    importeTotal: 0,
    //valorRetIva?: number;
    //valorRetRenta?: number;
    //guiaRemision?: string;
    clienteId,
    // clienteName,
    tipoIdentificacionComprador,
    razonSocialComprador,
    identificacionComprador,
    direccionComprador,
    detalles: [{
        linea: 1,
        itemId: null,
        codigoPrincipal: '',
        codigoAuxiliar: '',
        descripcion: '',
        cantidad: 1,
        precioUnitario: 0,
        descuento: 0,
        precioTotalSinImpuesto: 0,
        detallesAdicionales: {},
        hasIva: true,
        iva: {
          codigoPorcentaje: undefined,
          tarifa: undefined,
          baseImponible: 0,
          valor: 0
        }
      }],
    pagos: [{
      formaPago: '01',
      total: 0,
      plazo: 0,
      unidadTiempo: 'dias'
    }]
  };
}

class FacturaService {
  private service; //: axios.AxiosInstance;

  constructor() {
    this.service = createAxiosService('/facturas');
  }

  async getById(id: string|undefined, options?: { defaultValues: Partial<FacturaType> }): Promise<FacturaType> {
    if (id) {
      const { data } = await this.service.get(`/${id}`);
      return data;
    }
    else {
      return facturaEmpty(options?.defaultValues ?? {});
    }
  }

  async get(empresaId: string, page: number, size: number, sort: string[]): Promise<PageType<FacturaType>> {
    const { data } = await this.service.get(buildQueryByPage(page, size, sort)+`&empresa_id=${empresaId}`);
    return data;
  }

  async create(body: FacturaType): Promise<FacturaType> {
    const { data } = await this.service.post('/', body);
    return data;
  }

  async approve(id: string|undefined): Promise<String> {
    return await this.service.get(`/approve/${id}`);
  }

  // async get1(url: string, config?: AxiosRequestConfig<any> | undefined) {
  //   const { data } = await this.service.get(url, config);
  //   console.log(data);
  //   return data;
  // }
  
}

export default new FacturaService();
