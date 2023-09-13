import ReactDOM from 'react-dom/client'
import {BrowserRouter} from "react-router-dom";
import {QueryClient, QueryClientProvider} from "@tanstack/react-query";
import { ReactQueryDevtools } from '@tanstack/react-query-devtools'
import { LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs'
import 'dayjs/locale/es';

import App from './App.tsx'
import {AuthProvider} from "./services/auth/auth.tsx";
import {isDevelopment} from "./constants.ts";

const queryClient = new QueryClient();
const reactQueryDevtools = isDevelopment ? <ReactQueryDevtools initialIsOpen={true} /> : undefined;

ReactDOM.createRoot(document.getElementById('root')!).render(
  <BrowserRouter>
    <LocalizationProvider dateAdapter={AdapterDayjs} adapterLocale="es">
    <AuthProvider>
      <QueryClientProvider client={queryClient}>
        <App />
        {reactQueryDevtools}
      </QueryClientProvider>
    </AuthProvider>
    </LocalizationProvider>
  </BrowserRouter>
)
