package com.marvic.factsigner.repository;

import com.marvic.factsigner.model.comprobantes.FacturaComp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FacturaRepository extends JpaRepository<FacturaComp, UUID> {

    Optional<FacturaComp> findByNameAndEmpresaId(String name, String empresaId);

    List<FacturaComp> findAllByEmpresaId(String empresaId);

}
