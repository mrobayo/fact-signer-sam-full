package com.marvic.factsigner.payload.sistema;

import com.marvic.factsigner.payload.validation.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {

    private String id;

    @NotEmpty @Size(min=1, max=20)
    private String username;

    @NotEmpty @Size(min=1, max=60)
    private String name;

    @ValidPassword @Size(min=1, max=60)
    private String newPassword;

    @NotNull
    private String[] roles;

    private String[] empresas;

    private LocalDate ultimaConexion;

    private boolean cuentaBloqueada = false;

    private boolean cambiarPasswd = true;

    private boolean nuncaExpira = false;

    @NotEmpty @Size(min=1, max=20)
    private String identidad;

    @NotEmpty @Size(min=1, max=100)
    private String email;

    @NotEmpty @Size(min=1, max=60)
    private String telefono;
}
