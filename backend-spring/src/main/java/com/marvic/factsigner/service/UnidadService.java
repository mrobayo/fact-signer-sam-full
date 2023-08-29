package com.marvic.factsigner.service;

import com.marvic.factsigner.payload.UnidadDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UnidadService {

    UnidadDTO getOne(String id);

    List<UnidadDTO> getMany();

    UnidadDTO create(UnidadDTO dto);

    void delete(String id);

    UnidadDTO update(UnidadDTO dto, String id);
}
