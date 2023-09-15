import Toolbar from "@mui/material/Toolbar";
import IconButton from "@mui/material/IconButton";
import ChevronLeftIcon from "@mui/icons-material/ChevronLeft";
import Divider from "@mui/material/Divider";
import List from "@mui/material/List";

import Drawer from "../Drawer/Drawer.tsx";
import * as React from "react";
import {useNavigate} from "react-router-dom";
import {useAuth} from "../../../services/auth/useAuth.ts";
// import ReceiptOutlinedIcon from '@mui/icons-material/ReceiptOutlined';
import RequestQuoteOutlinedIcon from '@mui/icons-material/RequestQuoteOutlined';
import RequestQuoteIcon from '@mui/icons-material/RequestQuote';
// import AssignmentReturnedOutlinedIcon from '@mui/icons-material/AssignmentReturnedOutlined';
// import AssignmentReturnOutlinedIcon from '@mui/icons-material/AssignmentReturnOutlined';
import ListSubheader from "@mui/material/ListSubheader";
import PointOfSaleTwoToneIcon from "@mui/icons-material/PointOfSaleTwoTone";
import PeopleAltTwoToneIcon from "@mui/icons-material/PeopleAltTwoTone";
import ColorLensTwoToneIcon from "@mui/icons-material/ColorLensTwoTone";
import RuleFolderTwoToneIcon from "@mui/icons-material/RuleFolderTwoTone";
import ListItemIcon from "@mui/material/ListItemIcon";
import ListItemText from "@mui/material/ListItemText";
import ListItemButton from "@mui/material/ListItemButton";

const SideMenu: React.FC<{
    open: boolean;
    toggleDrawer: () => void;
}>  = ({ open, toggleDrawer }) => {
  const auth = useAuth();
  const navigate = useNavigate();

  const addMenu = (label: string, icon: React.ReactNode, path: string) => {
    return (
      <ListItemButton key={label} onClick={() => navigate(path)}>
        <ListItemIcon>{icon}</ListItemIcon>
        <ListItemText primary={label} />
      </ListItemButton>
    );
  }

  const mainListItems = [
    addMenu("Facturas", <RequestQuoteOutlinedIcon />, "/facturas"),
    addMenu("Retenciones", <RequestQuoteIcon />, "/facturas"),
    addMenu("Arqueo Caja", <PointOfSaleTwoToneIcon />, "/arqueo"),
    // addMenu("Notas Credito", <AssignmentReturnedOutlinedIcon />, "/notas-credito"),
    // addMenu("Notas Debito", <AssignmentReturnOutlinedIcon />, "/notas-debito"),
  ];
        // <SideMenuButton primary="Orders" icon={<ShoppingCartIcon />} />
        // <SideMenuButton primary="Customers" icon={<PeopleIcon />} />
        // <SideMenuButton primary="Reports" icon={<BarChartIcon />} />
        // <SideMenuButton primary="Integrations" icon={<LayersIcon />} />

  const secondaryListItems = [
    <ListSubheader key="Sistema" component="div" inset>Sistema</ListSubheader>,
      addMenu("Clientes", <PeopleAltTwoToneIcon />, "/clientes"),
      addMenu("Productos", <ColorLensTwoToneIcon />, "/productos"),
      addMenu("Administración", <RuleFolderTwoToneIcon />, "/admin")
  ];

    return (
        <Drawer variant="permanent" open={open}>
          <Toolbar
            sx={{
              display: 'flex',
              alignItems: 'center',
              justifyContent: 'flex-end',
              px: [1],
            }}
          >
            <IconButton onClick={toggleDrawer}><ChevronLeftIcon /></IconButton>
          </Toolbar>
          <Divider />
          {auth.user && (
            <List component="nav">
              {mainListItems}
              <Divider sx={{ my: 1 }} />
              {secondaryListItems}
            </List>
          )}
        </Drawer>
    );
};

export default SideMenu;
