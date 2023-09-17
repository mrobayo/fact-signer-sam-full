import {useQuery} from "@tanstack/react-query";
import clienteService, { type ClienteType } from "./clienteService";
import {PageType} from "../../util";
import {PageSize} from "../../constants";

export function useGetClientes(criteria: string, activo: boolean, page: number) {
  const query = useQuery<PageType<ClienteType>, Error>({
    queryKey: ['clientes', page, criteria, activo],
    queryFn: () => clienteService.get(criteria, activo, page, PageSize, []),
    keepPreviousData: true,
    staleTime: 30000,
  });
  return query;
}
