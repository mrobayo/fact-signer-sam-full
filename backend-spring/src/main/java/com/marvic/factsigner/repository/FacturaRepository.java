package com.marvic.factsigner.repository;

import com.marvic.factsigner.model.comprobantes.FacturaComp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FacturaRepository extends JpaRepository<FacturaComp, UUID> {

    Optional<FacturaComp> findByNameAndEmpresaId(String name, String empresaId);

    @Query(
            value="SELECT f FROM FacturaComp f LEFT JOIN FETCH f.comprador c WHERE f.empresa.id = ?1",
            countQuery="SELECT count(f.id) FROM FacturaComp f WHERE f.empresa.id = ?1")
    Page<FacturaComp> findAllByEmpresaId(String empresaId, Pageable paging);

}
