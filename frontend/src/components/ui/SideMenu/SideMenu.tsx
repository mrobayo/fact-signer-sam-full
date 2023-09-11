import Toolbar from "@mui/material/Toolbar";
import IconButton from "@mui/material/IconButton";
import ChevronLeftIcon from "@mui/icons-material/ChevronLeft";
import Divider from "@mui/material/Divider";
import List from "@mui/material/List";
import {secondaryListItems} from "./listItems.tsx";
import Drawer from "../Drawer/Drawer.tsx";
import * as React from "react";
import {useNavigate} from "react-router-dom";
import SideMenuButton from "./SideMenuButton/SideMenuButton.tsx";
import {useAuth} from "../../../services/auth/useAuth.ts";
import ReceiptOutlinedIcon from '@mui/icons-material/ReceiptOutlined';
import RequestQuoteOutlinedIcon from '@mui/icons-material/RequestQuoteOutlined';
import AssignmentReturnedOutlinedIcon from '@mui/icons-material/AssignmentReturnedOutlined';
import AssignmentReturnOutlinedIcon from '@mui/icons-material/AssignmentReturnOutlined';

const SideMenu: React.FC<{
    open: boolean;
    toggleDrawer: () => void;
}>  = ({ open, toggleDrawer }) => {
  const auth = useAuth();
  const navigate = useNavigate();

  const mainListItems = <>
        <SideMenuButton primary="Facturas" icon={<ReceiptOutlinedIcon />} onClick={() => {
          navigate("/facturas");
        }} />
        <SideMenuButton primary="Retenciones" icon={<RequestQuoteOutlinedIcon />} onClick={() => {
          navigate("/retenciones");
        }} />
        <SideMenuButton primary="Notas Credito" icon={<AssignmentReturnedOutlinedIcon />} onClick={() => {
          navigate("/notas-credito");
        }} />
      <SideMenuButton primary="Notas Debito" icon={<AssignmentReturnOutlinedIcon />} onClick={() => {
          navigate("/notas-debito");
        }} />
        {/*<SideMenuButton primary="Orders" icon={<ShoppingCartIcon />} />*/}
        {/*<SideMenuButton primary="Customers" icon={<PeopleIcon />} />*/}
        {/*<SideMenuButton primary="Reports" icon={<BarChartIcon />} />*/}
        {/*<SideMenuButton primary="Integrations" icon={<LayersIcon />} />*/}
    </>;

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
            <IconButton onClick={toggleDrawer}>
              <ChevronLeftIcon />
            </IconButton>
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
