package com.marvic.factsigner.model.comprobantes.types;

public enum EstadoTipo {

    BORRADOR,
    EMITIDO,    // <- EMITIDO -> LISTO (Revisado/Aprobado Supervisor)

    NO_AUTORIZADO,  // -> AUTORIZADO

    // Estado Final
    AUTORIZADO,     // <- (FINAL)
    ANULADO;        // <- (FINAL)
}
