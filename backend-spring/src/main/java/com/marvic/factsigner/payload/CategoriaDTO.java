package com.marvic.factsigner.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaDTO {

    @Size(max=20)
    private String id;

    @NotBlank @Size(min=1, max=20)
    private String name;

    private boolean activo;

    @NotBlank @Size(max=20)
    private String empresaId;

}
