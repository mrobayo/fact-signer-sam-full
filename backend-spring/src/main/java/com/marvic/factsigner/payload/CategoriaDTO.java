package com.marvic.factsigner.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaDTO {

    private String id;

    @NotEmpty
    private String name;

    private boolean activo;

    @NotEmpty
    private String empresaId;

}