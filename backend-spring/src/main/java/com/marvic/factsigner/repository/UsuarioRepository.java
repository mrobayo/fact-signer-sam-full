package com.marvic.factsigner.repository;

import com.marvic.factsigner.model.sistema.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    Optional<Usuario> findUsuarioByIdentidad(String identidad);

}
