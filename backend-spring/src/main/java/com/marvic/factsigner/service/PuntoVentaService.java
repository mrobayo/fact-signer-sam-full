package com.marvic.factsigner.service;

import com.marvic.factsigner.payload.sistema.PuntoVentaDTO;

import java.util.List;

public interface PuntoVentaService {
    PuntoVentaDTO getOne(String id);

    List<PuntoVentaDTO> getAll(String empresaId);

    PuntoVentaDTO create(PuntoVentaDTO dto);

    void delete(String id);

    PuntoVentaDTO update(PuntoVentaDTO dto, String id);
}
