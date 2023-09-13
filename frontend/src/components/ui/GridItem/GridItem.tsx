import {GridProps} from "@mui/material/Grid/Grid";
import React from "react";
import Grid from "@mui/material/Grid";

export function GridItem({ children, ...restProps }: GridProps): React.ReactNode {
  return <Grid item xs={12} {...restProps}>{children}</Grid>;
}