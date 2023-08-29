package com.marvic.factsigner.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpresaDTO {

    private String id;

    @NotEmpty
    private String name;

    private boolean activo;

    @NotEmpty
    private String ruc;

    @NotEmpty
    private String color;

    @NotEmpty
    private String comercial;

    @NotEmpty
    private String ambiente;

    @NotEmpty
    private BigDecimal tarifaIva;

    private String numeroContribuyente;

    @NotNull
    private boolean obligado;

    private String logo;

    @NotEmpty
    private String direccion;

    @NotEmpty
    private String telefono;

}
