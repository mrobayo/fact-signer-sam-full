package com.marvic.factsigner.repository;

import com.marvic.factsigner.model.sistema.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    Optional<Usuario> findByIdentidad(String identidad);

    Optional<Usuario> findByName(String name);

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByUsernameOrEmail(String username, String email);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    @Query("SELECT p.id FROM PuntoVenta p JOIN Usuario u on (u.id = ?1 and u.empresas like '%' || p.empresa.id || '%' and p.activo=true)")
    List<String> findPuntoAutorizados(String usuarioId);

}
