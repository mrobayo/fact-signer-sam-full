package com.marvic.factsigner.service.impl;

import com.marvic.factsigner.exception.ResourceExistsException;
import com.marvic.factsigner.exception.ResourceNotFoundException;
import com.marvic.factsigner.model.comprobantes.extra.PuntoVenta;
import com.marvic.factsigner.model.sistema.Empresa;
import com.marvic.factsigner.payload.PuntoVentaDTO;
import com.marvic.factsigner.payload.PuntoVentaDTO;
import com.marvic.factsigner.repository.PuntoVentaRepository;
import com.marvic.factsigner.repository.EmpresaRepository;
import com.marvic.factsigner.repository.PuntoVentaRepository;
import com.marvic.factsigner.service.PuntoVentaService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PuntoVentaServiceImpl implements PuntoVentaService  {

    private final PuntoVentaRepository puntoVentaRepository;

    private final EmpresaRepository empresaRepository;

    private final ModelMapper modelMapper;

    public PuntoVentaServiceImpl(PuntoVentaRepository puntoVentaRepository, EmpresaRepository empresaRepository, ModelMapper modelMapper) {
        this.puntoVentaRepository = puntoVentaRepository;
        this.empresaRepository = empresaRepository;
        this.modelMapper = modelMapper;
    }
    
    @Override
    public PuntoVentaDTO getOne(String id) {
        PuntoVenta entidad = puntoVentaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("not found"));
        return mapToDTO(entidad);
    }

    @Override
    public List<PuntoVentaDTO> getAll(String empresaId) {
        return puntoVentaRepository.findAllByEmpresaId(empresaId).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public PuntoVentaDTO create(PuntoVentaDTO dto) {
        // Check UK
        puntoVentaRepository
                .findByEstabAndPtoEmiAndEmpresaId(dto.getEstab(), dto.getPtoEmi(), dto.getEmpresaId())
                .ifPresent((c) -> {throw new ResourceExistsException(dto.getName());});

        PuntoVenta entity = mapToEntity(dto);

        Empresa empresa = empresaRepository.findById(dto.getEmpresaId()).get();
        entity.setEmpresa(empresa);
        entity.setActivo(true);
        entity.setId(String.format("%s-%s-%s", empresa.getId(), entity.getEstab(), entity.getPtoEmi()));

        PuntoVenta saved = puntoVentaRepository.save(entity);
        return mapToDTO(saved);
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public PuntoVentaDTO update(PuntoVentaDTO dto, String id) {
        return null;
    }

    private PuntoVenta mapToEntity(PuntoVentaDTO dto) {
        return modelMapper.map(dto, PuntoVenta.class);
    }

    private PuntoVentaDTO mapToDTO(PuntoVenta model) {
        return modelMapper.map(model, PuntoVentaDTO.class);
    }
    
}
