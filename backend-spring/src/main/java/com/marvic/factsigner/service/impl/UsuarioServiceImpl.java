package com.marvic.factsigner.service.impl;

import com.marvic.factsigner.model.sistema.Usuario;
import com.marvic.factsigner.payload.UsuarioDTO;
import com.marvic.factsigner.repository.UsuarioRepository;
import com.marvic.factsigner.service.UsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService  {
    private UsuarioRepository repository;

    private ModelMapper modelMapper;

    @Override
    public UsuarioDTO getUsuario(Integer UsuarioId) {
        return null;
    }

    @Override
    public List<UsuarioDTO> getUsuarios() {
        return null;
    }

    @Override
    public UsuarioDTO createUsuario(UsuarioDTO UsuarioDTO) {
        return null;
    }

    @Override
    public void deleteUsuario(Integer UsuarioId) {

    }

    @Override
    public UsuarioDTO updateUsuario(UsuarioDTO UsuarioDTO, Integer UsuarioId) {
        return null;
    }

    private Usuario mapToEntity(UsuarioDTO dto) {
        return modelMapper.map(dto, Usuario.class);
    }

    private UsuarioDTO mapToDTO(Usuario Cliente){
        return modelMapper.map(Cliente, UsuarioDTO.class);
    }

}
