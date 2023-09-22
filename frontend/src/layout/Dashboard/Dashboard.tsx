import * as React from 'react';
import { createTheme, ThemeProvider } from '@mui/material/styles';

import CssBaseline from '@mui/material/CssBaseline';
import GlobalStyles from "@mui/material/GlobalStyles";
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Alert from "@mui/material/Alert";
import Snackbar from '@mui/material/Snackbar';

import Container from '@mui/material/Container';
import Grid from '@mui/material/Grid';
import {red, grey} from "@mui/material/colors";

import Copyright from "../../components/ui/Copyright/Copyright.tsx";

import MainBar from "../../components/ui/MainBar/MainBar.tsx";
import SideMenu from "../../components/ui/SideMenu/SideMenu.tsx";
import {useAuth} from "../../services/auth/useAuth.ts";
import {useEffect} from "react";
import {useNotificationContext} from "../../notification";

// TODO remove, this demo shouldn't need to reset the theme.
const defaultTheme = createTheme({
  palette: {
    primary: {
      main: '#3368b7',
    },
    secondary: {
       main: grey["900"],
    },
    error: {
      main: red.A400,
    },
  },
});

const Dashboard: React.FC<{ children?: React.ReactNode }> = ({ children }) => {
  const auth = useAuth();
  const { notifications, takeNotification } = useNotificationContext();
  const [open, setOpen] = React.useState(false);
  const toggleDrawer = () => {
    setOpen(!open);
  };

  useEffect(() => {
    if (!open && auth.user) {
      toggleDrawer();
    }
    if (open && !auth.user) {
      toggleDrawer();
    }
  }, [auth.user]);

  return (
    <ThemeProvider theme={defaultTheme}>
      <Box sx={{ display: 'flex' }}>
        <GlobalStyles
          styles={{
            '&.MuiInputLabel-root.MuiInputLabel-shrink.MuiInputLabel-outlined.Mui-disabled': {
              //'-webkit-text-fill-color': 'rgb(125, 102, 95, 0.8)!important'
              '-webkit-text-fill-color': 'rgb(70, 90, 100, 0.7)!important'
            },
            '&.MuiInputBase-root>.Mui-disabled': {
              '-webkit-text-fill-color': 'rgba(0, 0, 0, 0.6)!important'
            },
          }}
        />
        <CssBaseline />
        <MainBar open={open} toggleDrawer={toggleDrawer} />
        <SideMenu open={open} toggleDrawer={toggleDrawer} />
        <Box
          component="main"
          sx={{
            backgroundColor: (theme) =>
              theme.palette.mode === 'light'
                ? theme.palette.grey[100]
                : theme.palette.grey[900],
            flexGrow: 1,
            height: '100vh',
            overflow: 'auto',
          }}
        >
          <Toolbar />
          <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
            <Grid
              container
              sx={{ flexDirection: 'column' }}
              spacing={3}
              data-test-id="dashboard-body"
            >
                {children}
            </Grid>
              <Container
                maxWidth="md"
                component="footer"
                sx={{
                  borderTop: (theme) => `1px solid ${theme.palette.divider}`,
                  mt: 8,
                  py: [3, 6],
                }}
              >
                <Copyright sx={{ pt: 4 }} />
              </Container>
          </Container>
        </Box>
      </Box>
      {notifications.map((notification, index) => (
        <Snackbar
          open={true}
          autoHideDuration={6000}
          anchorOrigin={{ vertical: 'top', horizontal: 'right' }}
          onClose={() => {takeNotification()}}
        >
          <Alert
            onClose={() => {takeNotification()}}
            key={`notification-${index}`}
            severity={notification.type}
            color={notification.type}>
            {notification.message}
          </Alert>
        </Snackbar>
      ))}
    </ThemeProvider>
  );
}

export default Dashboard;
