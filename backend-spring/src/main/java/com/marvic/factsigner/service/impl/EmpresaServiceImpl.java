package com.marvic.factsigner.service.impl;

import com.marvic.factsigner.exception.ResourceExistsException;
import com.marvic.factsigner.model.sistema.Empresa;
import com.marvic.factsigner.payload.EmpresaDTO;
import com.marvic.factsigner.repository.EmpresaRepository;
import com.marvic.factsigner.service.EmpresaService;
import ec.gob.sri.types.SriAmbiente;
import static org.apache.commons.lang3.StringUtils.trim;
import org.modelmapper.ModelMapper;
import com.marvic.factsigner.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import static org.springframework.util.StringUtils.trimAllWhitespace;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static com.marvic.factsigner.util.Utils.cleanUpper;
import static com.marvic.factsigner.util.Utils.coalesce;

@Service
public class EmpresaServiceImpl implements EmpresaService {


    private EmpresaRepository repository;

    private ModelMapper modelMapper;

    public EmpresaServiceImpl(EmpresaRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public EmpresaDTO getOne(String id) {
        Empresa entidad = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("not found"));
        return mapToDTO(entidad);
    }

    @Override
    public List<EmpresaDTO> getMany() {
        return repository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public EmpresaDTO create(EmpresaDTO dto) {

        // Validate
        dto.setName(trim(dto.getName()));
        dto.setId(trimAllWhitespace(dto.getId()));
        dto.setAmbiente(SriAmbiente.PRUEBAS.value());

        // Check UK by name
        repository
                .findByName(dto.getName())
                .ifPresent((c) -> {throw new ResourceExistsException(dto.getName());});

        // Check PK by id
        repository
                .findById(dto.getId())
                .ifPresent((c) -> {throw new ResourceExistsException(dto.getId());});

        Empresa entity = mapToEntity(dto);
        entity.setAmbiente(SriAmbiente.PRUEBAS);
        entity.setActivo(true);

        Empresa saved = repository.save(entity);
        return mapToDTO(saved);
    }

    @Override
    public void delete(String id) {
        Empresa entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("not found"));
        repository.delete(entity);
    }

    @Override
    public EmpresaDTO update(EmpresaDTO dto, String id) {
        Empresa entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("not found"));

        entity.setName(dto.getName());
        entity.setComercial(dto.getComercial());
        entity.setColor(dto.getColor());
        entity.setDireccion(dto.getDireccion());
        entity.setTelefono(dto.getTelefono());

        repository.save(entity);
        return mapToDTO(entity);
    }

    private Empresa mapToEntity(EmpresaDTO dto) {
        return modelMapper.map(dto, Empresa.class);
    }

    private EmpresaDTO mapToDTO(Empresa model){
        return modelMapper.map(model, EmpresaDTO.class);
    }

}
