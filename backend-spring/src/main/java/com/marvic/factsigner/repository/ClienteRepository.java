package com.marvic.factsigner.repository;

import com.marvic.factsigner.model.sistema.Cliente;
import ec.gob.sri.types.SriEnumIdentidad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, String> {

    Optional<Cliente> findByName(String name);

    Optional<Cliente> findByIdentidadAndTipo(String identidad, SriEnumIdentidad tipo);

}
