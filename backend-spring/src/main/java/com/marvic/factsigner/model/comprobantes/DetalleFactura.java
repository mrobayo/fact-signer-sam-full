package com.marvic.factsigner.model.comprobantes;

import com.marvic.factsigner.model.comprobantes.types.Impuesto;
import com.marvic.factsigner.model.sistema.Producto;

import com.marvic.factsigner.util.HashMapConverter;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Map;

@Data
@NoArgsConstructor

//@Entity
//@Table(name="ct_factura_detalles")
@Embeddable
public class DetalleFactura {

//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "factura_detalle_id_seq")
//    @SequenceGenerator(name = "factura_detalle_id_seq", sequenceName = "factura_detalle_id_seq", initialValue = 10001)
//    private Integer id;

    @Column(name="linea", nullable = false, insertable = false, updatable = false)
    private Integer linea;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "factura_id", nullable = false)
//    private Factura factura;

    @Column(name = "codigo_principal", length = 20, nullable = false)
    private String codigoPrincipal;
    @Column(name = "codigo_auxiliar", length = 20)
    private String codigoAuxiliar;
    @Column(length = 300, nullable = false)
    private String descripcion;

    @Column(precision = 18, scale = 6, nullable = false)
    private BigDecimal cantidad;
    @Column(precision = 18, scale = 6, nullable = false)
    private BigDecimal precioUnitario;

    @Column(precision = 14, scale = 2, nullable = false)
    private BigDecimal descuento;

    @Column(precision = 14, scale = 2, nullable = false)
    private BigDecimal precioTotalSinImpuesto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id")
    private Producto item;

    // ICE
    @Embedded
    @AttributeOverrides({
        @AttributeOverride( name = "codigo", column = @Column(name = "codigo_ice")),
        @AttributeOverride( name = "tarifa", column = @Column(name = "tarifa_ice", precision = 6, scale = 2)),
        @AttributeOverride( name = "baseImponible", column = @Column(name = "base_imponible_ice", precision = 14, scale = 2)),
        @AttributeOverride( name = "valor", column = @Column(name = "valor_ice", precision = 14, scale = 2))
    })
    Impuesto ice;

//    @Column(name = "codigo_ice")
//    private Integer codigoIce;
//
//    @Column(name = "tarifa_ice")
//    private BigDecimal tarifaIce;
//
//    @Column(name = "base_imponible_ice", precision = 14, scale = 2)
//    private BigDecimal baseImponibleIce;
//
//    @Column(name = "valor_ice", precision = 14, scale = 2)
//    private BigDecimal valorIce;

    // IVA
    @Embedded
    @AttributeOverrides({
        @AttributeOverride( name = "codigo", column = @Column(name = "codigo_iva")),
        @AttributeOverride( name = "tarifa", column = @Column(name = "tarifa_iva", precision = 6, scale = 2)),
        @AttributeOverride( name = "baseImponible", column = @Column(name = "base_imponible_iva", precision = 14, scale = 2)),
        @AttributeOverride( name = "valor", column = @Column(name = "valor_iva", precision = 14, scale = 2))
    })
    Impuesto iva;

//    @Column(name = "codigo_iva")
//    private Integer codigoIva;
//
//    @Column(name = "tarifa_iva")
//    private BigDecimal tarifaIva;
//
//    @Column(name = "base_imponible_iva", precision = 14, scale = 2)
//    private BigDecimal baseImponibleIva;
//
//    @Column(name = "valor_iva", precision = 14, scale = 2)
//    private BigDecimal valorIva;

    // Detalles adicionales

    @Convert(converter = HashMapConverter.class)
    @Column(name="detalles_adicionales", length = 4000)
    private Map<String, String> detallesAdicionales;

}
