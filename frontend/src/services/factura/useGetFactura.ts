import {useQuery} from "@tanstack/react-query";
import facturaService, { type FacturaType } from "./facturaService";

export function useGetFactura(
  id: string|undefined,
  options?: { defaultValues: Partial<FacturaType> }
) {
  const query = useQuery<FacturaType, Error>({
    queryKey: ['facturas', 'byId', id],
    queryFn: () => facturaService.getById(id, options),
    keepPreviousData: true,
    staleTime: 30000,
  });
  return query;
}
