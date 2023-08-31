package com.marvic.factsigner.service.impl;

import com.marvic.factsigner.exception.RequiredKeyException;
import com.marvic.factsigner.exception.ResourceExistsException;
import com.marvic.factsigner.model.sistema.Usuario;
import com.marvic.factsigner.payload.sistema.UsuarioDTO;
import com.marvic.factsigner.repository.UsuarioRepository;
import com.marvic.factsigner.service.UsuarioService;
import org.apache.commons.lang3.ArrayUtils;
import org.modelmapper.ModelMapper;
import com.marvic.factsigner.exception.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.marvic.factsigner.util.Utils.coalesce;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.trim;
import static org.springframework.util.StringUtils.trimAllWhitespace;

import java.util.List;
import java.util.stream.Collectors;

import static com.marvic.factsigner.util.Utils.cleanUpper;

@Service
public class UsuarioServiceImpl implements UsuarioService  {
    private final UsuarioRepository repository;

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository repository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
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
        entity.setPassword( passwordEncoder.encode(dto.getNewPassword()) );

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

        // Update password
        if (isNotEmpty(dto.getNewPassword())) {
            entity.setPassword( passwordEncoder.encode(dto.getNewPassword()) );
        }

        entity.setName(dto.getName());

        entity.setRoles( String.join(",", coalesce(dto.getRoles(), ArrayUtils.EMPTY_STRING_ARRAY )) );
        entity.setEmpresas( String.join(",", coalesce(dto.getEmpresas(), ArrayUtils.EMPTY_STRING_ARRAY)) );

        entity.setCuentaBloqueada(dto.isCuentaBloqueada());
        entity.setCambiarPasswd(dto.isCambiarPasswd());
        entity.setNuncaExpira(dto.isNuncaExpira());

        entity.setIdentidad(dto.getIdentidad());
        entity.setEmail(dto.getEmail());
        entity.setTelefono(dto.getTelefono());

        repository.save(entity);
        return mapToDTO(entity);
    }

    private Usuario mapToEntity(UsuarioDTO dto) {
        Usuario entity = modelMapper.map(dto, Usuario.class);
        entity.setRoles( String.join(",", coalesce(dto.getRoles(), ArrayUtils.EMPTY_STRING_ARRAY )) );
        entity.setEmpresas( String.join(",", coalesce(dto.getEmpresas(), ArrayUtils.EMPTY_STRING_ARRAY)) );
        return entity;
    }

    private UsuarioDTO mapToDTO(Usuario model){
        UsuarioDTO dto = modelMapper.map(model, UsuarioDTO.class);

        String[] roles = ArrayUtils.EMPTY_STRING_ARRAY;
        if (isNotEmpty(model.getRoles())) {
            roles = model.getRoles().split(",");
        }
        dto.setRoles(roles);

        String[] empresas = ArrayUtils.EMPTY_STRING_ARRAY;
        if (isNotEmpty(model.getEmpresas())) {
            empresas = model.getEmpresas().split(",");
        }
        dto.setEmpresas(empresas);

        //skipField(dto);

        return dto;
    }

//    private void skipField(UsuarioDTO dto) {
//        dto.setNewPassword(null);
//    }

}
