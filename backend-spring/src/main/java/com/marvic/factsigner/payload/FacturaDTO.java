package com.marvic.factsigner.payload;

import com.marvic.factsigner.model.comprobantes.types.Pago;
import com.marvic.factsigner.model.comprobantes.types.TotalImpuesto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
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
    private String clienteId;

    private String clienteName;

    private String tipoIdentificacionComprador;

    private String razonSocialComprador;

    private String identificacionComprador;

    private String direccionComprador;

    @NotNull
    private List<DetalleFacturaDTO> detalles;

    private Map<String, String> infoAdicional;

    @NotNull
    private Map<String, Pago> pagos;

    @NotNull
    private TotalImpuesto totalIva;

    @NotNull
    private TotalImpuesto totalIce;

    private String observacion;

}
