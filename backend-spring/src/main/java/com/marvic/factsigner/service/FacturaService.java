package com.marvic.factsigner.service;

import com.marvic.factsigner.payload.FacturaDTO;
import com.marvic.factsigner.payload.PageResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;
import java.util.List;

public interface FacturaService {

    /**
     * Aprobar comprobante
     */
    String approve(String id, Authentication auth);

    /**
     * Generar XML
     */
    String buildXml(String id, Authentication auth);

    FacturaDTO getOne(String id);

    PageResponse<FacturaDTO> getAllByEmpresaId(String empresaId, Pageable paging);

    // List<FacturaDTO> getAllByPuntoVentaId(String puntoVentaId);

    FacturaDTO create(FacturaDTO dto);

    void delete(String id);

    FacturaDTO update(FacturaDTO dto, String id);
}
