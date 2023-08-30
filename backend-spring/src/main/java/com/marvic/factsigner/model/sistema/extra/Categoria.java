package com.marvic.factsigner.model.sistema.extra;

import com.marvic.factsigner.model.sistema.Empresa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name="si_categoria",
        uniqueConstraints = @UniqueConstraint(name="si_categoria_uk", columnNames = {"empresa_id", "name"}))
public class Categoria {

    @Id()
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "categoria_id_seq")
    @SequenceGenerator(name = "categoria_id_seq", sequenceName = "categoria_id_seq")
    private Integer id;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean activo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="empresa_id", nullable = false, updatable = false)
    private Empresa empresa;

}
