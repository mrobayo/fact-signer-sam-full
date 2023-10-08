import axios, {AxiosInstance} from "axios";
import {ApiEndpoint, isDevelopment} from "../constants.ts";

export type PageType<T> = {
  content: T[]
  last: boolean;
  pageNumber: number;
  pageSize: number;
  totalElements: number;
  totalPages: number;
}

export function createAxiosService(apiPath: string): AxiosInstance {

  const service = axios.create({
    baseURL: `${ApiEndpoint}${apiPath}`,
    // headers: token ? { Authorization: `Bearer ${token}` } : {},
  });

  service.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");
  if (config.headers) {
    config.headers["Authorization"] = `Bearer ${token}`;
  } else {
    // let headers: AxiosRequestHeaders = {
    //   'Content-Type': 'application/json',
    //   crossDomain: true,
    //   Authorization: `Bearer ${token}`,
    // };
    // config.headers = headers;
    console.log('here header error');
  }

  return config;
});

  const handleSuccess = (response: any) => {
    return response;
  }

  const handleError = (error: any) => {
    if (isDevelopment) {
      console.log('axios-error', error);
    }
    switch (error?.response?.status) {
      case 401: // Token expired
        console.log('401');
        delete service.defaults.headers["Authorization"];

        localStorage.removeItem('login');
        localStorage.removeItem('punto');
        localStorage.removeItem("token");

        //this.redirectTo("/login");
        window.location.pathname =  '/login';
        break;
      case 404: // Not found
        console.log('404');
        //this.redirectTo("/404");
        break;
      default: // Internal server error
        console.log('internal server error', error);
        //this.redirectTo("/500");
        break;
    }
    return Promise.reject(error);
  }

  service.interceptors.response.use(handleSuccess, handleError);
  return service;
}
