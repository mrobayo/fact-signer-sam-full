package com.marvic.factsigner.payload;

import com.marvic.factsigner.model.sistema.Cliente;
import com.marvic.factsigner.model.sistema.extra.Grupo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {

    private String id;

    @NotEmpty
    private String name;

    private boolean activo;

    @NotEmpty
    private String tipo;

    @NotEmpty
    private String identidad;

    @NotEmpty
    private String nombres;

    @NotEmpty
    private String apellidos;

    @NotEmpty
    private String telefono;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String direccion;

    @NotEmpty
    private String pais;

    @NotEmpty
    private String ciudad;

    private String observacion;

    private LocalDate ultimaVenta;

    @NotEmpty
    private String contacto;

    @NotNull
    private boolean aseguradora;

    @NotNull
    private Cliente seguro;

    @NotEmpty
    private Grupo grupo;

}
