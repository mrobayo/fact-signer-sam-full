package com.marvic.factsigner.repository;

import com.marvic.factsigner.model.comprobantes.extra.PuntoSecuencia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PuntoSecuenciaRepository extends JpaRepository<PuntoSecuencia, String> {

}
