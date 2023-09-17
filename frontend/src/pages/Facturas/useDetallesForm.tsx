import { useFieldArray, useFormContext } from "react-hook-form";
import {FacturaType, ImpuestoType} from "../../services";

function useDetallesForm() {
  const { control } = useFormContext<FacturaType>();
  const {
    fields,
    append,
    remove,
  } = useFieldArray({
    control,
    name: "detalles",
  });
  const appendRow = () => {
    append({
      linea: 0,
      codigoPrincipal: "",
      descripcion: "",
      cantidad: 1,
      precioUnitario: 0,
      descuento: 0,
      precioTotalSinImpuesto: 0,
      iva: {} as ImpuestoType
    });
  }

  const removeRow = (index: number) => {
    remove(index);
  }

  return { fields, appendRow, removeRow};

}

export default useDetallesForm;
