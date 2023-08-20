import React from "react";
import GlobalStyles from "@mui/material/GlobalStyles";
import CssBaseline from "@mui/material/CssBaseline";
import BasicNav from "./BasicNav/BasicNav.tsx";
import {createTheme, ThemeProvider} from "@mui/material/styles";
import Container from "@mui/material/Container";

import GlobalFooter from "./BasicFooter/BasicFooter.tsx";

const defaultTheme = createTheme();

const BasicLayout: React.FC<{ children: React.ReactNode }> = ({ children }) => {
    return (
        <ThemeProvider theme={defaultTheme}>
          <GlobalStyles
              styles={{ ul: { margin: 0, padding: 0, listStyle: 'none' } }}
          />
          <CssBaseline />
          <BasicNav />
          {/* Hero unit */}
          <Container disableGutters maxWidth="sm" component="main" sx={{ pt: 8, pb: 6 }}>
              {children}
          </Container>
          <Container
            maxWidth="md"
            component="footer"
            sx={{
              borderTop: (theme) => `1px solid ${theme.palette.divider}`,
              mt: 8,
              py: [3, 6],
            }}
          >
              <GlobalFooter />
          </Container>
        </ThemeProvider>
    );
}

export default BasicLayout;
