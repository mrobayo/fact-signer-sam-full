package com.marvic.factsigner.service.impl;

import com.marvic.factsigner.payload.FacturaDTO;
import com.marvic.factsigner.service.FacturaService;
import org.springframework.stereotype.Service;

import java.util.List;

// @Service
public class FacturaServiceImpl implements FacturaService {

    @Override
    public FacturaDTO getOne(String id) {
        return null;
    }

    @Override
    public List<FacturaDTO> getAllByEmpresaId(String empresaId) {
        return null;
    }

    @Override
    public List<FacturaDTO> getAllByPuntoVentaId(String puntoVentaId) {
        return null;
    }


    @Override
    public FacturaDTO create(FacturaDTO dto) {
        return null;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public FacturaDTO update(FacturaDTO dto, String id) {
        return null;
    }
}
