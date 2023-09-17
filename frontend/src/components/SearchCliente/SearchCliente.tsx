import React, {useEffect, useMemo, useState} from 'react';
import Box from "@mui/material/Box";
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import SearchIcon from '@mui/icons-material/Search';
import {
  DataGrid,
  GridColDef,
  GridPaginationModel,
  GridRowId,
  GridRowSelectionModel
} from "@mui/x-data-grid";
import {PageSize} from "../../constants";
import TopToolbar from "../ui/TopToolbar/TopToolbar";
import DebouncedInput from "../ui/DebouncedInput/DebouncedInput";
import {ClienteType, useGetClientes} from "../../services";

interface SearchClienteProps {
    onSelect: (cliente?: ClienteType) => void;
    isVisible: boolean;
    setVisible: (visible: boolean) => void;
}

const SearchCliente: React.FC<SearchClienteProps> = (
    { onSelect, isVisible, setVisible }
) => {
  const [criteria, setCriteria] = useState('');
  const [page, setPage] = useState(0);
  const {isLoading, data} = useGetClientes(criteria, true, page);
  const [selectedId, setSelectedId] = useState<GridRowId>();

  useEffect(() => {
    setSelectedId(undefined);
  }, [isVisible]);

  const handleSelect = () => {
    const cliente = data?.content?.find(row => row.id === selectedId);
    onSelect(cliente);
    setVisible(false);
  }

  const handleOnRowSelection = (rowSelectionModel: GridRowSelectionModel) => {
    setSelectedId(rowSelectionModel[0]);
  }

  const handleClose = () => setVisible(false);

  const columns: GridColDef[] = useMemo(() => {
    return [
      {field: 'tipo', headerName: 'Tipo', width: 60},
      {field: 'identidad', headerName: 'Ident.', width: 120},
      {field: 'name', headerName: 'Nombres', flex: 1, minWidth: 200},
      ] as GridColDef[];
  }, []);

  return (
      <Dialog
        fullWidth
        open={isVisible}
        onClose={handleClose}
        aria-labelledby="cliente-dialog-title"
        aria-describedby="cliente-dialog-description"
      >
        <DialogTitle id="cliente-dialog-title">
          <SearchIcon sx={{ m: 1, mb: '-6px' }} /> Buscar Cliente
        </DialogTitle>
        <DialogContent>
            <TopToolbar>
              <DebouncedInput value={criteria} setValue={setCriteria} />
            </TopToolbar>
            <Box sx={{ height: 450, width: '100%' }}>
              <DataGrid
                rows={data?.content ?? []}
                columns={columns}
                initialState={{
                  pagination: { paginationModel: { pageSize: PageSize } },
                }}
                onPaginationModelChange={(model: GridPaginationModel) => setPage(model.page)}
                onRowSelectionModelChange={handleOnRowSelection}
                paginationModel={{ page, pageSize: PageSize}}
                paginationMode="server"
                pageSizeOptions={[PageSize]}
                rowCount={data?.totalElements ?? 0}
                loading={isLoading}
              />
            </Box>
        </DialogContent>
        <DialogActions sx={{ justifyContent: "space-between", m: 2, mt: 0 }}>
            <Button color="secondary" onClick={handleClose} aria-label="Cancel">Cancel</Button>
            <Button
              variant="contained"
              color="primary"
              onClick={handleSelect}
              disabled={selectedId === undefined}
              aria-label="Seleccionar">
              Seleccionar
            </Button>
        </DialogActions>
      </Dialog>
  );
}

export default SearchCliente;
