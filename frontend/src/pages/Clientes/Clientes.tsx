import React, {useEffect, useState} from 'react';
import withAuth from "../../services/auth/withAuth.tsx";
import Box from '@mui/material/Box';

import { DataGrid, GridColDef, GridValueGetterParams } from '@mui/x-data-grid';
import {Title} from "../../components/ui";
import clienteService from "../../services/clienteService.ts";
import {getAge} from "../../util";

const columns: GridColDef[] = [
  //{ field: 'id', headerName: 'ID', width: 90 },
  {
    field: 'identidad',
    headerName: 'Ident.',
    type: 'string',
    width: 110,
  },
  {
    field: 'name',
    headerName: 'Nombres Apellidos',
    width: 190,
  },
  {
    field: 'edad',
    headerName: 'Edad',
    width: 60,
    valueGetter: (params: GridValueGetterParams) => getAge(params.row.birthday)
  },
  {
    field: 'grupoId',
    headerName: 'Grupo',
    sortable: false,
    width: 160,
  },
  {
    field: 'ultimaVenta',
    headerName: 'Ultima Venta',
    sortable: false,
    width: 110,
  },
];

const Clientes: React.FC = () => {
  const [data, setData] = useState<any[]>([]);
  const load = async () => {
    const data1 = await clienteService.get();
    console.log('data', data1);
    setData(data1);
  }
  useEffect(() => {
    load();
  }, []);

  return (
    <div>
      <Title>Clientes</Title>
      <Box sx={{ height: 400, width: '100%', backgroundColor: 'white' }}>
      <DataGrid
        rows={data}
        columns={columns}
        initialState={{
          pagination: { paginationModel: { pageSize: 5, }, },
        }}
        pageSizeOptions={[5]}
        checkboxSelection
        disableRowSelectionOnClick
      />
    </Box>
    </div>
  )
};

export default withAuth(Clientes);
