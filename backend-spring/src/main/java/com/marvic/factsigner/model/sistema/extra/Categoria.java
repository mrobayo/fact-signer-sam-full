package com.marvic.factsigner.model.sistema.extra;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

@Entity
@Table(name="si_categoria",
        uniqueConstraints = @UniqueConstraint(name="si_categoria_uk", columnNames = {"name"}))
public class Categoria {

    @Id
    @Column(length = 20)
    private String id;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false)
    private boolean activo;

}
