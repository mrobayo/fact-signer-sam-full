package com.marvic.factsigner.service;

import com.marvic.factsigner.payload.UsuarioDTO;

import java.util.List;

public interface UsuarioService {

    UsuarioDTO getUsuario(Integer UsuarioId);

    List<UsuarioDTO> getUsuarios();

    UsuarioDTO createUsuario(UsuarioDTO UsuarioDTO);

    void deleteUsuario(Integer UsuarioId);

    UsuarioDTO updateUsuario(UsuarioDTO UsuarioDTO, Integer UsuarioId);

}
