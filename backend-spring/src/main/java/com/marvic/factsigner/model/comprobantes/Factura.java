package com.marvic.factsigner.model.comprobantes;

import com.marvic.factsigner.model.comprobantes.types.TipoFactura;
import com.marvic.factsigner.model.sistema.Cliente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
// @SuperBuilder

@Entity
@Table(name="ct_factura"
    , uniqueConstraints = @UniqueConstraint(name="ct_factura_numero_uk", columnNames = {"numero", "empresa_id"})
)
public class Factura extends Comprobante {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_factura", nullable = false, columnDefinition = "varchar(20) default 'VENTA_LOCAL'")
    private TipoFactura tipoFactura;

    @Column(name = "total_descuento", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalDescuento;

    @Column(name = "propina", nullable = false, precision = 10, scale = 2)
    private BigDecimal propina;

    @Column(name = "total_sinimpuestos", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalSinImpuestos;

    @Column(name = "importe_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal importeTotal;

    @Column(name = "valor_retiva", precision = 10, scale = 2)
    private BigDecimal valorRetIva;

    @Column(name = "valor_retrenta", precision = 10, scale = 2)
    private BigDecimal valorRetRenta;

    @Column(name = "guia_remision", length = 60)
    private String guiaRemision;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="comprador_id", nullable = false)
    private Cliente comprador;

    //private Pago

    //private AuditObject audit;
    //private List<DatoAdicional> adicionales = new ArrayList<DatoAdicional>();

}
