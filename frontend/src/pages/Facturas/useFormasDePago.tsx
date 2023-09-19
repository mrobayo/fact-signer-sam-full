import {useState} from "react";
import {useFieldArray, useFormContext} from "react-hook-form";
import {FacturaType} from "../../services";

function useDetallesForm() {
  const [currentRow, setCurrentRow] = useState<number>();
  const {
    control,
    getValues,
  } = useFormContext<FacturaType>();
  const {
    fields,
    append,
    remove,
  } = useFieldArray({ control, name: "pagos" });

  const appendNew = () => {
    const pagos = getValues("pagos");
    const linea = pagos.length;
    setCurrentRow(linea);
    append({
      formaPago: '',
      total: 0,
      plazo: 0,
      unidadTiempo: 'dias'
    });
  };

  const removeRow = (index: number) => {
    remove(index);
  }

  const editRow = (index: number) => {
    setCurrentRow(index);
  }

  return {
    currentRow, fields, appendNew, editRow, removeRow,
  };
}

export default useDetallesForm;
