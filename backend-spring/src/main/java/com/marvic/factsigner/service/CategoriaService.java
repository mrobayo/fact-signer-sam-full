package com.marvic.factsigner.service;

import com.marvic.factsigner.payload.sistema.CategoriaDTO;

import java.util.List;

public interface CategoriaService {

    CategoriaDTO getOne(Integer id);

    List<CategoriaDTO> getAll(String empresaId);

    CategoriaDTO create(CategoriaDTO dto);

    void delete(Integer id);

    CategoriaDTO update(CategoriaDTO dto, Integer id);
}
