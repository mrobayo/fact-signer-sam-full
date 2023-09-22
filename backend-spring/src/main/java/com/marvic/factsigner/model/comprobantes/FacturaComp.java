package com.marvic.factsigner.model.comprobantes;

import com.marvic.factsigner.model.comprobantes.types.*;
import com.marvic.factsigner.model.sistema.Cliente;
import com.marvic.factsigner.util.HashMapConverter;
import com.marvic.factsigner.util.PagosListConverter;
import com.marvic.factsigner.util.PagosMapConverter;
import ec.gob.sri.types.SriEnumIdentidad;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
// @SuperBuilder

@Entity
@Table(name="ct_factura"
    , uniqueConstraints = @UniqueConstraint(name="ct_factura_numero_uk", columnNames = {"numero", "empresa_id"})
)
public class FacturaComp extends Comprobante {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID id;

    @Column(name = "contribuyente_especial", length = 20)
    private String contribuyenteEspecial;

    @Column(nullable = false)
    private boolean obligadoContabilidad;

    @Column(name="tipo_cliente", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private SriEnumIdentidad tipoIdentificacionComprador;

    @Column(name="razon_social_cliente", length = 100, nullable = false)
    private String razonSocialComprador;

    @Column(name="identidad_cliente", length = 20, nullable = false)
    private String identificacionComprador;

    @Column(name="direccion_cliente", length = 100)
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
    //********

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_factura", nullable = false, columnDefinition = "varchar(20) default 'VENTA_LOCAL'")
    private TipoFactura tipoFactura;

    @Column(name = "valor_retiva", precision = 10, scale = 2)
    private BigDecimal valorRetIva;

    @Column(name = "valor_retrenta", precision = 10, scale = 2)
    private BigDecimal valorRetRenta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cliente_id")
    private Cliente cliente;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name="ct_factura_detalles")
    @OrderColumn(name = "linea")
    private List<DetalleFactura> detalles;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "codigoPorcentaje", column = @Column(name = "codigo_iva")),
            @AttributeOverride( name = "descuentoAdicional", column = @Column(name = "descuento_iva", precision = 14, scale = 2)),
            @AttributeOverride( name = "baseImponible", column = @Column(name = "base_imponible_iva", precision = 14, scale = 2)),
            @AttributeOverride( name = "valor", column = @Column(name = "valor_iva", precision = 14, scale = 2))
    })
    private TotalImpuesto totalIva;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "codigoPorcentaje", column = @Column(name = "codigo_ice")),
            @AttributeOverride( name = "descuentoAdicional", column = @Column(name = "descuento_ice", precision = 14, scale = 2)),
            @AttributeOverride( name = "baseImponible", column = @Column(name = "base_imponible_ice", precision = 14, scale = 2)),
            @AttributeOverride( name = "valor", column = @Column(name = "valor_ice", precision = 14, scale = 2))
    })
    private TotalImpuesto totalIce;

    //private AuditObject audit;

    @Convert(converter = HashMapConverter.class)
    @Column(name="info_adicional", length = 4000)
    private Map<String, String> infoAdicional;

    @Convert(converter = PagosListConverter.class)
    @Column(name="pagos", length = 4000)
    //private Map<String, Pago> pagos;
    private List<Pago> pagos;

//    @Column(name = "total_descuento", nullable = false, precision = 10, scale = 2)
//    private BigDecimal totalDescuento;
//    @Column(name = "propina", nullable = false, precision = 10, scale = 2)
//    private BigDecimal propina;
//    @Column(name = "total_sinimpuestos", nullable = false, precision = 10, scale = 2)
//    private BigDecimal totalSinImpuestos;
//    @Column(name = "importe_total", nullable = false, precision = 10, scale = 2)
//    private BigDecimal importeTotal;
//    @Column(name = "guia_remision", length = 60)
//    private String guiaRemision;

}
