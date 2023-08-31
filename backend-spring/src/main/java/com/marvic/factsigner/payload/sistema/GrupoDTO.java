package com.marvic.factsigner.payload.sistema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrupoDTO {

    private String id;

    @NotBlank @Size(min=1)
    private String name;

    private boolean activo;

    private String seguroId;

}
