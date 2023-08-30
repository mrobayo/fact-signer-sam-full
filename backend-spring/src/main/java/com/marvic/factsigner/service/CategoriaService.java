package com.marvic.factsigner.service;

import com.marvic.factsigner.payload.CategoriaDTO;

import java.util.List;

public interface CategoriaService {

    CategoriaDTO getOne(Integer id);

    List<CategoriaDTO> getMany(String empresaId);

    CategoriaDTO create(CategoriaDTO dto);

    void delete(Integer id);

    CategoriaDTO update(CategoriaDTO dto, Integer id);
}