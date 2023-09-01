package com.marvic.factsigner.payload;

import com.marvic.factsigner.model.sistema.Producto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@NoArgsConstructor

public class DetalleFacturaDTO {

    private Integer id;

    @NotNull @Min(0)
    private Integer linea;

    @NotBlank @Size(min=1, max=20)
    private String codigoPrincipal;

    @Size(min=1, max=20)
    private String codigoAuxiliar;

    @NotBlank @Size(min=1, max=300)
    private String descripcion;

    @NotNull @Min(0)
    private BigDecimal cantidad;

    @NotNull @Min(0)
    private BigDecimal precioUnitario;

    @NotNull @Min(0)
    private BigDecimal descuento;

    @NotNull @Min(0)
    private BigDecimal precioTotalSinImpuesto;

    @Size(max=40)
    private String itemId;

    @NotNull @Min(0)
    private Integer codigoIva;

    @NotNull @Min(0)
    private BigDecimal valorIva;

}
