package com.marvic.factsigner.model.sistema;

import com.marvic.factsigner.model.sistema.extra.GrupoCliente;
import ec.gob.sri.types.SriEnumIdentidad;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

@Entity
@Table(name="si_cliente",
        uniqueConstraints = @UniqueConstraint(name="si_cliente_ide_uk", columnNames = {"tipo", "identidad"}))
public class Cliente {

    @Id
    private UUID id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private SriEnumIdentidad tipo;

    @Column(nullable = false, length = 20)
    private String identidad;

    @Column(nullable = false)
    private Boolean activo;

    @Column(nullable = false, length = 60)
    private String nombres;

    @Column(nullable = false, length = 60)
    private String apellidos;

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

    @Column(nullable = false, length = 300)
    private String observacion;

    @Column(name="ultima_venta")
    private LocalDate ultimaVenta;

    @Column(length = 100)
    private String contacto;

    @Column(nullable = false)
    private Boolean aseguradora;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="seguro_id", nullable = false)
    private Cliente seguro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="grupo_id", nullable = false)
    private GrupoCliente grupo;

}
