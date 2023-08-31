package com.marvic.factsigner.service;

import com.marvic.factsigner.payload.FacturaDTO;

import java.time.LocalDate;
import java.util.List;

public interface FacturaService {

    FacturaDTO getOne(String id);

    List<FacturaDTO> getAllByEmpresaId(String empresaId);

    List<FacturaDTO> getAllByPuntoVentaId(String puntoVentaId);

    FacturaDTO create(FacturaDTO dto);

    void delete(String id);

    FacturaDTO update(FacturaDTO dto, String id);
}
