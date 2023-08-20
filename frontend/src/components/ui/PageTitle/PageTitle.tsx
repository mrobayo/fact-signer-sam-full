import Button from "@mui/material/Button";
import {css} from "@emotion/react";

import Typography from "@mui/material/Typography";
import React from "react";

interface PageTitleProps {
  pageTitle: string;
  actionIcon: React.ReactNode;
  actionLabel: string;
  onActionClick: () => void;
}

const PageTitle: React.FC<PageTitleProps> = (
  {pageTitle, actionLabel, actionIcon, onActionClick }
) => {
  return (
    <Typography variant="h4" component="h1">
      {pageTitle}
        <Button
            variant="contained"
            onClick={onActionClick}
            css={css`float: right;`}
            startIcon={actionIcon}
        >
          {actionLabel}
        </Button>
      </Typography>
  );
};

export default PageTitle;
