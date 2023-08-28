import { alpha } from '@mui/material/styles';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';

import IconButton from '@mui/material/IconButton';
import Tooltip from '@mui/material/Tooltip';

import DeleteIcon from '@mui/icons-material/Delete';
import AddBoxIcon from "@mui/icons-material/AddBox";
import Button from "@mui/material/Button";
// import {UseFieldArrayAppend} from "react-hook-form";
// import {FacturaType} from "../factura";

interface EnhancedTableToolbarProps {
  numSelected: number;
  appendNew: () => void;
  removeSelected: () => void;
}

function DetalleToolbar(props: EnhancedTableToolbarProps) {
  const { numSelected, appendNew, removeSelected } = props;

  return (
    <Toolbar
      sx={{
        pl: { sm: 2 },
        pr: { xs: 1, sm: 1 },
        ...(numSelected > 0 && {
          bgcolor: (theme) =>
            alpha(theme.palette.primary.main, theme.palette.action.activatedOpacity),
        }),
      }}
    >
      {numSelected > 0 ? (
        <Typography
          sx={{ flex: '1 1 100%' }}
          color="inherit"
          variant="subtitle1"
          component="div"
        >
          {numSelected} fila seleccionada(s)
        </Typography>
      )
        : (
        <Typography
          sx={{ flex: '1 1 100%' }}
          variant="h6"
          id="tableTitle"
          component="div"
        >
          Detalle de Venta
        </Typography>
      )
      }
      {numSelected > 0 && (
        <Tooltip title="Delete">
          <IconButton onClick={removeSelected}>
            <DeleteIcon />
          </IconButton>
        </Tooltip>
      )}
      {numSelected === -1 && (
        <Button
          type="button"
          onClick={appendNew}
          startIcon={<AddBoxIcon />}
        >
          Añadir
        </Button>
      )}
    </Toolbar>
  );
}

export default DetalleToolbar;
