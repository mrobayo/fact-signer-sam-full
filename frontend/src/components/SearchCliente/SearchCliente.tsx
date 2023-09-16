import * as React from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';

import DialogTitle from '@mui/material/DialogTitle';
import {ClienteType} from "../../services";
import SearchIcon from '@mui/icons-material/Search';
import TopToolbar from "../ui/TopToolbar/TopToolbar.tsx";

import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import OutlinedInput from '@mui/material/OutlinedInput';
import {useEffect, useRef} from "react";

interface SearchClienteProps {
    cliente?: ClienteType;
    onSelect: (cliente?: ClienteType) => void;
    isVisible: boolean;
    setVisible: (visible: boolean) => void;
}

function createData(
  name: string,
  calories: number,
  fat: number,
  carbs: number,
  protein: number,
) {
  return { name, calories, fat, carbs, protein };
}

const rows = [
  createData('Frozen yoghurt', 159, 6.0, 24, 4.0),
  createData('Ice cream sandwich', 237, 9.0, 37, 4.3),
  createData('Eclair', 262, 16.0, 24, 6.0),
  createData('Cupcake', 305, 3.7, 67, 4.3),
  createData('Gingerbread', 356, 16.0, 49, 3.9),
];

const SearchCliente: React.FC<SearchClienteProps> = (
    { cliente, onSelect, isVisible, setVisible }
) => {
  const inputRef = useRef<HTMLInputElement>(null);

  useEffect(() => {
    setTimeout(() => {
      if (inputRef.current) {
        inputRef.current.focus();
      }
    }, 1000);
  }, [isVisible]);

  const handleSelect = () => {
      if (cliente !== undefined) {
          onSelect(cliente);
      }
      setVisible(false);
  }

  const handleClose = () => {
    setVisible(false);
  };

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
              <OutlinedInput ref={inputRef} sx={{ flexGrow: 1 }} placeholder="Buscar por Identificacion/Nombre..."/>
            </TopToolbar>
            <TableContainer component={Paper}>
              <Table aria-label="Resultados">
                <TableHead>
                  <TableRow>
                    <TableCell align="center" colSpan={2}>Identificacion</TableCell>
                    <TableCell sx={{ flexGrow: 1, minWidth: '70%' }}>Razon Social / Nombre Completo</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {rows.map((row) => (
                    <TableRow key={row.name} sx={{ '&:last-child td, &:last-child th': { border: 0 } }}>
                      <TableCell align="right">{row.calories}</TableCell>
                      <TableCell>{row.fat}</TableCell>
                      <TableCell sx={{ flexGrow: 1 }}>{row.name}</TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </TableContainer>
        </DialogContent>
        <DialogActions sx={{ justifyContent: "space-between", m: 2, mt: 0 }}>
            <Button color="secondary" onClick={handleClose} aria-label="Cancel">Cancel</Button>
            <Button variant="contained" color="primary" onClick={handleSelect} autoFocus aria-label="Seleccionar">Seleccionar</Button>
        </DialogActions>
      </Dialog>
  );
}

export default SearchCliente;
