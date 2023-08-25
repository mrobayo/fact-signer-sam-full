import React from 'react';
import {AuthContext} from "./auth.tsx";

export function useAuth() {
  return React.useContext(AuthContext);
}
