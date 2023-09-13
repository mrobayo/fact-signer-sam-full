import {useQuery} from "@tanstack/react-query";
import productoService, { type ProductoType } from "./productoService";

export function useGetProducto(id: string|undefined, options?: { defaultValues: Partial<ProductoType> }) {
  const query = useQuery<ProductoType, Error>({
    queryKey: ['productos', 'byId', id],
    queryFn: () => productoService.getById(id, options),
    keepPreviousData: true,
    staleTime: 30000,
  });
  return query;
}
