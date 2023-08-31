package com.marvic.factsigner.payload.sistema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnidadDTO {

    private String id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String tipo;

    @NotNull
    private boolean base;

    private BigDecimal factor;

    private boolean activo;

}
