import React, {useState} from 'react';
import withAuth from "../../services/auth/withAuth.tsx";
import Box from '@mui/material/Box';

import {
  useQuery,
} from '@tanstack/react-query';
import {
  DataGrid,
  GridColDef,
  GridPaginationModel,
  GridValueGetterParams,
} from '@mui/x-data-grid';
import {Title} from "../../components/ui";
import clienteService from "../../services/clienteService.ts";
import {getAge} from "../../util";
import {PageSize} from "../../constants.ts";

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
  const [page, setPage] = useState(0);
  const { isLoading, error, data, isPreviousData } = useQuery({
    queryKey: ['clientes', page],
    queryFn: async () => {
      return await clienteService.get(page, PageSize, []);
    },
    keepPreviousData: true,
    staleTime: 30000,
  }, [page]);
  //const apiRef = useGridApiRef();

  if (isLoading) return 'Loading...';
  if (error) return 'An error has occurred.';

  console.log('isPreviousData', isPreviousData);

  return (
    <div>
      <Title>Clientes</Title>
      <Box sx={{ height: 400, width: '100%', backgroundColor: 'white' }}>
        <DataGrid
          //apiRef={apiRef}
          rows={data?.content ?? []}
          columns={columns}
          initialState={{
            pagination: { paginationModel: { pageSize: PageSize, }, },
          }}
          onPaginationModelChange={(model: GridPaginationModel) => {
            setPage(model.page);
          }}
          paginationModel={{ page, pageSize: PageSize}}
          paginationMode="server"
          pageSizeOptions={[PageSize]}
          rowCount={data?.totalElements ?? 0}
          checkboxSelection
          disableRowSelectionOnClick
        />

        {/*<DataGrid*/}
        {/*  rows={data?.content ?? []}*/}
        {/*  columns={columns}*/}
        {/*  // initialState={{*/}
        {/*  //   pagination: { paginationModel: { pageSize: PageSize, }, },*/}
        {/*  // }}*/}
        {/*  pagination*/}
        {/*  // pageSize={PageSize}*/}
        {/*  //rowsPerPageOptions={[PageSize]}*/}
        {/*  rowCount={data?.totalElements ?? 0}*/}
        {/*  paginationMode="server"*/}
        {/*  //onPageChange={handlePageChange}*/}
        {/*  //page={page}*/}
        {/*  pageSizeOptions={[PageSize]}*/}
        {/*  checkboxSelection*/}
        {/*  disableRowSelectionOnClick*/}
        {/*  loading={isLoading}*/}
        {/*/>*/}
      </Box>
    </div>
  )
};

export default withAuth(Clientes);
