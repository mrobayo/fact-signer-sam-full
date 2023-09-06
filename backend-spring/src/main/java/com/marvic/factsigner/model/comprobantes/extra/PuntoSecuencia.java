package com.marvic.factsigner.model.comprobantes.extra;

import ec.gob.sri.types.SriTipoDoc;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="si_punto_secuencia",
        uniqueConstraints = @UniqueConstraint(name="si_punto_secuencia_uk", columnNames = {"tipo_doc", "punto_venta_id"}))
public class PuntoSecuencia {

    @Id
    @Column(length=40)
    private String id;

    @Version
    @Column(name="version", columnDefinition = "integer default 0", nullable = false)
    private Integer version;

    @Column(nullable = false, name = "tipo_doc", length = 20)
    @Enumerated(EnumType.STRING)
    private SriTipoDoc tipoDoc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="punto_venta_id", nullable = false)
    private PuntoVenta puntoVenta;

    @Column(nullable = false)
    private Integer secuencia;

}
