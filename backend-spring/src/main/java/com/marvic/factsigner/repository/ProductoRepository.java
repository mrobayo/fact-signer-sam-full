package com.marvic.factsigner.repository;

import com.marvic.factsigner.model.sistema.Producto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductoRepository extends JpaRepository<Producto, UUID> {

    List<Producto> findAllByEmpresaId(String empresaId, Pageable paging);

    Optional<Producto> findByEmpresaIdAndCodigo(String empresaId, String codigo);

}
