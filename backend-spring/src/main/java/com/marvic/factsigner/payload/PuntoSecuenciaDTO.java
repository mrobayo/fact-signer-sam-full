package com.marvic.factsigner.payload;

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
