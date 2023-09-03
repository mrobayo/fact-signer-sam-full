import React from "react";
import axios from "axios";

export interface AuthLoginResponse {
  accessToken: string,
  puntos: string[],
  tokenType: string
}

export interface AuthContextType {
  user: any;
  puntos: string[]; // Punto de venta
  error?: string;
  signin: (user: string, password: string, callback: VoidFunction) => void;
  signout: (callback: VoidFunction) => void;
}

/**
 * This represents some generic auth provider API, like Firebase.
 */
export const jwtAuthProvider = {
  isAuthenticated: false,
  async signin(username: string, password: string, callback: (data: AuthLoginResponse) => void) {
    const { data } = await axios.post('http://localhost:8081/api/auth/login', {username, password}); // console.log(user, password);
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
  const puntos = localStorage.getItem('puntos');
  return { user, puntos: puntos ? puntos.split(',') : [] };
}

export const AuthContext = React.createContext<AuthContextType>(null!);

export function AuthProvider({ children }: { children: React.ReactNode }) {
  const [login, setLogin] = React.useState<LoginState>(loadAuthContext());

  const signin = (newUser: string, password: string, loginPageCallback: VoidFunction) => {
    jwtAuthProvider.signin(newUser, password,(data: AuthLoginResponse) => {
      localStorage.setItem('login', newUser);
      localStorage.setItem('puntos', data.puntos.join(','));
      localStorage.setItem('token', data.accessToken);
      setLogin({ user: newUser, puntos: data.puntos });
      loginPageCallback();
    }).catch((error: any) => {
        setLogin({
          user: null,
          puntos: [],
          error: error?.response?.data?.message ?? error.message
        })
    });
  };

   const signout = (callback: VoidFunction) => {
    return jwtAuthProvider.signout(() => {
      setLogin({ user: null, puntos: [] });
      localStorage.removeItem('login');
      callback();
    });
  };

  const value: AuthContextType = { ...login, signin, signout };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}



