import React, {useEffect, useMemo, useRef, useState} from 'react';
import withAuth from "../../services/auth/withAuth.tsx";
import Box from '@mui/material/Box';

import {
  useQuery,
} from '@tanstack/react-query';
import {
  DataGrid,
  GridColDef,
  GridPaginationModel,
  GridCellParams,
  GridValueGetterParams,
  GridToolbarContainer,
  GridToolbarExport,
  GridActionsCellItem,
  GridRowId,
  GridToolbarColumnsButton,
} from '@mui/x-data-grid';
import {Title} from "../../components/ui";
import {getAge, PageType} from "../../util";
import {PageSize} from "../../constants.ts";
import IconButton from "@mui/material/IconButton";
import OutlinedInput from '@mui/material/OutlinedInput';
import DeleteIcon from '@mui/icons-material/DeleteOutlined';
import AddIcon from '@mui/icons-material/AddBoxRounded';
import InputAdornment from '@mui/material/InputAdornment';
import ModeEditIcon from '@mui/icons-material/ModeEdit';
import PeopleAltTwoToneIcon from "@mui/icons-material/PeopleAltTwoTone";
import {useNavigate} from "react-router-dom";
import {clienteService, type ClienteType} from "../../services";
import SearchIcon from "@mui/icons-material/Search";
import {debounce} from "lodash";
import {Switch} from "@mui/material";

interface ClienteToolbarProps {
  criteria: string;
  setCriteria: (criteria: string) => void;
  activo: boolean;
  setActivo: (activo: boolean) => void
}

function ClienteToolbar({criteria, setCriteria, activo, setActivo}: ClienteToolbarProps) {
  const navigate = useNavigate();
  const [text, setText] = useState(criteria);

  const debouncedSearch = useRef(
    debounce(async (criteria) => setCriteria(criteria), 500)).current;
  useEffect(() => () => debouncedSearch.cancel(), [debouncedSearch]);

  return (
    <GridToolbarContainer sx={{ justifyContent: 'flex-end' }}>
      <OutlinedInput
        value={text}
        onChange={(event: React.ChangeEvent<HTMLInputElement>) => {
          setText(event.target.value);
          debouncedSearch(event.target.value);
        }}
        sx={{ flexGrow: 1 }}
        autoFocus
        placeholder="Buscar por Identificacion/Nombre..."
        startAdornment={<InputAdornment position="start"><SearchIcon /></InputAdornment>}
      />
      <Switch
        checked={activo}
        onChange={() => setActivo(!activo)}
        inputProps={{ 'aria-label': 'Cliente Activo' }}
      />
      <Box sx={{ minWidth: 200 }}/>
      <GridToolbarColumnsButton  />
      <GridToolbarExport />
      <span><IconButton color="info" onClick={() => navigate('/clientes/new')}>
        <AddIcon />
      </IconButton></span>
    </GridToolbarContainer>
  );
}

const Clientes: React.FC = () => {
  const navigate = useNavigate();
  const [activo, setActivo] = useState<boolean>(true);
  const [page, setPage] = useState(0);
  const [criteria, setCriteria] = useState('');

  const { isLoading, data  } = useQuery<PageType<ClienteType>, Error>({
    queryKey: ['clientes', page, criteria, activo],
    queryFn: () => clienteService.get(criteria, activo, page, PageSize, []),
    keepPreviousData: true,
    staleTime: 30000,
  });

  const handleEditClick = (id: GridRowId) => () => {
    navigate(`/clientes/edit/${id}`);
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

          //valueGetter: (params: GridValueGetterParams) => (params.row.birthday)
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
                icon={<ModeEditIcon />}
                label="Ver"
                className="textPrimary"
                onClick={handleEditClick(id)}
                color="inherit"
              />,
              <GridActionsCellItem
                icon={<DeleteIcon/>}
                label="Eliminar"
                onClick={handleDeleteClick(id)}
                color="inherit"
                disabled
              />,
            ];
          }
        }
      ] as GridColDef[];
  }, []);



  // if (error) return 'An error has occurred.';

  return (
    <div>
      <Title><PeopleAltTwoToneIcon sx={{ m: 2, mb: '-4px' }} /> Clientes <b>{!activo && 'Archivados'}</b></Title>

      <Box sx={{ height: 450, width: '100%', backgroundColor: 'white', '& .celldisabled': {textDecoration: 'line-through'} }}>
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
            toolbar: () => ClienteToolbar({criteria, setCriteria, activo, setActivo}),
          }}
          getCellClassName={(params: GridCellParams<ClienteType, any, number>) =>
            params.field==='name' && params.row.activo==false ? 'celldisabled' : ''
          }
        />

      </Box>
    </div>
  )
};

export default withAuth(Clientes);
