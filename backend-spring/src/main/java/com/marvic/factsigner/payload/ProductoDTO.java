package com.marvic.factsigner.payload;

import com.marvic.factsigner.model.sistema.extra.Categoria;
import com.marvic.factsigner.model.sistema.extra.Unidad;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDTO {

    private String id;

    @NotBlank @Size(min=1, max=100)
    private String name;

    @NotBlank @Size(min=1, max=60)
    private String codigo;

    private boolean activo;

    @NotBlank @Size(max=20)
    private String empresaId;

    @NotBlank @Size(max=20)
    private String tipo;

    @NotNull @Min(0)
    private Integer codigoIva;

    @NotNull @Size(max=20)
    private String unidadVentaId;

    @NotNull @Min(0)
    private Integer categoriaId;

    @NotNull @Size(max=20)
    private String control;

    private boolean vendido;

    private boolean comprado;

}
