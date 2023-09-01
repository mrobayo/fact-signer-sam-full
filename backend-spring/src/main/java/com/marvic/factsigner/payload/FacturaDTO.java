package com.marvic.factsigner.payload;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
// @SuperBuilder
public class FacturaDTO extends ComprobanteDTO {

    @Size(max=40)
    private String id;

    @NotBlank @Size(max=20)
    private String tipoFactura;

    @Min(0)
    private BigDecimal totalDescuento;

    @Min(0)
    private BigDecimal propina;

    @Min(0)
    private BigDecimal totalSinImpuestos;

    @Min(0)
    private BigDecimal importeTotal;

    private BigDecimal valorRetIva;

    private BigDecimal valorRetRenta;

    @Size(max=40)
    private String guiaRemision;

    @NotBlank @Size(max=40)
    private String compradorId;

    private List<DetalleFacturaDTO> detalles;

}
