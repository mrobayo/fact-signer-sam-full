import React, {useEffect, useState, useRef} from "react";
import {
  useNavigate,
  useLocation,
} from "react-router-dom";
import {useAuth} from "../../services/auth/useAuth.ts";

import Button from '@mui/material/Button';
import IconButton from '@mui/material/IconButton';
import TextField from '@mui/material/TextField';

import OutlinedInput from '@mui/material/OutlinedInput';
import InputLabel from '@mui/material/InputLabel';
import FormControl from '@mui/material/FormControl';

import Link from '@mui/material/Link';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import {useForm} from "react-hook-form";
import {AuthFormType} from "../../services/auth/types.ts";
import InputAdornment from '@mui/material/InputAdornment';
import Typography from "@mui/material/Typography";
import Alert from "@mui/material/Alert";
import Avatar from "@mui/material/Avatar";
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Visibility from '@mui/icons-material/Visibility';
import VisibilityOff from '@mui/icons-material/VisibilityOff';

function LoginPage() {
  const navigate = useNavigate();
  const location = useLocation();
  const auth = useAuth();
  const [showPassword, setShowPassword] = useState(false);
  const handleClickShowPassword = () => setShowPassword((show) => !show);
  const handleMouseDownPassword = (event: React.MouseEvent<HTMLButtonElement>) => event.preventDefault();

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

  const onFormSubmit = handleSubmit((data) => {

    auth.signin(data.username, data.password, data.empresaId, () => {
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

        {/*<TextField*/}
        {/*  margin="normal"*/}
        {/*  fullWidth*/}
        {/*  label="Clave"*/}
        {/*  type={showPassword ? 'text' : 'password'}*/}
        {/*  id="password"*/}
        {/*  autoComplete="current-password"*/}
        {/*  error={errors.password !== undefined}*/}
        {/*  helperText={errors.password !== undefined ? 'Required field' : ''}*/}
        {/*  endAdornment={*/}
        {/*      <InputAdornment position="end">*/}
        {/*        <IconButton*/}
        {/*          aria-label="toggle password visibility"*/}
        {/*          onClick={handleClickShowPassword}*/}
        {/*          onMouseDown={handleMouseDownPassword}*/}
        {/*          edge="end"*/}
        {/*        >*/}
        {/*          fsdf*/}
        {/*          {showPassword ? <VisibilityOff /> : <Visibility />}*/}
        {/*        </IconButton>*/}
        {/*      </InputAdornment>*/}
        {/*  }*/}
        {/*  {...register("password", { required: true })}*/}
        {/*/>*/}

        <FormControl
          variant="outlined"
          fullWidth
          error={errors.password !== undefined}
        >
          <InputLabel htmlFor="outlined-password">Password</InputLabel>
          <OutlinedInput
            defaultValue="Secret#011235"
            id="outlined-password"
            type={showPassword ? 'text' : 'password'}
            endAdornment={
              <InputAdornment position="end">
                <IconButton
                  aria-label="toggle password visibility"
                  onClick={handleClickShowPassword}
                  onMouseDown={handleMouseDownPassword}
                  edge="end"
                >
                  {showPassword ? <VisibilityOff /> : <Visibility />}
                </IconButton>
              </InputAdornment>
            }
            label="Password"
            {...register("password", { required: true })}
          />
        </FormControl>

        <TextField
          margin="normal"
          fullWidth
          label="Empresa"
          id="empresaId"
          error={errors.empresaId !== undefined}
          helperText={errors?.empresaId?.message}
          {...register("empresaId", { required: true, minLength: 13, maxLength: 13 })}
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
  );
}

export default LoginPage;
