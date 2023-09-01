package com.marvic.factsigner.payload.sistema;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpresaDTO {

    private String id;

    @NotBlank @Size(min=1, max=100)
    private String name;

    private boolean activo;

    private String ruc;

    @NotBlank @Size(min=1, max=20)
    private String color;

    @NotBlank @Size(min=1, max=100)
    private String comercial;

    @Size(max=20)
    private String ambiente;

    @NotNull
    private BigDecimal tarifaIva;

    @Size(max=20)
    private String numeroContribuyente;

    @NotNull
    private boolean obligado;

    @Size(max=300)
    private String logo;

    @NotBlank @Size(min=1, max=100)
    private String direccion;

    @NotBlank @Size(min=7, max=100)
    private String telefono;

    private String moneda;

}
