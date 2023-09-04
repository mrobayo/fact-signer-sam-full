package com.marvic.factsigner.repository;

import com.marvic.factsigner.model.comprobantes.extra.PuntoSecuencia;
import ec.gob.sri.types.SriTipoDoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;

import java.util.List;
import java.util.Optional;

public interface PuntoSecuenciaRepository extends JpaRepository<PuntoSecuencia, String> {

    // https://www.objectdb.com/java/jpa/persistence/lock#Pessimistic_Locking_

    //    @Lock(LockModeType.PESSIMISTIC_READ)
    //    @QueryHints({@QueryHint(name = "jakarta.persistence.lock.timeout", value = "3000")})
    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    @Query("SELECT c FROM PuntoSecuencia c WHERE c.id = ?1")
    Optional<PuntoSecuencia> lockById(String puntoSecuenciaId);

    List<PuntoSecuencia> findAllByPuntoVentaId(String puntoVentaId);

    Optional<PuntoSecuencia> findByPuntoVentaIdAndTipoDoc(String puntoVentaId, SriTipoDoc tipoDoc);

}
