package com.marvic.factsigner.model.sistema.extra;

import com.marvic.factsigner.model.sistema.types.UnidadTipo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name="si_unidad",
        uniqueConstraints = @UniqueConstraint(name="si_unidad_uk", columnNames = {"name"}))
public class Unidad {

    @Id
    @Column(length = 20)
    private String id;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private UnidadTipo tipo; // Peso, Volumen, Empaque

    @Column(nullable = false)
    private boolean base; // Unidad principal/base de este tipo

    @Column(nullable = false, precision = 10, scale = 3)
    private BigDecimal factor; // Factor de conversion con respecto a la unidad base

}
