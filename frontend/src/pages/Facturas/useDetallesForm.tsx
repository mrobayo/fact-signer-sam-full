import React, {useEffect, useState} from "react";
import { useFieldArray, useFormContext } from "react-hook-form";
import {FacturaType, ImpuestoType} from "../../services";
import isEmpty from "lodash/isEmpty";

function useDetallesForm() {
  const [selected, setSelected] = React.useState<readonly number[]>([]);
  const [currentLinea, setCurrentLinea] = useState<number>();
  const {
    clearErrors,
    control,
    getValues,
    setError,
    setValue,
  } = useFormContext<FacturaType>();
  const {
    fields,
    append,
    remove,
  } = useFieldArray({
    control,
    name: "detalles",
  });

  const getCurrentRowIndex = () => {
    const detalles = getValues("detalles");
    return detalles?.findIndex(({linea}) => linea === currentLinea);
  }

  const getCurrentRow = () => {
    const detalles = getValues("detalles");
    return detalles?.find(({linea}) => linea === currentLinea);
  }

  useEffect(() => {
    if (currentLinea) {
      const index = getCurrentRowIndex();
      const descripcionInput = document.getElementById(`detalles.${index}.descripcion`) as HTMLInputElement;
      //const descripcionInput = formRef.current?.querySelector(`#detalles.${editDetalleId}.descripcion`) as HTMLInputElement;
      descripcionInput && descripcionInput.focus();
    }
  }, [currentLinea]);

  const updateImpuestos = (data: ImpuestoType) => {
    const detalleIndex = getCurrentRowIndex();

    if (detalleIndex !== -1) {
      console.log(`index: ${detalleIndex}`, data);
      setValue(`detalles.${detalleIndex}.iva`, data);
    }
  };

  const computePrecioSinImpuestos = (): boolean => {
    const detalles = getValues("detalles");
    const index = getCurrentRowIndex();
    if (index === -1) {
      return false;
    }
    const detalle = detalles[index];

    const valor = (+detalle.precioUnitario) * (+detalle.cantidad) - (+detalle.descuento);
    setValue(`detalles.${index}.precioTotalSinImpuesto`, +valor.toFixed(2),
      //{shouldDirty: true, shouldTouch: true, shouldValidate: true}
    );

    let isRowValid = true;

    if (valor < 0) { // VALIDATE ROW
      isRowValid = false;
      setError(`detalles.${index}.precioTotalSinImpuesto`,
        {type: 'manual', message: 'Subtotal debe ser mayor a cero'}
      );
    }

    if (isRowValid) {
      clearErrors(`detalles.${index}.precioTotalSinImpuesto`);
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
        const detalles = getValues("detalles");
        const rowLen = detalles.length;
        if (isEmpty(detalles[rowLen-1].descripcion)) {
          remove(rowLen-1);
        }
        appendNew();
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

  const appendNew = () => {
    const detalles = getValues("detalles");
    //const inconsistentEditRow = detalles.length === 0 || detalles.length-1 < editRow;
    //if (inconsistentEditRow || editRow < 0 || computePrecioSinImpuestos()) {
      const linea = 1 + detalles.reduce(
      (acc, { linea }) => linea && linea > acc ? linea: acc, 0);

      setCurrentLinea(linea);
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

  const removeSelected = () => {
    const indexList = getValues('detalles')
      .map(({ linea }, index) => selected.includes(linea) ? index : null)
      .filter(index => index !== null) as number[];

    if (currentLinea && selected.includes(currentLinea)) {
      setCurrentLinea(undefined);
    }
    setSelected([]);
    remove(indexList);
  }

  const editRow = (index: number) => {
    const detalles = getValues("detalles");
    setCurrentLinea(detalles[index].linea);
  }

  return {
    selected, handleSelected,
    currentLinea, updateImpuestos,
    fields, appendNew, editRow, removeSelected,
    getCurrentRow, onKeyEnter,
  };
}

export default useDetallesForm;
