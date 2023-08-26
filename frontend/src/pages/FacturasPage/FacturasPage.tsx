import React from "react";
import Box from "@mui/material/Box";
import AddIcon from "@mui/icons-material/Add";
import PageTitle from "../../components/ui/PageTitle/PageTitle.tsx";
import {css} from "@emotion/react";
import Button from "@mui/material/Button";
import TopToolbar from "../../components/ui/TopToolbar/TopToolbar.tsx";
import {useNavigate} from "react-router-dom";
import withAuth from "../../services/auth/withAuth.tsx";

const FacturasPage: React.FC = () => {
  const navigate = useNavigate();

  const handleOpenNew = () => {
    navigate("/facturas/new");
  };

  return (
    <Box data-test-id="FacturasPage" sx={{ width: '100%' }}>
      <PageTitle
        pageTitle="Facturas"
        actionLabel={"Nueva"}
        actionIcon={<AddIcon />}
        onActionClick={handleOpenNew}
      />
        <TopToolbar>
          <Button
            variant="contained"
            onClick={handleOpenNew}
            css={css`float: right;`}
            startIcon={<AddIcon />}
        >
          Button X
        </Button>
      </TopToolbar>


    </Box>
  );
};

export default withAuth(FacturasPage);
