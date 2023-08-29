package com.marvic.factsigner.payload;

import com.marvic.factsigner.model.sistema.types.UnidadTipo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
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

}
