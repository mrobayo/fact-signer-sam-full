import {useQuery} from "@tanstack/react-query";
import clienteService, { type ClienteType } from "./clienteService";

export function useSaveCliente(id: string|null, body: ClienteType) {
  const query = useQuery<ClienteType, Error>({
    queryKey: ['cliente', id],
    queryFn: () => {
      return (id != null) ? clienteService.update(id, body) : clienteService.create(body);
    },
  });
  return query;
}
