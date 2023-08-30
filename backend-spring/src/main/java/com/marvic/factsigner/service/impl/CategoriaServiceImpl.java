package com.marvic.factsigner.service.impl;

import com.marvic.factsigner.exception.RequiredKeyException;
import com.marvic.factsigner.exception.ResourceExistsException;
import com.marvic.factsigner.model.sistema.extra.Categoria;
import com.marvic.factsigner.payload.CategoriaDTO;
import com.marvic.factsigner.repository.CategoriaRepository;
import com.marvic.factsigner.service.CategoriaService;
import org.modelmapper.ModelMapper;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static com.marvic.factsigner.util.Utils.cleanUpper;
import static com.marvic.factsigner.util.Utils.coalesce;

@Service
public class CategoriaServiceImpl implements CategoriaService {
    
    private CategoriaRepository repository;

    private ModelMapper modelMapper;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository, ModelMapper modelMapper) {
        this.repository = categoriaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoriaDTO getOne(Integer id) {
        Categoria entidad = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("not found"));
        return mapToDTO(entidad);
    }

    @Override
    public List<CategoriaDTO> getMany(String empresaId) {
        return repository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public CategoriaDTO create(CategoriaDTO dto) {

        // Validate
        dto.setName(StringUtils.trimAllWhitespace(dto.getName()));
        dto.setId(null);

        // Check UK by name
        repository
                .findByNameAndEmpresaId(dto.getName(), dto.getEmpresaId())
                .ifPresent((c) -> {throw new ResourceExistsException(dto.getName());});

        Categoria entidad = mapToEntity(dto);
        Categoria saved = repository.save(entidad);
        return mapToDTO(saved);
    }

    @Override
    public void delete(Integer id) {
        Categoria entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("not found"));
        repository.delete(entity);
    }

    @Override
    public CategoriaDTO update(CategoriaDTO dto, Integer id) {
        Categoria entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("not found"));

        entity.setName(dto.getName());

        repository.save(entity);
        return mapToDTO(entity);
    }

    private Categoria mapToEntity(CategoriaDTO dto) {
        return modelMapper.map(dto, Categoria.class);
    }

    private CategoriaDTO mapToDTO(Categoria model){
        return modelMapper.map(model, CategoriaDTO.class);
    }

}
