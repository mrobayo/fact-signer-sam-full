package com.marvic.factsigner.service;

import com.marvic.factsigner.payload.sistema.ClienteDTO;

import java.util.List;

public interface ClienteService {

    ClienteDTO getOne(String id);

    List<ClienteDTO> getAll();

    ClienteDTO create(ClienteDTO dto);

    void delete(String id);

    ClienteDTO update(ClienteDTO dto, String id);
    
}
