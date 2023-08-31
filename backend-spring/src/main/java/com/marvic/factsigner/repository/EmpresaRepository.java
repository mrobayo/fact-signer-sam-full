package com.marvic.factsigner.repository;

import com.marvic.factsigner.model.sistema.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmpresaRepository extends JpaRepository<Empresa, String> {

    Optional<Empresa> findByName(String name);

}
