package com.marvic.factsigner.repository;

import com.marvic.factsigner.model.comprobantes.Factura;
import com.marvic.factsigner.model.sistema.extra.Categoria;
import com.marvic.factsigner.model.sistema.extra.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FacturaRepository extends JpaRepository<Grupo, String> {

//    Optional<Factura> findByNumeroAndEmpresaId(String name, String empresaId);
//
//    List<Factura> findAllByEmpresaId(String empresaId);

}
