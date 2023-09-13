import {useQuery} from "@tanstack/react-query";
import clienteService, { type ClienteType } from "./clienteService";

export function useGetCliente(id?: string) {
  const query = useQuery<ClienteType, Error>({
    queryKey: ['clientes', 'byId', id],
    queryFn: () => clienteService.getById(id),
    keepPreviousData: true,
    staleTime: 30000,
  });
  return query;
}
