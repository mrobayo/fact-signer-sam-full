package com.marvic.factsigner.service.sistema;

import com.marvic.factsigner.payload.PageResponse;
import com.marvic.factsigner.payload.sistema.ClienteDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClienteService {

    void addRandom(int number);

    ClienteDTO getOne(String id);

    PageResponse<ClienteDTO> getAll(String search, String activo, Pageable paging);

    ClienteDTO create(ClienteDTO dto);

    void delete(String id);

    ClienteDTO update(ClienteDTO dto, String id);
    
}
