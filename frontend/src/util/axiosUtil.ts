import axios, {AxiosInstance} from "axios";
import {ApiEndpoint} from "../constants.ts";


  export function createAxiosService(apiPath: string): AxiosInstance {
    const token = localStorage.getItem("token");
    const service = axios.create({
      baseURL: `${ApiEndpoint}${apiPath}`,
      headers: token ? { Authorization: `Bearer ${token}` } : {},
    });

    const handleSuccess = (response: any) => {
      return response;
    }

    const handleError = (error: any) => {
      switch (error.response.status) {
        case 401: // Token expired
          console.log('401');
          delete service.defaults.headers["Authorization"];
          window.localStorage.removeItem("token");
          //this.redirectTo("/login");
          break;
        case 404: // Not found
          console.log('404');
          //this.redirectTo("/404");
          break;
        default: // Internal server error
          console.log('internal server error');
          //this.redirectTo("/500");
          break;
      }
      return Promise.reject(error);
    }

    service.interceptors.response.use(handleSuccess, handleError);
    return service;
  }