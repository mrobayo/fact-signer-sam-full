import React, {useMemo, useState} from 'react';

import {useNavigate} from "react-router-dom";
import {
  DataGrid,
  GridActionsCellItem,
  GridColDef, GridPaginationModel,
  GridRowId,
  GridToolbarContainer,
  GridToolbarExport
} from "@mui/x-data-grid";
import IconButton from "@mui/material/IconButton";
import AddIcon from "@mui/icons-material/AddBoxRounded";
import {useQuery} from "@tanstack/react-query";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/DeleteOutlined";
import Box from "@mui/material/Box";
import RequestQuoteOutlinedIcon from '@mui/icons-material/RequestQuoteOutlined';

import withAuth from "../../services/auth/withAuth.tsx";
import {facturaService, type FacturaType} from "../../services";
import {Title} from "../../components/ui";
import {PageSize} from "../../constants.ts";
import {PageType} from "../../util";
import {useAuth} from "../../services/auth/useAuth.ts";

function FacturaToolbar() {
  const navigate = useNavigate();
  return (
    <GridToolbarContainer sx={{ justifyContent: 'flex-end' }}>
      <GridToolbarExport />
      <IconButton
        color="info"
        onClick={() => navigate('/facturas/new')}>
        <AddIcon />
      </IconButton>
      <IconButton
        color="info"
        onClick={() => navigate('/facturas/new-v1')}>
        <AddIcon />
      </IconButton>
    </GridToolbarContainer>
  );
}

const Facturas: React.FC = () => {
  const auth = useAuth();
  const {empresaId} = auth.puntoVenta ?? {};

  console.log('empresa: ', auth);

  const navigate = useNavigate();
  const [page, setPage] = useState(0);
  const { isLoading, data  } = useQuery<PageType<FacturaType>, Error>({
    queryKey: ['facturas', page],
    queryFn: () => facturaService.get(empresaId, page, PageSize, []),
    keepPreviousData: true,
    staleTime: 30000,
  });

  const handleEditClick = (id: GridRowId) => () => {
    navigate(`/facturas/edit/${id}`);
  };

  const handleDeleteClick = (id: GridRowId) => () => {
    console.log('del', data?.content?.filter((row) => row.id !== id));
  };

  const columns: GridColDef[] = useMemo(() => {
    return [
        {
          field: 'name',
          headerName: 'Numero',
          type: 'string',
          width: 160,
        },
        {
          field: 'estadoDoc',
          headerName: 'Estado',
          width: 110,
        },
        {
          field: 'compradorName',
          headerName: 'Cliente',
          flex: 1,
          minWidth: 200,
        },
        {
          field: 'observacion',
          headerName: 'Observacion',
          flex: 1,
          minWidth: 200,
        },
        {
          field: 'importeTotal',
          headerName: 'Total',
          sortable: false,
          width: 110,
        },
        {
          field: 'fechaEmision',
          headerName: 'Emision',
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
                disabled
              />,
            ];
          }
        }
      ] as GridColDef[];
  }, []);

  return (
    <div>
      <Title><RequestQuoteOutlinedIcon sx={{ m: 2, mb: '-4px' }} /> Facturas</Title>

      <Box sx={{ height: 450, width: '100%', backgroundColor: 'white' }}>
        <DataGrid
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
            toolbar: FacturaToolbar,
          }}
        />

      </Box>
    </div>
  )
};

export default withAuth(Facturas);
