package com.marvic.factsigner.repository;

import com.marvic.factsigner.model.comprobantes.extra.PuntoVenta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PuntoVentaRepository extends JpaRepository<PuntoVenta, String> {

    Optional<PuntoVenta> findByEstabAndPtoEmiAndEmpresaId(String estab, String ptoEmi, String empresaId);

    List<PuntoVenta> findAllByEmpresaId(String empresaId);

}
