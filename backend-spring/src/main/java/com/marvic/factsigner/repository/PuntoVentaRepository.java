package com.marvic.factsigner.repository;

import com.marvic.factsigner.model.comprobantes.extra.PuntoVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PuntoVentaRepository extends JpaRepository<PuntoVenta, String> {

    Optional<PuntoVenta> findByEstabAndPtoEmiAndEmpresaId(String estab, String ptoEmi, String empresaId);

    List<PuntoVenta> findAllByEmpresaId(String empresaId);

    @Query("SELECT p FROM PuntoVenta p JOIN FETCH p.empresa " +
            "JOIN Usuario u on (u.id = ?1 and u.empresas like '%' || p.empresa.id || '%' and p.activo=true) " +
            "WHERE p.empresa.id = ?2")
    Optional<PuntoVenta> findPuntoVentaAutorizado(String usuarioId, String empresaId);

}
