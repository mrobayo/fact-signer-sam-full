package com.marvic.factsigner.repository;

import com.marvic.factsigner.model.sistema.Cliente;
import ec.gob.sri.types.SriEnumIdentidad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {

    Optional<Cliente> findByName(String name);

    Optional<Cliente> findByIdentidadAndTipo(String identidad, SriEnumIdentidad tipo);

    @Query(
            value="SELECT c FROM Cliente c WHERE c.name LIKE :search AND (:activo IS NULL OR c.activo = :activo)",
            countQuery="SELECT count(1) FROM Cliente c WHERE c.name LIKE :search AND (:activo IS NULL OR c.activo = :activo)")
    Page<Cliente> findAllByNameAndActivo(@Param("search") String search, @Param("activo") Boolean activo, Pageable paging);

    @Query(
            value="SELECT c FROM Cliente c WHERE c.identidad LIKE :search AND (:activo IS NULL OR c.activo = :activo)",
            countQuery="SELECT count(1) FROM Cliente c WHERE c.identidad LIKE :search AND (:activo IS NULL OR c.activo = :activo)")
    Page<Cliente> findAllByIdentidadAndActivo(@Param("search") String search, @Param("activo") Boolean activo, Pageable paging);

}
