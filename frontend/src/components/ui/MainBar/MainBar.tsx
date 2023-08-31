import Toolbar from "@mui/material/Toolbar";
import IconButton from "@mui/material/IconButton";
import MenuIcon from "@mui/icons-material/Menu";
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

  return (
      <AppBar position="absolute" open={open}>
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
              {appTitle}
          </Typography>
          <IconButton color="inherit">
            <Badge badgeContent={0} color="secondary">
              <NotificationsIcon />
            </Badge>
          </IconButton>
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
                      color="secondary" {...bindTrigger(popupState)}
                      endIcon={<ArrowDropDownIcon/>}
                    >
                      {auth.user}
                    </Button>
                    <Menu {...bindMenu(popupState)}>
                      {/*<MenuItem onClick={popupState.close}>Profile</MenuItem>*/}
                      {/*<MenuItem onClick={popupState.close}>My account</MenuItem>*/}
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
