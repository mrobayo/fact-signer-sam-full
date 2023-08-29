package com.marvic.factsigner.payload;

import com.marvic.factsigner.model.comprobantes.extra.PuntoVenta;
import ec.gob.sri.types.SriTipoDoc;

import javax.persistence.*;
import java.util.UUID;

public class PuntoSecuenciaDTO {

    private String tipoDoc;

    private Integer secuencia;

}
