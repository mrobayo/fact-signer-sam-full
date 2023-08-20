import * as React from 'react';
import ListSubheader from '@mui/material/ListSubheader';
import DashboardIcon from '@mui/icons-material/Dashboard';
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import PeopleIcon from '@mui/icons-material/People';
import BarChartIcon from '@mui/icons-material/BarChart';
import LayersIcon from '@mui/icons-material/Layers';
import AssignmentIcon from '@mui/icons-material/Assignment';
import SideMenuButton from "./SideMenuButton/SideMenuButton.tsx";

export const mainListItems = (
    <React.Fragment>
        <SideMenuButton primary="Facturas" icon={<DashboardIcon />} />
        <SideMenuButton primary="Orders" icon={<ShoppingCartIcon />} />
        <SideMenuButton primary="Customers" icon={<PeopleIcon />} />
        <SideMenuButton primary="Reports" icon={<BarChartIcon />} />
        <SideMenuButton primary="Integrations" icon={<LayersIcon />} />
    </React.Fragment>
);

export const secondaryListItems = (
  <React.Fragment>
      <ListSubheader component="div" inset>Settings...</ListSubheader>
      <SideMenuButton primary="Current month" icon={<AssignmentIcon />} />
      <SideMenuButton primary="Last quarter" icon={<AssignmentIcon />} />
      <SideMenuButton primary="Year-end sale" icon={<AssignmentIcon />} />
  </React.Fragment>
);
