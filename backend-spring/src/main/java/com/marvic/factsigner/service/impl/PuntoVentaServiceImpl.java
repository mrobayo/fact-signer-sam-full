package com.marvic.factsigner.service.impl;

import com.marvic.factsigner.exception.ResourceExistsException;
import com.marvic.factsigner.exception.ResourceNotFoundException;
import com.marvic.factsigner.model.comprobantes.extra.PuntoSecuencia;
import com.marvic.factsigner.model.comprobantes.extra.PuntoVenta;
import com.marvic.factsigner.model.sistema.Empresa;
import com.marvic.factsigner.payload.sistema.EmpresaDTO;
import com.marvic.factsigner.payload.sistema.PuntoSecuenciaDTO;
import com.marvic.factsigner.payload.sistema.PuntoVentaDTO;
import com.marvic.factsigner.repository.PuntoSecuenciaRepository;
import com.marvic.factsigner.repository.PuntoVentaRepository;
import com.marvic.factsigner.repository.EmpresaRepository;
import com.marvic.factsigner.service.PuntoVentaService;

import ec.gob.sri.types.SriTipoDoc;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PuntoVentaServiceImpl implements PuntoVentaService  {

    private final PuntoVentaRepository puntoVentaRepository;

    private final PuntoSecuenciaRepository puntoSecuenciaRepository;

    private final EmpresaRepository empresaRepository;

    private final ModelMapper modelMapper;

    public PuntoVentaServiceImpl(PuntoVentaRepository puntoVentaRepository, PuntoSecuenciaRepository puntoSecuenciaRepository, EmpresaRepository empresaRepository, ModelMapper modelMapper) {
        this.puntoVentaRepository = puntoVentaRepository;
        this.puntoSecuenciaRepository = puntoSecuenciaRepository;
        this.empresaRepository = empresaRepository;
        this.modelMapper = modelMapper;
    }
    
    @Override
    public PuntoVentaDTO getOne(String id) {
        PuntoVenta entity = puntoVentaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("not found"));

        List<PuntoSecuencia> secuencias = puntoSecuenciaRepository.findAllByPuntoVentaId(id);
        List<PuntoSecuenciaDTO> secuenciasDto = secuencias.stream().map(this::mapSecuenciaToDTO).collect(Collectors.toList());

        PuntoVentaDTO dto = mapToDTO(entity);
        dto.setSecuencias(secuenciasDto);
        dto.setEmpresa(modelMapper.map(entity.getEmpresa(), EmpresaDTO.class));

        return dto;
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

        // Guardar secuencias
        List<PuntoSecuencia> secuencias = Arrays.stream(SriTipoDoc.values())
                .map((tipoDoc -> tipoDocToSecuencia(tipoDoc, saved)))
                .collect(Collectors.toList());

        List<PuntoSecuenciaDTO> secuenciasDTO = puntoSecuenciaRepository
                .saveAll(secuencias)
                .stream().map(this::mapSecuenciaToDTO).collect(Collectors.toList());

        PuntoVentaDTO savedDto = mapToDTO(saved);
        savedDto.setSecuencias(secuenciasDTO);

        return savedDto;
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

    private PuntoSecuenciaDTO mapSecuenciaToDTO(PuntoSecuencia model) {
        return modelMapper.map(model, PuntoSecuenciaDTO.class);
    }

    private PuntoSecuencia tipoDocToSecuencia(SriTipoDoc tipoDoc, PuntoVenta puntoVenta) {
        PuntoSecuencia secuencia = new PuntoSecuencia();
        secuencia.setTipoDoc(tipoDoc);
        secuencia.setSecuencia(1);
        secuencia.setPuntoVenta(puntoVenta);
        secuencia.setId(String.format("%s-%s", puntoVenta.getId(), tipoDoc.name()));

        return secuencia;
    }
    
}
