package com.marvic.factsigner.service.impl;

import com.marvic.factsigner.exception.ResourceExistsException;
import com.marvic.factsigner.exception.ResourceNotFoundException;
import com.marvic.factsigner.model.sistema.Cliente;
import com.marvic.factsigner.model.sistema.extra.Grupo;
import com.marvic.factsigner.payload.PageResponse;
import com.marvic.factsigner.payload.sistema.ClienteDTO;
import com.marvic.factsigner.repository.ClienteRepository;
import com.marvic.factsigner.repository.GrupoRepository;
import com.marvic.factsigner.service.sistema.ClienteService;
import com.marvic.factsigner.util.PageUtil;
import com.marvic.factsigner.util.Utils;
import com.marvic.sample.DataFiller;
import ec.gob.sri.types.SriEnumIdentidad;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public PageResponse<ClienteDTO> getAll(Pageable paging) {
        Page<Cliente> page = repository.findAll(paging);
        return PageUtil.mapPage(page, this::mapToDTO);
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

    public void addRandom(int number) {
        Grupo grupo = grupoRepository.findById("PARTICULAR").get();
        for(int i = 1; i <= number; i++) {
            Cliente c = DataFiller.getCliente();
            c.setGrupo(grupo);
            repository.save(c);
        }
    }

    private Cliente mapToEntity(ClienteDTO dto) {
        return modelMapper.map(dto, Cliente.class);
    }

    private ClienteDTO mapToDTO(Cliente model){
        return modelMapper.map(model, ClienteDTO.class);
    }

}
