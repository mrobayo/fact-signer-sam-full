import {useEffect, useRef} from "react";
import {
  useNavigate,
  useLocation,
} from "react-router-dom";
import {useAuth} from "../../services/auth/useAuth.ts";

import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';

import Link from '@mui/material/Link';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import {useForm} from "react-hook-form";
import {AuthFormType} from "../../services/auth/types.ts";
import Typography from "@mui/material/Typography";
import {Alert, Avatar} from "@mui/material";
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';

function LoginPage() {
  const navigate = useNavigate();
  const location = useLocation();
  const auth = useAuth();

  const {
      register,
      reset,
      handleSubmit,
      formState: { errors }
  } = useForm<AuthFormType>();

  const formRef = useRef<HTMLFormElement>(null);

  const from = location.state?.from?.pathname || "/";

  useEffect(() => {
    reset();
  }, [auth.user]);

  useEffect(() => {
    if (auth.error) {
      console.log('error', auth.error);
    }
  }, [auth.error]);

  const onFormSubmit = handleSubmit((data) => {

    auth.signin(data.username, data.password, () => {
      navigate(from, { replace: true });
    });
  })

  return (
    <Box sx={{
      marginTop: 8,
      border: 1,
      borderRadius: 2,
      borderColor: 'primary.main',
      padding: '20px',
      maxWidth: '360px',
      alignSelf: 'center',

    }}>
      <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
            <LockOutlinedIcon />
      </Avatar>
      <Typography component="h1" variant="h5">
        Sign in
      </Typography>
      <Box
        ref={formRef}
        component="form"
        onSubmit={onFormSubmit}
        noValidate
        sx={{ mt: 1 }}
        data-test-id="login-form"
      >
        <TextField
          margin="normal"
          fullWidth
          id="username"
          label="Usuario / Email"
          autoFocus
          error={errors.username !== undefined}
          helperText={errors.username !== undefined ? 'Required field' : ''}
          {...register("username", { required: true })}
        />
        <TextField
          margin="normal"
          fullWidth
          label="Clave"
          type="password"
          id="password"
          autoComplete="current-password"
          error={errors.password !== undefined}
          helperText={errors.password !== undefined ? 'Required field' : ''}
          {...register("password", { required: true })}
        />
        <Button
          type="submit"
          fullWidth
          variant="contained"
          sx={{ mt: 3, mb: 2 }}
        >
          Continuar
        </Button>
        <Grid container>
          <Grid item xs>
            <Link href="#" variant="body2">
              Olvido su clave?
            </Link>
          </Grid>
        </Grid>
        { auth.error && <Alert severity="error">{auth.error}</Alert> }
      </Box>
    </Box>
    // <div>
    //   <p>You must log in to view the page at {from}</p>
    //
    //   <form onSubmit={handleSubmit}>
    //     <label>
    //       Username: <input name="username" type="text" />
    //     </label>{" "}
    //     <button type="submit">Login</button>
    //   </form>
    // </div>
  );
}

export default LoginPage;
