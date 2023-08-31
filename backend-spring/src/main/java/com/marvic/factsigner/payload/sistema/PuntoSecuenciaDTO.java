package com.marvic.factsigner.payload.sistema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PuntoSecuenciaDTO {

    private String tipoDoc;

    private Integer secuencia;

    private String puntoVentaId;

}
