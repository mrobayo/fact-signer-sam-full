package com.marvic.factsigner.service;

import com.marvic.factsigner.payload.UsuarioDTO;

import java.util.List;

public interface UsuarioService {

    UsuarioDTO getOne(String id);

    List<UsuarioDTO> getMany();

    UsuarioDTO create(UsuarioDTO dto);

    void delete(String id);

    UsuarioDTO update(UsuarioDTO dto, String id);

}