import {useQuery} from "@tanstack/react-query";
import grupoService, { type GrupoType } from "./grupoService";

export function useGrupos() {
  const query = useQuery<GrupoType[], Error>({
    queryKey: ['grupos'],
    queryFn: () => grupoService.get(),
    keepPreviousData: true,
    staleTime: 30000,
  });
  return query;
}
