import {Outlet, Route, Routes} from "react-router-dom";

import Dashboard from "./layout/Dashboard/Dashboard.tsx";
import LoginPage from "./layout/LoginPage/LoginPage.tsx";
import PublicPage from "./layout/PublicPage/PublicPage.tsx";
import FacturasPage from "./pages/FacturasPage/FacturasPage.tsx";
import NoMatch from "./components/ui/NoMatch/NoMatch.tsx";
import EditFactura from "./pages/FacturasPage/EditFactura/EditFactura.tsx";

function App() {
  return (
      <Routes>
        <Route element={<Dashboard><Outlet /></Dashboard>}>
          <Route path="/" element={<PublicPage />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/facturas" element={<FacturasPage />} />
          <Route
              path="/facturas/new"
              element={<EditFactura isNew />}
            />
          <Route
            path="/facturas/edit/:id"
            element={<div>Factura edit 1</div>}
          />
        </Route>
        <Route path="*" element={<NoMatch />} />
      </Routes>
  )
}

export default App
