package com.marvic.factsigner.model.sistema.extra;

import javax.persistence.*;

@Entity
@Table(name="si_grupo_cliente",
        uniqueConstraints = @UniqueConstraint(name="si_grupo_cliente_uk", columnNames = {"name"}))
public class Grupo {

    @Id
    @Column(length = 20)
    private String id;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false)
    private boolean activo;

}
