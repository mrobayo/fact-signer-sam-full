import {Outlet, Route, Routes} from "react-router-dom";

import Dashboard from "./layout/Dashboard/Dashboard.tsx";
import LoginPage from "./layout/LoginPage/LoginPage.tsx";
import PublicPage from "./layout/PublicPage/PublicPage.tsx";
import FacturasPage from "./pages/FacturasPage/FacturasPage.tsx";
import RequireAuth from "./services/auth/RequireAuth.tsx";

function App() {
  return (
      <Routes>
        <Route element={<Dashboard><Outlet /></Dashboard>}>
          <Route path="/" element={<PublicPage />} />
          <Route path="/login" element={<LoginPage />} />
          <Route
            path="/protected"
            element={
              <RequireAuth>
                <FacturasPage />
              </RequireAuth>
            }
          />
        </Route>
      </Routes>
  )
}

export default App
