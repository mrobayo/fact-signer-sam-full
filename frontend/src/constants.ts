
export const isDevelopment = import.meta.env.DEV;

export const AppTitle = import.meta.env.VITE_APP_TITLE;
export const ApiEndpoint = isDevelopment ? 'http://localhost:8081' : import.meta.env.VITE_APP_API_ENDPOINT;
