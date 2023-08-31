package com.marvic.factsigner.service.impl;

import com.marvic.factsigner.exception.RequiredKeyException;
import com.marvic.factsigner.exception.ResourceExistsException;
import com.marvic.factsigner.exception.ResourceNotFoundException;
import com.marvic.factsigner.model.sistema.Cliente;
import com.marvic.factsigner.model.sistema.extra.Grupo;
import com.marvic.factsigner.payload.ClienteDTO;
import com.marvic.factsigner.repository.ClienteRepository;
import com.marvic.factsigner.repository.GrupoRepository;
import com.marvic.factsigner.service.ClienteService;
import com.marvic.factsigner.util.Utils;
import ec.gob.sri.types.SriEnumIdentidad;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.marvic.factsigner.util.Utils.cleanUpper;
import static com.marvic.factsigner.util.Utils.coalesce;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository repository;
    private final GrupoRepository grupoRepository;
    private final ModelMapper modelMapper;

    public ClienteServiceImpl(ClienteRepository repository, GrupoRepository grupoRepository, ModelMapper modelMapper) {
        this.repository = repository;
        this.grupoRepository = grupoRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ClienteDTO getOne(String id) {
        UUID uuid = Utils.toUUID(id);
        Cliente entity = repository.findById(uuid).orElseThrow(() -> new ResourceNotFoundException("not found"));
        return mapToDTO(entity);
    }

    @Override
    public List<ClienteDTO> getAll() {
        return repository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public ClienteDTO create(ClienteDTO dto) {
        // Validate
        dto.setName(StringUtils.trim(dto.getName()));

        SriEnumIdentidad tipo = SriEnumIdentidad.valueOf(dto.getTipo());

        // Check UK by identidad + tipo
        repository
                .findByIdentidadAndTipo(dto.getIdentidad(), tipo)
                .ifPresent((c) -> {throw new ResourceExistsException(dto.getName());});

        Cliente entity = mapToEntity(dto);

        entity.setTipo(tipo);
        entity.setId(null);

        entity.setUltimaVenta(null);
        entity.setActivo(true);

        // Grupo
        Grupo grupo = grupoRepository.findById(coalesce(dto.getGrupoId(), "PARTICULAR")).get();
        entity.setGrupo(grupo);

        Cliente saved = repository.save(entity);
        return mapToDTO(saved);
    }

    @Override
    public void delete(String id) {
        UUID uuid = Utils.toUUID(id);
        Cliente entity = repository.findById(uuid).orElseThrow(() -> new ResourceNotFoundException("not found"));
        repository.delete(entity);
    }

    @Override
    public ClienteDTO update(ClienteDTO dto, String id) {
        UUID uuid = Utils.toUUID(id);
        Cliente entity = repository.findById(uuid).orElseThrow(() -> new ResourceNotFoundException("not found"));

        entity.setName(dto.getName());
        // entity.setActivo(dto.isActivo());

        Cliente saved = repository.save(entity);
        return mapToDTO(saved);
    }

    private Cliente mapToEntity(ClienteDTO dto) {
        return modelMapper.map(dto, Cliente.class);
    }

    private ClienteDTO mapToDTO(Cliente model){
        return modelMapper.map(model, ClienteDTO.class);
    }

}
