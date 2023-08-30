package com.marvic.factsigner.service.impl;

import com.marvic.factsigner.exception.RequiredKeyException;
import com.marvic.factsigner.exception.ResourceExistsException;
import com.marvic.factsigner.exception.ResourceNotFoundException;
import com.marvic.factsigner.model.sistema.extra.Grupo;

import com.marvic.factsigner.payload.GrupoDTO;
import com.marvic.factsigner.repository.GrupoRepository;
import com.marvic.factsigner.service.GrupoService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

import static com.marvic.factsigner.util.Utils.cleanUpper;
import static com.marvic.factsigner.util.Utils.coalesce;

@Service
public class GrupoServiceImpl implements GrupoService {

    private final GrupoRepository repository;

    private final ModelMapper modelMapper;

    public GrupoServiceImpl(GrupoRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public GrupoDTO getOne(String id) {
        Grupo entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("not found"));
        return mapToDTO(entity);
    }

    @Override
    public List<GrupoDTO> getAll() {
        return repository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public GrupoDTO create(GrupoDTO dto) {
        // Validate
        dto.setName(StringUtils.trimAllWhitespace(dto.getName()));

        String id = coalesce(cleanUpper(dto.getId()), cleanUpper(dto.getName()));
        if (id == null) {
            throw new RequiredKeyException();
        }
        dto.setId(id);
        dto.setActivo(true);

        // Check UK by name
        repository
                .findByName(dto.getName())
                .ifPresent((c) -> {throw new ResourceExistsException(dto.getName());});

        // Check PK by id
        repository
                .findById(id)
                .ifPresent((c) -> {throw new ResourceExistsException(dto.getId());});

        Grupo entity = mapToEntity(dto);
        Grupo saved = repository.save(entity);
        return mapToDTO(saved);
    }

    @Override
    public void delete(String id) {
        Grupo entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("not found"));
        repository.delete(entity);
    }

    @Override
    public GrupoDTO update(GrupoDTO dto, String id) {
        Grupo entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("not found"));

        entity.setName(dto.getName());
        entity.setActivo(dto.isActivo());

        Grupo saved = repository.save(entity);
        return mapToDTO(saved);
    }

    private Grupo mapToEntity(GrupoDTO dto) {
        return modelMapper.map(dto, Grupo.class);
    }

    private GrupoDTO mapToDTO(Grupo model){
        return modelMapper.map(model, GrupoDTO.class);
    }

}
