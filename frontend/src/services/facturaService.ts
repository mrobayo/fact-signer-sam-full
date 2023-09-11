import axios, {AxiosRequestConfig} from 'axios';
import {ApiEndpoint} from "../constants.ts";

class FacturaService {
  private service; //: axios.AxiosInstance;

  constructor() {
    const token = localStorage.getItem("token");
    const service = axios.create({
      baseURL: ApiEndpoint,
      headers: token
        ? {
            Authorization: `Bearer ${token}`,
          }
        : {},
    });
    service.interceptors.response.use(this.handleSuccess, this.handleError);
    this.service = service;
  }

  handleSuccess(response: any) {
    return response;
  }

  handleError(error: any) {
    switch (error.response.status) {
      case 401:
        // Token expired
        console.log('401');
        delete this.service.defaults.headers["Authorization"];
        window.localStorage.removeItem("token");
        //this.redirectTo("/login");
        break;
      case 404:
        // Not found
        console.log('404');
        //this.redirectTo("/404");
        break;
      default:
        // Internal server error
        console.log('internal server error');
        //this.redirectTo("/500");
        break;
    }
    return Promise.reject(error);
  }

  async get(url: string, config?: AxiosRequestConfig<any> | undefined) {
    const { data } = await this.service.get(url, config);
    console.log(data);
    return data;
  }
  
}

export default new FacturaService();
