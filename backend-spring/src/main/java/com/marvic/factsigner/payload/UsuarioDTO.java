package com.marvic.factsigner.payload;

import com.marvic.factsigner.payload.validation.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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

    @ValidPassword
    private String newPassword;

    @NotNull
    private String[] roles;

    private String[] empresas;

    private LocalDate ultimaConexion;

    private boolean cuentaBloqueada = false;

    private boolean cambiarPasswd = true;

    private boolean nuncaExpira = false;

    @NotEmpty
    private String identidad;

    @NotEmpty
    private String email;

    @NotEmpty
    private String telefono;
}
