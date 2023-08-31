package com.marvic.factsigner.service;

import com.marvic.factsigner.payload.sistema.GrupoDTO;

import java.util.List;

public interface GrupoService {

    GrupoDTO getOne(String id);

    List<GrupoDTO> getAll();

    GrupoDTO create(GrupoDTO dto);

    void delete(String id);

    GrupoDTO update(GrupoDTO dto, String id);
    
}
