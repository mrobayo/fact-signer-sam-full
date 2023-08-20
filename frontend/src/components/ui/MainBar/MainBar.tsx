import Toolbar from "@mui/material/Toolbar";
import IconButton from "@mui/material/IconButton";
import MenuIcon from "@mui/icons-material/Menu";
import Typography from "@mui/material/Typography";
import Badge from "@mui/material/Badge";
import NotificationsIcon from "@mui/icons-material/Notifications";
import AppBar from "../AppBar/AppBar.tsx";
import * as React from "react";
import Button from "@mui/material/Button";

const MainBar: React.FC<{
    open: boolean;
    toggleDrawer: () => void;
}> = ({ open, toggleDrawer }) => {

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
              <Badge badgeContent={7} color="secondary">
                <NotificationsIcon />
              </Badge>
            </IconButton>

            <Button
                onClick={() => { console.log("login..."); }}
                href="#"
                variant="contained"
                color="secondary"
                sx={{ my: 1, mx: 1.5 }}
            >
              Login
            </Button>
          </Toolbar>
        </AppBar>
    );
}

export default MainBar;
