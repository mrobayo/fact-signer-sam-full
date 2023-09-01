package com.marvic.factsigner.model.comprobantes;

import com.marvic.factsigner.model.sistema.Producto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor

@Entity
@Table(name="ct_factura_detalles")
public class DetalleFactura {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "factura_detalle_id_seq")
    @SequenceGenerator(name = "factura_detalle_id_seq", sequenceName = "factura_detalle_id_seq", initialValue = 10001)
    private Integer id;

    @Column(nullable = false)
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

    @Column(nullable = false, name = "codigo_iva")
    private Integer codigoIva;

    @Column(precision = 14, scale = 2, nullable = false)
    private BigDecimal valorIva;

}
