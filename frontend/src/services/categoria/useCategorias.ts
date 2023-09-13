import {useQuery} from "@tanstack/react-query";
import categoriaService, { type CategoriaType } from "./categoriaService";

export function useCategorias(empresaId: string) {
  const query = useQuery<CategoriaType[], Error>({
    queryKey: ['categorias', empresaId],
    queryFn: () => categoriaService.get(empresaId),
    keepPreviousData: true,
    staleTime: 30000,
  });
  return query;
}
