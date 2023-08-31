package com.marvic.factsigner.model.comprobantes;

import javax.persistence.Column;

public class Retencion {

    @Column(name = "periodo_id", length = 20)
    private String periodoFiscal;

}
