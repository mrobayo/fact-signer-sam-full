import React, {useMemo, useState} from 'react';
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
  GridToolbarContainer,
  GridToolbarExport,
  GridActionsCellItem,
  GridRowId,
} from '@mui/x-data-grid';
import {Title} from "../../components/ui";
import clienteService from "../../services/clienteService.ts";
import {getAge, PageType} from "../../util";
import {PageSize} from "../../constants.ts";
import IconButton from "@mui/material/IconButton";
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/DeleteOutlined';
import AddIcon from '@mui/icons-material/AddBoxRounded';
import {ClienteType} from "./cliente.types.ts";
import PeopleAltTwoToneIcon from "@mui/icons-material/PeopleAltTwoTone";

function CustomToolbar() {
  return (
    <GridToolbarContainer sx={{ justifyContent: 'flex-end' }}>
      {/*<GridToolbarColumnsButton />*/}
      {/*<GridToolbarFilterButton />*/}
      {/*<GridToolbarDensitySelector />*/}
      <GridToolbarExport />
      <span><IconButton
        color="info"
        onClick={() => console.log()}>
        <AddIcon />
      </IconButton></span>
    </GridToolbarContainer>
  );
}

const Clientes: React.FC = () => {
  const [page, setPage] = useState(0);
  const { isLoading, data  } = useQuery<PageType<ClienteType>, Error>({
    queryKey: ['clientes', page],
    queryFn: () => clienteService.get(page, PageSize, []),
    keepPreviousData: true,
    staleTime: 30000,
  });
  //const apiRef = useGridApiRef();

  const handleEditClick = (id: GridRowId) => () => {
    console.log('editing', data?.content?.find((row) => row.id === id));
    //setRowModesModel({ ...rowModesModel, [id]: { mode: GridRowModes.Edit } });
  };

  // const handleSaveClick = (id: GridRowId) => () => {
  //   setRowModesModel({ ...rowModesModel, [id]: { mode: GridRowModes.View } });
  // };

  const handleDeleteClick = (id: GridRowId) => () => {
    console.log('del', data?.content?.filter((row) => row.id !== id));
    //setRows();
  };

  const columns: GridColDef[] = useMemo(() => {
    return [
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
          flex: 1,
          minWidth: 200,
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
        {
          field: 'actions',
          type: 'actions',
          headerName: '...',
          width: 100,
          cellClassName: 'actions',
          getActions: ({id}) => {
            //const isInEditMode = rowModesModel[id]?.mode === GridRowModes.Edit;
            return [
              <GridActionsCellItem
                icon={<EditIcon/>}
                label="Edit"
                className="textPrimary"
                onClick={handleEditClick(id)}
                color="inherit"
              />,
              <GridActionsCellItem
                icon={<DeleteIcon/>}
                label="Delete"
                onClick={handleDeleteClick(id)}
                color="inherit"
              />,
            ];
          }
        }
      ] as GridColDef[];
  }, []);


  // if (error) return 'An error has occurred.';

  return (
    <div>
      <Title><PeopleAltTwoToneIcon sx={{ m: 2, mb: '-4px' }} /> Clientes</Title>

      <Box sx={{ height: 450, width: '100%', backgroundColor: 'white' }}>
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
          loading={isLoading}
          slots={{
            toolbar: CustomToolbar,
          }}
        />

      </Box>
    </div>
  )
};

export default withAuth(Clientes);
