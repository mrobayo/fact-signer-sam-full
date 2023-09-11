import React from "react";
import axios from "axios";
import {type AuthContextType, PuntoVentaType, UserProfileType} from './types';
import {ApiEndpoint} from "../../constants.ts";

interface AuthLoginResponse {
  accessToken: string;
  email: string;
  roles: string[];
  puntoVenta: PuntoVentaType;
  tokenType: string;
}

/**
 * This represents some generic auth provider API, like Firebase.
 */
export const jwtAuthProvider = {
  isAuthenticated: false,
  async signin(username: string, password: string, empresaId: string, callback: (data: AuthLoginResponse) => void) {
    const { data } = await axios.post(`${ApiEndpoint}/auth/login`, {username, password, empresaId}); // console.log(user, password);
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
  let user;
  let puntoVenta;
  try {
    user = JSON.parse(localStorage.getItem('login') ?? '');
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
      const user: UserProfileType = {login: newUser, email: data.email, roles: data.roles};
      localStorage.setItem('login', JSON.stringify(user));
      localStorage.setItem('punto', JSON.stringify(data.puntoVenta));
      localStorage.setItem('token', data.accessToken);
      setLogin({ user, puntoVenta: data.puntoVenta });
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
      localStorage.removeItem('login');
      localStorage.removeItem('punto');
      localStorage.removeItem('token');
      setLogin({ user: null, puntoVenta: {} as PuntoVentaType });
      callback();
    });
  };

  const value: AuthContextType = { ...login, signin, signout };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}



