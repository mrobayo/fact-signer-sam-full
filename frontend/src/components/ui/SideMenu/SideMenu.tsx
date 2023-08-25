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
import DashboardIcon from "@mui/icons-material/Dashboard";
import {useAuth} from "../../../services/auth/useAuth.ts";

const SideMenu: React.FC<{
    open: boolean;
    toggleDrawer: () => void;
}>  = ({ open, toggleDrawer }) => {
  const auth = useAuth();
  const navigate = useNavigate();

  const mainListItems = <>
        <SideMenuButton primary="Facturas" icon={<DashboardIcon />} onClick={() => {
          navigate("/protected");
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
