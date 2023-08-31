package com.marvic.factsigner.model.comprobantes.extra;
import com.marvic.factsigner.model.sistema.Empresa;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name="si_punto_venta",
        uniqueConstraints = @UniqueConstraint(name="si_punto_venta_uk", columnNames = {"empresa_id", "estab", "pto_emi"}))
public class PuntoVenta {

    @Id
    private String id;

    @Column(name="id", updatable = false, insertable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean activo;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean matriz = false;

    @Column(nullable = false, length = 3)
    private String estab;

    @Column(nullable = false, name="pto_emi", length = 3)
    private String ptoEmi;

    @Column(nullable = false, length = 100)
    private String direccion;

    @Column(nullable = false, length = 60)
    private String telefono;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="empresa_id", nullable = false)
    private Empresa empresa;

}
