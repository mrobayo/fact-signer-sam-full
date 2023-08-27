import {styled} from "@mui/material/styles";
import Table from "@mui/material/Table";
import TableCell from "@mui/material/TableCell";

export const editFacturaCss = {
    marginTop: 2,
    border: 1,
    borderRadius: 2,
    borderColor: 'primary.main',
    padding: '20px',
    width: '100%',
    alignSelf: 'center',
};

export const DetailCell = styled(TableCell)({
  padding: '12px',
});

export const DetailTable = styled(Table)({
   '& .MuiTextField-root': {
       marginTop: 0,
       marginBottom: 0,
   }
});

//export const DetailCell = TableCell;
