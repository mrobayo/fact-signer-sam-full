package com.marvic.factsigner.service.impl;

import com.marvic.factsigner.exception.RequiredKeyException;
import com.marvic.factsigner.exception.ResourceExistsException;
import com.marvic.factsigner.model.sistema.Usuario;
import com.marvic.factsigner.payload.UsuarioDTO;
import com.marvic.factsigner.repository.UsuarioRepository;
import com.marvic.factsigner.service.UsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang3.StringUtils.trim;
import static org.springframework.util.StringUtils.trimAllWhitespace;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static com.marvic.factsigner.util.Utils.cleanUpper;
import static com.marvic.factsigner.util.Utils.coalesce;

@Service
public class UsuarioServiceImpl implements UsuarioService  {
    private UsuarioRepository repository;

    private ModelMapper modelMapper;

    public UsuarioServiceImpl(UsuarioRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UsuarioDTO getOne(String id) {
        Usuario entidad = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("not found"));
        return mapToDTO(entidad);
    }

    @Override
    public List<UsuarioDTO> getMany() {
        return repository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public UsuarioDTO create(UsuarioDTO dto) {

        dto.setUsername(trimAllWhitespace(dto.getUsername()));
        dto.setName(trim(dto.getName()));

        String id = cleanUpper(dto.getUsername());
        if (id == null) {
            throw new RequiredKeyException();
        }
        dto.setUsername(id);
        dto.setId(id);

        // Check UK by email
        repository
                .findByEmail(dto.getEmail())
                .ifPresent((c) -> {throw new ResourceExistsException(dto.getEmail());});

        // Check PK by id
        repository
                .findById(id)
                .ifPresent((c) -> {throw new ResourceExistsException(dto.getUsername());});

        // Check UK by email
        repository
                .findByIdentidad(dto.getIdentidad())
                .ifPresent((c) -> {throw new ResourceExistsException(dto.getIdentidad());});

        Usuario entity = mapToEntity(dto);
        Usuario saved = repository.save(entity);
        return mapToDTO(saved);
    }

    @Override
    public void delete(String id) {
        Usuario entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("not found"));
        repository.delete(entity);
    }

    @Override
    public UsuarioDTO update(UsuarioDTO dto, String id) {
        Usuario entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("not found"));

        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setNuncaExpira(dto.isNuncaExpira());
        entity.setIdentidad(dto.getIdentidad());
        entity.setTelefono(dto.getTelefono());
        entity.setCambiarPasswd(dto.isCambiarPasswd());
        entity.setCuentaBloqueada(dto.isCuentaBloqueada());

        repository.save(entity);
        return mapToDTO(entity);
    }

    private Usuario mapToEntity(UsuarioDTO dto) {
        return modelMapper.map(dto, Usuario.class);
    }

    private UsuarioDTO mapToDTO(Usuario model){
        return modelMapper.map(model, UsuarioDTO.class);
    }

}
