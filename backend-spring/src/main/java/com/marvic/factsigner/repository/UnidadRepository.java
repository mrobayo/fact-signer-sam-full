package com.marvic.factsigner.repository;

import com.marvic.factsigner.model.sistema.extra.Unidad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UnidadRepository extends JpaRepository<Unidad, String> {

}
