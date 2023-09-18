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
import ColorLensTwoToneIcon from "@mui/icons-material/ColorLensTwoTone";

import withAuth from "../../services/auth/withAuth.tsx";
import {productoService, type ProductoType} from "../../services";
import {Title} from "../../components/ui";
import {PageSize} from "../../constants.ts";
import {PageType} from "../../util";
import {useAuth} from "../../services/auth/useAuth.ts";

function ProductoToolbar() {
  const navigate = useNavigate();
  return (
    <GridToolbarContainer sx={{ justifyContent: 'flex-end' }}>
      <GridToolbarExport />
      <span><IconButton
        color="info"
        onClick={() => navigate('/productos/new')}>
        <AddIcon />
      </IconButton></span>
    </GridToolbarContainer>
  );
}

const Productos: React.FC = () => {
  const auth = useAuth();
  const {empresaId} = auth.puntoVenta ?? {};

  // console.log('empresa: ', auth);

  const navigate = useNavigate();
  const [page, setPage] = useState(0);
  const { isLoading, data  } = useQuery<PageType<ProductoType>, Error>({
    queryKey: ['productos', page],
    queryFn: () => productoService.get(empresaId, page, PageSize, []),
    keepPreviousData: true,
    staleTime: 30000,
  });

  const handleEditClick = (id: GridRowId) => () => {
    navigate(`/productos/edit/${id}`);
  };

  const handleDeleteClick = (id: GridRowId) => () => {
    console.log('del', data?.content?.filter((row) => row.id !== id));
  };

  const columns: GridColDef[] = useMemo(() => {
    return [
        {
          field: 'codigo',
          headerName: 'Codigo',
          type: 'string',
          width: 110,
        },
        {
          field: 'name',
          headerName: 'Producto',
          flex: 1,
          minWidth: 200,
        },
        {
          field: 'tipo',
          headerName: 'Tipo',
          width: 60,
        },
        {
          field: 'codigoIva',
          headerName: 'IVA',
          sortable: false,
          width: 160,
        },
        {
          field: 'unidadVentaId',
          headerName: 'Unidad Venta',
          sortable: false,
          width: 110,
        },
        {
          field: 'categoriaId',
          headerName: 'Categoria',
          sortable: false,
          width: 110,
        },
        {
          field: 'control',
          headerName: 'Control',
          sortable: false,
          width: 40,
        },
        {
          field: 'vendido',
          headerName: 'V.',
          sortable: false,
          width: 40,
        },
        {
          field: 'comprado',
          headerName: 'C.',
          sortable: false,
          width: 40,
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
      <Title><ColorLensTwoToneIcon sx={{ m: 2, mb: '-4px' }} /> Productos</Title>

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
            toolbar: ProductoToolbar,
          }}
        />

      </Box>
    </div>
  )
};


export default withAuth(Productos);
