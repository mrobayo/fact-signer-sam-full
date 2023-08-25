import React from "react";
import Box from "@mui/material/Box";
import AddIcon from "@mui/icons-material/Add";
import PageTitle from "../../components/ui/PageTitle/PageTitle.tsx";
import {css} from "@emotion/react";
import Button from "@mui/material/Button";
import TopToolbar from "../../components/ui/TopToolbar/TopToolbar.tsx";

const FacturasPage: React.FC = () => {
  const handleOpenNew = () => {

  };

//   const LikeButton = () => {
//     const record = useRecordContext();
//     const like = { postId: record.id };
//     const [create, { isLoading, error }] = useCreate();
//     const handleClick = () => {
//         create('likes', { data: like })
//     }
//     if (error) { return <p>ERROR</p>; }
//     return <button disabled={isLoading} onClick={handleClick}>Like</button>;
// };

  return (
    <Box data-test-id="FacturasPage" sx={{ width: '100%' }}>
      <PageTitle pageTitle="Facturas" actionLabel={"Nueva"} actionIcon={<AddIcon />} onActionClick={handleOpenNew} />

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

export default FacturasPage;
