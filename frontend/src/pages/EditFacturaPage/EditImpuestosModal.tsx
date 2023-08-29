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

import {type ImpuestoType} from "../FacturasPage/factura";
import DialogTitle from "../../components/ui/DialogTitle";
import { StyledInput } from '../../components/ui/StyledInput/StyledInput.tsx';
import {TARIFAS_IVA} from "../tarifario.ts";

interface EditImpuestosProps {
    impuesto?: ImpuestoType;
    onUpdate?: (impuestos: ImpuestoType) => void;
    isVisible: boolean;
    setVisible: (visible: boolean) => void;
}

const EditImpuestosModal: React.FC<EditImpuestosProps> = ({
    impuesto,
    onUpdate,
    isVisible,
    setVisible
  }) => {
  const {
    register,
    reset,
    handleSubmit,
    formState: { errors }
  } = useForm<ImpuestoType>({
    defaultValues: {
      codigo: 2,
      codigoPorcentaje: 2,
      tarifa: 12,
      baseImponible: 0,
      valor: 0
    }
  });
  const formRef = useRef<HTMLFormElement>(null);
  const theme = useTheme();

  useEffect(() => {
    reset(impuesto ?? {codigo: 2, codigoPorcentaje: 2, tarifa: 12, baseImponible: 0, valor: 0});
  }, [impuesto, reset]);

  const handleCloseDialog = () => setVisible(false);

  const onFormSubmit = handleSubmit((data) => {
    const index = TARIFAS_IVA
      .findIndex(({ codigoPorcentaje }) => codigoPorcentaje === +data.codigoPorcentaje);
    const impuesto = {
      ...data,
      codigoPorcentaje: +data.codigoPorcentaje,
      tarifa: TARIFAS_IVA[index]?.tarifa
    } as ImpuestoType;
    onUpdate && onUpdate(impuesto);
  })
  return (
    <Dialog
      fullWidth
      maxWidth="sm"
      onClose={handleCloseDialog}
      aria-labelledby="edit-impuesto-title"
      open={isVisible}
    >
      <DialogTitle id="edit-impuesto-title" onClose={handleCloseDialog}>
        Seleccione el Impuesto a Aplicar
      </DialogTitle>
      <DialogContent sx={[ { minHeight: '240px', }, ]}>
        <form ref={formRef} noValidate autoComplete="off" onSubmit={onFormSubmit}>
          <FormControl fullWidth variant="outlined" error={errors.codigoPorcentaje !== undefined}
             css={css`.Mui-error>select { border-color: ${theme.palette.error.main} !important; }`}>
            <InputLabel
                htmlFor="device-type-select"
                shrink={false}
                css={css`margin-bottom: -24px; transform: none; position: relative; color: ${theme.palette.text.primary}!important; `}
            >
                Tarifa del IVA *
            </InputLabel>
            <NativeSelect
              id="device-type-select"
              input={<StyledInput />}
              {...register("codigoPorcentaje", { required: true })}
            >
              <option value="" disabled>Tarifa del IVA</option>
              {TARIFAS_IVA.filter(({active}) => active).map(
                tarifa => <option key={tarifa.codigoPorcentaje} value={tarifa.codigoPorcentaje}>{tarifa.label}</option>
              )}
            </NativeSelect>
            <FormHelperText>{errors?.codigoPorcentaje?.message}</FormHelperText>
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

export default EditImpuestosModal;
