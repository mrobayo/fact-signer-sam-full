import React, {useEffect, useRef} from 'react';
import { useForm } from "react-hook-form";
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogContent from '@mui/material/DialogContent';
import DialogActions from '@mui/material/DialogActions';
import {css} from "@emotion/react";

import {
    FormControl,
    FormHelperText,
    InputLabel,
    NativeSelect,
    useTheme
} from "@mui/material";

import {type PagoType} from "../factura";
import DialogTitle from "../../../components/ui/DialogTitle";
import { StyledInput } from '../../../components/ui/StyledInput/StyledInput';
import {FORMAS_PAGO} from "../../tarifario";

interface FormaPagoModalProps {
    pago?: PagoType;
    onUpdate?: (pago: PagoType) => void;
    isVisible: boolean;
    setVisible: (visible: boolean) => void;
}

const FormaPagoModalModal: React.FC<FormaPagoModalProps> = ({
    pago,
    onUpdate,
    isVisible,
    setVisible
  }) => {
  const {
    register,
    reset,
    handleSubmit,
    formState: { errors }
  } = useForm<PagoType>({
    defaultValues: {
      formaPago: '01',
      total: 0,
      plazo: 0,
      unidadTiempo: 'días'
    }
  });
  const formRef = useRef<HTMLFormElement>(null);
  const theme = useTheme();

  useEffect(() => {
    reset(pago ?? {formaPago: '01', total: 0, plazo: 0, unidadTiempo: 'días'});
  }, [pago, reset]);

  const handleCloseDialog = () => setVisible(false);

  const onFormSubmit = handleSubmit((data) => {
    // const index = FORMAS_PAGO
    //   .findIndex(({ codigo }) => codigo === data.formaPago);
    const pago = {
      formaPago: data.formaPago,
      total: data.total,
      plazo: data.plazo,
      unidadTiempo: 'días'
    } as PagoType;
    onUpdate && onUpdate(pago);
  })
  return (
    <Dialog
      fullWidth
      maxWidth="sm"
      onClose={handleCloseDialog}
      aria-labelledby="edit-forma-pago-title"
      open={isVisible}
    >
      <DialogTitle id="edit-forma-pago-title" onClose={handleCloseDialog}>
        Seleccione forma de pago
      </DialogTitle>
      <DialogContent sx={[ { minHeight: '240px', }, ]}>
        <form ref={formRef} noValidate autoComplete="off" onSubmit={onFormSubmit}>
          <FormControl fullWidth variant="outlined" error={errors.formaPago !== undefined}
             css={css`.Mui-error>select { border-color: ${theme.palette.error.main} !important; }`}>
            <InputLabel
                htmlFor="device-type-select"
                shrink={false}
                css={css`margin-bottom: -24px; transform: none; position: relative; color: ${theme.palette.text.primary}!important; `}
            >
                Forma de Pago *
            </InputLabel>
            <NativeSelect
              id="device-type-select"
              input={<StyledInput />}
              {...register("formaPago", { required: true })}
            >
              <option value="" disabled>Forma de Pago</option>
              {FORMAS_PAGO.map(
                forma => <option key={forma.codigo} value={forma.codigo}>{forma.label}</option>
              )}
            </NativeSelect>
            <FormHelperText>{errors?.formaPago?.message}</FormHelperText>
          </FormControl>
        </form>
      </DialogContent>
      <DialogActions css={css`padding: 0 30px 30px 0;`}>
        <Button variant="outlined" onClick={handleCloseDialog}>Cancel</Button>
        <Button
            variant="contained"
            onClick={() => { formRef?.current?.requestSubmit(); }}
        >Seleccionar</Button>
    </DialogActions>
    </Dialog>
  );
}

export default FormaPagoModalModal;
