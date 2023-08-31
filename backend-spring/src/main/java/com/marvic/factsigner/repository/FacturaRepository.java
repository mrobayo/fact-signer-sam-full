package com.marvic.factsigner.repository;

import com.marvic.factsigner.model.comprobantes.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FacturaRepository extends JpaRepository<Factura, UUID> {

    Optional<Factura> findByNameAndEmpresaId(String name, String empresaId);

    // List<Factura> findAllByEmpresaId(String empresaId);

}
