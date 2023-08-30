package com.marvic.factsigner.service;

import com.marvic.factsigner.payload.EmpresaDTO;

import java.util.List;

public interface EmpresaService {

    EmpresaDTO getOne(String id);

    List<EmpresaDTO> getMany();

    EmpresaDTO create(EmpresaDTO dto);

    void delete(String id);

    EmpresaDTO update(EmpresaDTO dto, String id);
}
