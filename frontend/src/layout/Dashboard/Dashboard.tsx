import * as React from 'react';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';

import Container from '@mui/material/Container';
import Grid from '@mui/material/Grid';

import Copyright from "../../components/ui/Copyright/Copyright.tsx";

import MainBar from "../../components/ui/MainBar/MainBar.tsx";
import SideMenu from "../../components/ui/SideMenu/SideMenu.tsx";

// TODO remove, this demo shouldn't need to reset the theme.
const defaultTheme = createTheme();

const Dashboard: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [open, setOpen] = React.useState(true);
  const toggleDrawer = () => {
    setOpen(!open);
  };

  return (
    <ThemeProvider theme={defaultTheme}>
      <Box sx={{ display: 'flex' }}>
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
            <Grid container spacing={3}>

                {children}
              {/*<Grid item xs={12} md={8} lg={9}>*/}
              {/*  <Paper*/}
              {/*    sx={{*/}
              {/*      p: 2,*/}
              {/*      display: 'flex',*/}
              {/*      flexDirection: 'column',*/}
              {/*      height: 240,*/}
              {/*    }}*/}
              {/*  >*/}
              {/*    <Chart />*/}
              {/*  </Paper>*/}
              {/*</Grid>*/}

              {/*<Grid item xs={12} md={4} lg={3}>*/}
              {/*  <Paper*/}
              {/*    sx={{*/}
              {/*      p: 2,*/}
              {/*      display: 'flex',*/}
              {/*      flexDirection: 'column',*/}
              {/*      height: 240,*/}
              {/*    }}*/}
              {/*  >*/}
              {/*    <Deposits />*/}
              {/*  </Paper>*/}
              {/*</Grid>*/}

              {/*<Grid item xs={12}>*/}
              {/*  <Paper sx={{ p: 2, display: 'flex', flexDirection: 'column' }}>*/}
              {/*    <Orders />*/}
              {/*  </Paper>*/}
              {/*</Grid>*/}

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
    </ThemeProvider>
  );
}

export default Dashboard;
