import Toolbar from "@mui/material/Toolbar";
import IconButton from "@mui/material/IconButton";
import MenuIcon from "@mui/icons-material/Menu";
import BugReportIcon from '@mui/icons-material/BugReportTwoTone';
import Typography from "@mui/material/Typography";
import Badge from "@mui/material/Badge";
import NotificationsIcon from "@mui/icons-material/Notifications";
import AppBar from "../AppBar/AppBar.tsx";
import * as React from "react";
import Button from "@mui/material/Button";
import {useAuth} from "../../../services/auth/useAuth.ts";
import {useNavigate} from "react-router-dom";
import PopupState from "material-ui-popup-state";
import Menu from '@mui/material/Menu'
import MenuItem from '@mui/material/MenuItem'
import AccountCircle from '@mui/icons-material/AccountCircle';
import Tooltip from '@mui/material/Tooltip';

import {
  bindTrigger,
  bindMenu,
} from 'material-ui-popup-state/hooks';
import ArrowDropDownIcon from '@mui/icons-material/ArrowDropDown';


const MainBar: React.FC<{
    open: boolean;
    toggleDrawer: () => void;
}> = ({ open, toggleDrawer }) => {
  const auth = useAuth();
  const navigate = useNavigate();
  const appTitle = import.meta.env.VITE_APP_TITLE;
  const { puntoVenta} = auth;
  const { empresa } = puntoVenta ?? {};

  return (
      <AppBar position="absolute" open={open} sx={ empresa?.color ? { backgroundColor: empresa?.color }: {}}>
        <Toolbar
          sx={{
            pr: '24px', // keep right padding when drawer closed
          }}
        >
          <IconButton
            edge="start"
            color="inherit"
            aria-label="open drawer"
            onClick={toggleDrawer}
            disabled={!auth.user}
            sx={{
              marginRight: '36px',
              ...(open && { display: 'none' }),
            }}
          >
            <MenuIcon />
          </IconButton>

          <Typography
            component="h1"
            variant="h6"
            color="inherit"
            noWrap
            sx={{ flexGrow: 1 }}
          >
              {empresa?.name ?? appTitle}
          </Typography>

          {empresa?.ambiente &&
            <Tooltip title={`Ambiente ${empresa?.ambiente}`}>
              <BugReportIcon color="disabled" fontSize="large" sx={{ mx: 2 }} />
            </Tooltip>
          }

          {puntoVenta?.ptoEmi &&
            <Tooltip title="Punto de Venta">
              <span>{puntoVenta?.estab}-{puntoVenta?.ptoEmi}</span>
            </Tooltip>
          }

          {auth.user &&
            <Tooltip title={`Notificationes`}>
              <IconButton color="inherit" sx={{ mx: 1 }}>
                <Badge badgeContent={0} color="secondary"><NotificationsIcon /></Badge>
              </IconButton>
            </Tooltip>
          }

          {!auth.user &&
            <Button
              onClick={() => { navigate("/login"); }}
              href="#"
              variant="contained"
              color="secondary"
              sx={{ my: 1, mx: 1.5 }}
            >
              Login
            </Button>
          }

          {auth.user &&
            (<>
              <PopupState variant="popover" popupId="main-session-popup-menu">
                {(popupState) => (
                  <React.Fragment>

                    <Button
                      variant="text"
                      color="inherit"
                      {...bindTrigger(popupState)}
                      startIcon={<AccountCircle />}
                      endIcon={<ArrowDropDownIcon/>}
                    >
                       {auth.user?.login}
                    </Button>

                    <Menu {...bindMenu(popupState)}>
                      <MenuItem disabled>{auth.user?.roles?.join(', ')}</MenuItem>
                      <MenuItem onClick={() => {
                        popupState.close();
                        auth.signout(() => navigate("/"));
                      }}>Logout</MenuItem>
                    </Menu>
                  </React.Fragment>
                )}
              </PopupState>
            </>)
          }
        </Toolbar>
      </AppBar>
  );
}

export default MainBar;
