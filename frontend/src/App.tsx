import {Outlet, Route, Routes} from "react-router-dom";

import Dashboard from "./layout/Dashboard/Dashboard.tsx";
import LoginPage from "./layout/LoginPage/LoginPage.tsx";
import PublicPage from "./layout/PublicPage/PublicPage.tsx";
import FacturasPage from "./pages/FacturasPage";
import NoMatch from "./components/ui/NoMatch/NoMatch.tsx";
import EditFactura from "./pages/EditFacturaPage";
import Clientes, {EditCliente} from "./pages/Clientes";
import Productos, {EditProducto} from "./pages/Productos";
import Admin from "./pages/Admin";
import Arqueo from "./pages/Arqueo";

// import { activeYupLocale } from "./util";
// activeYupLocale();

function App() {
  return (
      <Routes>
        <Route element={<Dashboard><Outlet /></Dashboard>}>
          <Route path="/" element={<PublicPage />} />
          <Route path="/login" element={<LoginPage />} />

          <Route path="/facturas">
            <Route index element={<FacturasPage />} />
            <Route path="new" element={<EditFactura />} />
            <Route path="edit/:id" element={<div>Factura edit 1</div>} />
          </Route>

          <Route path="/clientes">
            <Route index element={<Clientes />} />
            <Route path="new" element={<EditCliente />} />
            <Route path="edit/:id" element={<EditCliente />} />
          </Route>

          <Route path="/productos">
            <Route index element={<Productos />} />
            <Route path="new" element={<EditProducto />} />
            <Route path="edit/:id" element={<EditProducto />} />
          </Route>

          <Route path="/arqueo">
            <Route index element={<Arqueo />} />
          </Route>

          <Route path="/admin">
            <Route index element={<Admin />} />
          </Route>

        </Route>
        <Route path="*" element={<NoMatch />} />
      </Routes>
  )
}

export default App
