import React, {useEffect, useState} from "react";
import { useFieldArray, useFormContext } from "react-hook-form";
import isEmpty from "lodash/isEmpty";
import {FacturaType, ImpuestoType} from "../../services";

function useDetallesForm() {
  const [selected, setSelected] = React.useState<readonly number[]>([]);
  const [editDetalleId, setEditDetalleId] = useState<number>();
  const {
    clearErrors,
    control,
    getValues,
    setError,
    setValue,
    trigger,
  } = useFormContext<FacturaType>();
  const {
    fields,
    append,
    remove,
  } = useFieldArray({
    control,
    name: "detalles",
  });

  useEffect(() => {
    if (editDetalleId) {
      const detalles = getValues("detalles");
      const index = detalles.findIndex(({linea}) => linea === editDetalleId);

      const descripcionInput = document.getElementById(`detalles.${index}.descripcion`) as HTMLInputElement;
      //const descripcionInput = formRef.current?.querySelector(`#detalles.${editDetalleId}.descripcion`) as HTMLInputElement;
      descripcionInput && descripcionInput.focus();
    }
  }, [editDetalleId]);

  const computePrecioSinImpuestos = (): boolean => {
    const detalles = getValues("detalles");
    const detallesLen = detalles.length;
    const index = detalles.findIndex(({linea}) => linea === editDetalleId);
    if (index === -1) {
      console.log('index', -1);
      return false;
    }
    const detalle = detalles[index];

    const valor = (+detalle.precioUnitario) * (+detalle.cantidad);
    setValue(`detalles.${index}.precioTotalSinImpuesto`, +valor.toFixed(2),
      //{shouldDirty: true, shouldTouch: true, shouldValidate: true}
    );

    let isRowValid = true;
    // VALIDATE ROW
    if (valor === 0) {
      console.log('2 isRowValid');
      isRowValid = false;
      setError(`detalles.${index}.precioTotalSinImpuesto`,
        { type: 'manual', message:'Subtotal Sin Impuesto debe ser mayor a cero' }
      );
    }
    if (isEmpty(detalle.descripcion)) {
      console.log('3 isRowValid');
      isRowValid = false;
      trigger(`detalles.${index}.descripcion`);
    }
    if (isRowValid) {
      console.log('4 isRowValid', isRowValid);
      clearErrors(`detalles.${index}.precioTotalSinImpuesto`);
      if (!detalles[detallesLen-1].descripcion) {
        remove(detallesLen-1);
      }
    }
    return isRowValid;
  };

  const onKeyEnter = (event: React.KeyboardEvent<HTMLFormElement>) => {
    if (event.key !== "Enter") {
      return;
    }
    event.preventDefault();
    const target= event.target as HTMLFormElement;

    if (target.id.endsWith('descuento')) {
      if (computePrecioSinImpuestos()) {
        console.log('3');
        appendRow();
      }
    }
    if (target.id.startsWith('detalles')) {
      const form = target.form;
      if (form) {
        const index = [...form].indexOf(event.target);
        const nextElement = form[index + 2];
        nextElement && nextElement.focus();
      }
    }
  };

  const appendRow = () => {
    const detalles = getValues("detalles");
    //const inconsistentEditRow = detalles.length === 0 || detalles.length-1 < editRow;
    //if (inconsistentEditRow || editRow < 0 || computePrecioSinImpuestos()) {
      const linea = 1 + detalles.reduce(
      (acc, { linea }) => linea && linea > acc ? linea: acc, 0);

      setEditDetalleId(linea);
      append({
        linea,
        codigoPrincipal: "",
        descripcion: "",
        cantidad: 1,
        precioUnitario: 0,
        descuento: 0,
        precioTotalSinImpuesto: 0,
        iva: {} as ImpuestoType
      });
      setSelected([]);
    //}
  }

  const handleSelected = (linea: number) => {
    const selectedIndex = selected.indexOf(linea);
    let newSelected: readonly number[] = [];
    if (selectedIndex === -1) {
      newSelected = newSelected.concat(selected, linea);
    } else if (selectedIndex === 0) {
      newSelected = newSelected.concat(selected.slice(1));
    } else if (selectedIndex === selected.length - 1) {
      newSelected = newSelected.concat(selected.slice(0, -1));
    } else if (selectedIndex > 0) {
      newSelected = newSelected.concat(
        selected.slice(0, selectedIndex),
        selected.slice(selectedIndex + 1)
      );
    }
    setSelected(newSelected);
  }

  const removeRow = () => {
    const indexList = getValues('detalles')
      .map(({ linea }, index) => selected.includes(linea) ? index : null)
      .filter(index => index !== null) as number[];

    if (editDetalleId && selected.includes(editDetalleId)) {
      setEditDetalleId(undefined);
    }
    setSelected([]);
    remove(indexList);
  }

  const editRow = (index: number) => {
    const detalles = getValues("detalles");
    setEditDetalleId(detalles[index].linea);
  }

  return { onKeyEnter, selected, setSelected,
    editDetalleId, setEditDetalleId,
    fields, appendRow, editRow, removeRow, handleSelected,
    // control, register, errors
  };
}

export default useDetallesForm;
