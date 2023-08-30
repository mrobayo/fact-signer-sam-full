package com.marvic.factsigner.repository;

import com.marvic.factsigner.model.sistema.extra.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GrupoRepository extends JpaRepository<Grupo, String> {

    Optional<Grupo> findByName(String name);

}
