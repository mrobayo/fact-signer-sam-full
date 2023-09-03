import React from "react";
import axios from "axios";
import {type AuthContextType, PuntoVentaType} from './types';

interface AuthLoginResponse {
  accessToken: string;
  puntoVenta: PuntoVentaType;
  tokenType: string;
}

/**
 * This represents some generic auth provider API, like Firebase.
 */
export const jwtAuthProvider = {
  isAuthenticated: false,
  async signin(username: string, password: string, empresaId: string, callback: (data: AuthLoginResponse) => void) {
    const { data } = await axios.post('http://localhost:8081/api/auth/login', {username, password, empresaId}); // console.log(user, password);
    jwtAuthProvider.isAuthenticated = true;
    callback(data); // async
  },
  signout(callback: VoidFunction) {
    jwtAuthProvider.isAuthenticated = false;
    setTimeout(callback, 100);
  },
};

type LoginState = Omit<AuthContextType, "signin"|"signout">;

function loadAuthContext(): LoginState {
  const user = localStorage.getItem('login');
  let puntoVenta;
  try {
    puntoVenta = JSON.parse(localStorage.getItem('punto') ?? '');
  } catch (_) {
    //
  }
  return { user, puntoVenta };
}

export const AuthContext = React.createContext<AuthContextType>(null!);

export function AuthProvider({ children }: { children: React.ReactNode }) {
  const [login, setLogin] = React.useState<LoginState>(loadAuthContext());

  const signin = (newUser: string, password: string, empresaId: string, loginPageCallback: VoidFunction) => {
    jwtAuthProvider.signin(newUser, password, empresaId,(data: AuthLoginResponse) => {
      localStorage.setItem('login', newUser);
      localStorage.setItem('punto', JSON.stringify(data.puntoVenta));
      localStorage.setItem('token', data.accessToken);
      setLogin({ user: newUser, puntoVenta: data.puntoVenta });
      loginPageCallback();
    }).catch((error: any) => {
        setLogin({
          user: null,
          puntoVenta: {} as PuntoVentaType,
          error: error?.response?.data?.message ?? error.message
        })
    });
  };

   const signout = (callback: VoidFunction) => {
    return jwtAuthProvider.signout(() => {
      setLogin({ user: null, puntoVenta: {} as PuntoVentaType });
      localStorage.removeItem('login');
      callback();
    });
  };

  const value: AuthContextType = { ...login, signin, signout };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}



