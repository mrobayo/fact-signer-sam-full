package com.marvic.factsigner.model.comprobantes.types;

import ec.gob.sri.types.SriEnumIdentidad;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor

@Embeddable
public class InfoFactura {

    @Column(name="fecha_emision")
    private LocalDate fechaEmision;

    @Column(name = "contribuyente_especial", length = 20)
    private String contribuyenteEspecial;

    @Column(nullable = false)
    private boolean obligadoContabilidad;

    @Column(name="tipo_comprador", length = 20)
    @Enumerated(EnumType.STRING)
    private SriEnumIdentidad tipoIdentificacionComprador;

    @Column(name="razon_social_comprador", length = 100)
    private String razonSocialComprador;

    @Column(name="identidad_comprador", length = 20)
    private String identificacionComprador;

    @Column(name="direccion_comprador", length = 100)
    private String direccionComprador;

    @Column(name = "guia_remision", length = 60)
    private String guiaRemision;

    @Column(name = "total_sinimpuestos", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalSinImpuestos;

    @Column(name = "total_descuento", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalDescuento;

    @Column(name = "propina", nullable = false, precision = 10, scale = 2)
    private BigDecimal propina;

    @Column(name = "importe_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal importeTotal;

    @Column(name="moneda", length = 20)
    private String moneda;

//    protected Factura.InfoFactura.TotalConImpuestos totalConImpuestos;
//    protected Factura.InfoFactura.compensacion compensaciones;
//    protected BigDecimal totalSubsidio; //***

//    protected String dirEstablecimiento; //***

//    protected Factura.InfoFactura.Pago pagos;

}
