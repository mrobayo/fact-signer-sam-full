package com.marvic.factsigner.model.comprobantes.types;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor

@Embeddable
public class Impuesto {

    // @Column
    private Integer codigo;

    // @Column(precision = 6, scale = 2)
    private BigDecimal tarifa;

    // @Column(precision = 14, scale = 2)
    private BigDecimal baseImponible;

    // @Column(precision = 14, scale = 2)
    private BigDecimal valor;

}
