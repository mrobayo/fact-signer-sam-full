import * as React from 'react';
import Typography from '@mui/material/Typography';
import {OverridableComponent} from "@mui/material/OverridableComponent";
import {SvgIconTypeMap} from "@mui/material/SvgIcon/SvgIcon";


interface TitleProps {
  disabled?: boolean;
  Icon: OverridableComponent<SvgIconTypeMap>;
  children?: React.ReactNode;
}

export function ToolbarTitle({ children, disabled, Icon }: TitleProps) {
  return (
    <div style={{ flexGrow: 1 }}>
      <Typography component="h2" variant="h6" color="primary" gutterBottom sx={{ textDecoration: disabled ? 'line-through' : 'none' }}>
        <Icon sx={{ m: 2, mb: '-4px' }} />
        {children}
      </Typography>
    </div>
  );
}
