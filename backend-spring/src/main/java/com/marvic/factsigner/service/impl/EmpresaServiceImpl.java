package com.marvic.factsigner.service.impl;

import com.marvic.factsigner.exception.ResourceExistsException;
import com.marvic.factsigner.model.Util;
import com.marvic.factsigner.model.sistema.Empresa;
import com.marvic.factsigner.payload.sistema.CertificateDTO;
import com.marvic.factsigner.payload.sistema.EmpresaDTO;
import com.marvic.factsigner.repository.EmpresaRepository;
import com.marvic.factsigner.service.sistema.EmpresaService;
import com.marvic.factsigner.util.AesCipher;
import ec.gob.sri.types.SriAmbiente;
import static org.apache.commons.lang3.StringUtils.trim;
import org.modelmapper.ModelMapper;
import com.marvic.factsigner.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.utils.IoUtils;

import static org.springframework.util.StringUtils.trimAllWhitespace;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmpresaServiceImpl implements EmpresaService {

    private final Logger log = LoggerFactory.getLogger(Empresa.class);

    private final EmpresaRepository repository;

    private final ModelMapper modelMapper;

    public EmpresaServiceImpl(EmpresaRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void setupCertificate(CertificateDTO dto, InputStream file, String empresaId) { //, MultipartFile file
        Empresa entity = repository.findById(empresaId).orElseThrow(() -> new ResourceNotFoundException("not found"));
        entity.setClaveCert(Util.getCipher(entity).encrypt(dto.getKey()));

        try (InputStream in = file) {
            // byte[] sourceBytes = IoUtils.toByteArray(in);
            //String encodedString = Base64.getEncoder().encodeToString(sourceBytes);
            entity.setCertFile(dto.getBody());
        } catch(IOException exception) {
            log.error("Saving cert - " + exception.getMessage());
        }
        //dto.getName();
        repository.save(entity);
    }

    @Override
    public EmpresaDTO getOne(String id) {
        Empresa entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("not found"));
        return mapToDTO(entity);
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
        entity.setMoneda("DOLAR");

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
