package com.marvic.factsigner.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {

    private String id;

    @NotEmpty
    private String username;

    @NotEmpty
    private String name;

    private String password;

    private String roles;

    private String empresas;

    private LocalDate ultimaConexion;

    private boolean cuentaBloqueada = false;

    private boolean cambiarPasswd = true;

    private LocalDate ultimoPasswd;

    private boolean nuncaExpira = false;

    @NotEmpty
    private String identidad;

    @NotEmpty
    private String email;

    @NotEmpty
    private String telefono;
}
