package com.marvic.factsigner.model.comprobantes.types;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor

@Embeddable
public class TotalImpuesto {

    private Integer codigoPorcentaje; // >2</ codigoPorcentaje>
    private BigDecimal descuentoAdicional; // >5.00</descuentoAdicional>
    private BigDecimal baseImponible; // >68.19</ baseImponible >
    private BigDecimal valor; // >7.58</valor >

}
