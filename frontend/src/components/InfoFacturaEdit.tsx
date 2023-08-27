import Grid from "@mui/material/Grid";
import TextField from "@mui/material/TextField";
import React from "react";
import {FieldErrors, UseFormRegister} from "react-hook-form";
import {FacturaType} from "../pages/FacturasPage/factura";
import Typography from "@mui/material/Typography";

const InfoFacturaEdit: React.FC<{
  register: UseFormRegister<FacturaType>;
  errors: FieldErrors<FacturaType>;
}> = ({ register, errors }) => {
  return (
    <>
      <Typography component="h1" variant="h5">
        Comprador
      </Typography>
      <Grid container spacing={2}>
        <Grid item xs={2}>
          <TextField
            margin="normal"
            fullWidth
            id="identificacionComprador"
            label="Identificacion"
            autoFocus
            error={errors.infoFactura?.identificacionComprador !== undefined}
            helperText={errors.infoFactura?.identificacionComprador !== undefined ? 'Required field' : ''}
            {...register("infoFactura.identificacionComprador", { required: true })}
          />
        </Grid>
        <Grid item xs={6}>
          <TextField
            margin="normal"
            fullWidth
            id="razonSocialComprador"
            label="Razon Social"
            error={errors.infoFactura?.razonSocialComprador !== undefined}
            helperText={errors.infoFactura?.razonSocialComprador !== undefined ? 'Required field' : ''}
            {...register("infoFactura.razonSocialComprador", { required: true })}
          />
        </Grid>
        <Grid item xs={4}>
          <TextField
            margin="normal"
            fullWidth
            id="direccionComprador"
            label="Direccion"
            error={errors.infoFactura?.direccionComprador !== undefined}
            helperText={errors.infoFactura?.direccionComprador !== undefined ? 'Required field' : ''}
            {...register("infoFactura.direccionComprador", { required: true })}
          />
        </Grid>
      </Grid>
    </>
  );
}

export default InfoFacturaEdit;
