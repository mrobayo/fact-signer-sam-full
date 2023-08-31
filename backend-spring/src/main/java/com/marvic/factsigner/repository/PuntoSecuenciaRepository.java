package com.marvic.factsigner.repository;

import com.marvic.factsigner.model.comprobantes.extra.PuntoSecuencia;
import ec.gob.sri.types.SriTipoDoc;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PuntoSecuenciaRepository extends JpaRepository<PuntoSecuencia, String> {

    List<PuntoSecuencia> findAllByPuntoVentaId(String puntoVentaId);

    Optional<PuntoSecuencia> findByPuntoVentaIdAndTipoDoc(String puntoVentaId, SriTipoDoc tipoDoc);

}
