package com.marvic.factsigner.payload.sistema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {

    private String id;

    @NotBlank @Size(min=2, max=100)
    private String name;

    private boolean activo;

    @NotBlank @Size(min=3, max=3)
    private String tipo;

    @NotBlank @Size(min=3, max=20)
    private String identidad;

    @NotBlank @Size(min=2, max=40)
    private String nombres;

    @NotBlank @Size(min=2, max=40)
    private String apellidos;

    @NotBlank
    private String telefono;

    @NotBlank @Email @Size(max=100)
    private String email;

    @NotBlank @Size(max=100)
    private String direccion;

    @NotBlank @Size(max=20)
    private String pais;

    @NotBlank @Size(max=20)
    private String ciudad;

    @Size(max=300)
    private String observacion;

    private LocalDate ultimaVenta;

    @NotBlank @Size(max=100)
    private String contacto;

    @NotNull
    private boolean aseguradora;

    private String grupoId;

}
