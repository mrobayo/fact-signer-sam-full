export type AuthFormType = {
    id: string;
    username: string;
    password: string;
    empresaId: string;
};

export type EmpresaType = {
    id: string;
    name: string;
    ruc: string;
    color: string;
    ambiente: string;
    tarifaIva: number;
    moneda: string;
};

export type PuntoVentaType = {
    id: string;
    empresaId: string;
    estab: string;
    ptoEmi: string;
    direccion: string;
    telefono: string;
    secuencias: any,
    empresa: EmpresaType;
};

export interface UserProfileType {
  login: string;
  email: string;
  roles: string[];
}

export interface AuthContextType {
  user: UserProfileType|null;
  puntoVenta: PuntoVentaType; // Punto de venta
  error?: string;
  signin: (user: string, password: string, empresaId: string, callback: VoidFunction) => void;
  signout: (callback: VoidFunction) => void;
}
