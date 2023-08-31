package com.marvic.factsigner.model.sistema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "si_usuario",
       uniqueConstraints = {
        @UniqueConstraint(name="si_usuario_id_uk", columnNames = {"identidad"}),
        @UniqueConstraint(name="si_usuario_email_uk", columnNames = {"email"})
        })
public class Usuario {

    @Id @Column(length = 20)
    private String id;

    @Column(name="id", updatable = false, insertable = false)
    private String username;

    @Column(nullable = false, length = 60)
    private String password;

    @Column(nullable = false, length = 60)
    private String name;

    @Column(nullable = false, length = 300)
    private String roles;

    @Column(length = 300)
    private String empresas;

    @Column(length = 300)
    private String passwordHistory;

    @Column
    private LocalDate ultimaConexion;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean cuentaBloqueada = false;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean cambiarPasswd = true;

    private LocalDate ultimoPasswd;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean nuncaExpira = false;

    @Column(nullable = false, length = 20)
    private String identidad;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 60)
    private String telefono;

}
