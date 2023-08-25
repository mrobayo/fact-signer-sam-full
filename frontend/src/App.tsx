import Dashboard from "./layout/Dashboard/Dashboard.tsx";
import FacturasPage from "./pages/FacturasPage/FacturasPage.tsx";
import {CoreAdmin} from "./core";
import {DataProvider} from "./types.ts";

const defaultDataProvider: DataProvider = {
    // @ts-ignore
    create: () => Promise.resolve({ data: { id: 0 } }),
    // @ts-ignore
    delete: () => Promise.resolve({ data: {} }),
    deleteMany: () => Promise.resolve({}),
    getList: () => Promise.resolve({ data: [], total: 0 }),
    getMany: () => Promise.resolve({ data: [] }),
    getManyReference: () => Promise.resolve({ data: [], total: 0 }),
    // @ts-ignore
    getOne: () => Promise.resolve({ data: {} }),
    // @ts-ignore
    update: () => Promise.resolve({ data: {} }),
    updateMany: () => Promise.resolve({}),
};

function App() {



  return (
    <CoreAdmin dataProvider={defaultDataProvider}>
      <Dashboard>
        <FacturasPage />
      </Dashboard>
    </CoreAdmin>
  )
}

export default App
