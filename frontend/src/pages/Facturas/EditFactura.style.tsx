import {styled} from "@mui/material/styles";
import TableCell from "@mui/material/TableCell";
import Table from "@mui/material/Table";

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

export const DetailToolCell = styled(TableCell)({
  padding: '12px 4px',
  border: 'none',
});

export const DetailSummaryCell = styled(TableCell)({
  fontSize: 'large'
});

export const DetailTable = styled(Table)({
   '& .MuiTextField-root': {
       marginTop: 0,
       marginBottom: 0,
   }
});
