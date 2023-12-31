package com.marvic.factsigner.model.sistema;

import com.marvic.factsigner.model.sistema.extra.Grupo;
import ec.gob.sri.types.SriEnumIdentidad;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Valid UUID
 *
 * facade00-0000-4000-a000-000000000000
 * decade00-0000-4000-a000-000000000000
 * ad0be000-0000-4000-a000-000000000000
 * c0c0a000-0000-4000-a000-000000000000
 * 5ca1ab1e-0000-4000-a000-000000000000
 * f100ded0-0000-4000-a000-000000000000
 */
@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name="si_cliente"
        , uniqueConstraints = @UniqueConstraint(name="si_cliente_ide_uk", columnNames = {"tipo", "identidad"})
)
public class Cliente {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private SriEnumIdentidad tipo;

    @Column(nullable = false, length = 20)
    private String identidad;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean activo;

    @Column(nullable = false, length = 40)
    private String nombres;

    @Column(nullable = false, length = 40)
    private String apellidos;

    @Column
    private LocalDate birthday;

    @Column(nullable = false, length = 60)
    private String telefono;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 100)
    private String direccion;

    @Column(nullable = false, length = 20)
    private String pais;

    @Column(nullable = false, length = 20)
    private String ciudad;

    @Column(length = 300)
    private String observacion;

    @Column(name="ultima_venta")
    private LocalDate ultimaVenta;

    @Column(length = 100)
    private String contacto;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean aseguradora;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="grupo_id", nullable = false)
    private Grupo grupo;

}
