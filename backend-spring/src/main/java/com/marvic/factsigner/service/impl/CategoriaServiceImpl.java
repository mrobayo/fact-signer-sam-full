package com.marvic.factsigner.service.impl;

import com.marvic.factsigner.exception.ResourceExistsException;
import com.marvic.factsigner.exception.ResourceNotFoundException;
import com.marvic.factsigner.model.sistema.Empresa;
import com.marvic.factsigner.model.sistema.extra.Categoria;
import com.marvic.factsigner.payload.sistema.CategoriaDTO;
import com.marvic.factsigner.repository.CategoriaRepository;
import com.marvic.factsigner.repository.EmpresaRepository;
import com.marvic.factsigner.service.sistema.CategoriaService;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaServiceImpl implements CategoriaService {
    
    private final CategoriaRepository repository;

    private final EmpresaRepository empresaRepository;

    private final ModelMapper modelMapper;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository, EmpresaRepository empresaRepository, ModelMapper modelMapper) {
        this.repository = categoriaRepository;
        this.empresaRepository = empresaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoriaDTO getOne(Integer id) {
        Categoria entidad = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("not found"));
        return mapToDTO(entidad);
    }

    @Override
    public List<CategoriaDTO> getAll(String empresaId) {
        return repository.findAllByEmpresaId(empresaId).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public CategoriaDTO create(CategoriaDTO dto) {

        // Validate
        dto.setId(null);

        // Check UK by name
        repository
                .findByNameAndEmpresaId(dto.getName(), dto.getEmpresaId())
                .ifPresent((c) -> {throw new ResourceExistsException(dto.getName());});

        Categoria entity = mapToEntity(dto);

        Empresa empresa = empresaRepository.findById(dto.getEmpresaId()).get();
        entity.setEmpresa(empresa);
        entity.setActivo(true);

        Categoria saved = repository.save(entity);
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

        if (StringUtils.isNotBlank(dto.getEmpresaId())) {
            Empresa empresa = empresaRepository.findById(dto.getEmpresaId()).get();
            entity.setEmpresa(empresa);
        }

        Categoria saved = repository.save(entity);
        return mapToDTO(saved);
    }

    private Categoria mapToEntity(CategoriaDTO dto) {
        return modelMapper.map(dto, Categoria.class);
    }

    private CategoriaDTO mapToDTO(Categoria model) {
        return modelMapper.map(model, CategoriaDTO.class);
    }

}
