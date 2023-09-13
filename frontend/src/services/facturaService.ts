import { AxiosRequestConfig } from 'axios';
import { createAxiosService } from "../util";

class FacturaService {
  private service; //: axios.AxiosInstance;

  constructor() {
    this.service = createAxiosService('facturas');
  }

  async get(url: string, config?: AxiosRequestConfig<any> | undefined) {
    const { data } = await this.service.get(url, config);
    console.log(data);
    return data;
  }
  
}

export default new FacturaService();
