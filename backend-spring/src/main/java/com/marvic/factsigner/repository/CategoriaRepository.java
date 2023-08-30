package com.marvic.factsigner.repository;

import com.marvic.factsigner.model.sistema.extra.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    Optional<Categoria> findByNameAndEmpresaId(String name, String empresaId);

}
