package com.marvic.factsigner.service.impl;

import com.marvic.factsigner.exception.RequiredKeyException;
import com.marvic.factsigner.exception.ResourceExistsException;
import com.marvic.factsigner.model.sistema.extra.Unidad;
import com.marvic.factsigner.model.sistema.types.UnidadTipo;
import com.marvic.factsigner.payload.UnidadDTO;
import com.marvic.factsigner.repository.UnidadRepository;
import com.marvic.factsigner.service.UnidadService;
import static com.marvic.factsigner.util.Utils.*;
import org.modelmapper.ModelMapper;
import com.marvic.factsigner.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UnidadServiceImpl implements UnidadService {
    
    private UnidadRepository repository;

    private ModelMapper modelMapper;

    public UnidadServiceImpl(UnidadRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UnidadDTO getOne(String id) {
        Unidad entidad = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("not found"));
        return mapToDTO(entidad);
    }

    @Override
    public List<UnidadDTO> getMany() {
        return repository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public UnidadDTO create(UnidadDTO dto) {

        // Validate
        dto.setName(StringUtils.trimAllWhitespace(dto.getName()));

        String id = coalesce(cleanUpper(dto.getId()), cleanUpper(dto.getName()));
        if (id == null) {
            throw new RequiredKeyException();
        }
        dto.setId(id);
        if (dto.getFactor() == null) {
            dto.setFactor(new BigDecimal(1));
        }

        // Check UK by name
        repository
                .findByName(dto.getName())
                .ifPresent((c) -> {throw new ResourceExistsException(dto.getName());});

        // Check PK by id
        repository
                .findById(id)
                .ifPresent((c) -> {throw new ResourceExistsException(dto.getId());});

        Unidad entidad = mapToEntity(dto);
        Unidad saved = repository.save(entidad);
        return mapToDTO(saved);
    }

    @Override
    public void delete(String id) {
        Unidad entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("not found"));
        repository.delete(entity);
    }

    @Override
    public UnidadDTO update(UnidadDTO dto, String id) {
        Unidad entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("not found"));
        entity.setBase(dto.isBase());
        entity.setFactor(dto.getFactor());
        entity.setName(dto.getName());
        entity.setTipo(UnidadTipo.valueOf(dto.getTipo()));
        repository.save(entity);
        return mapToDTO(entity);
    }

    private Unidad mapToEntity(UnidadDTO dto) {
        return modelMapper.map(dto, Unidad.class);
    }

    private UnidadDTO mapToDTO(Unidad model){
        return modelMapper.map(model, UnidadDTO.class);
    }

}
