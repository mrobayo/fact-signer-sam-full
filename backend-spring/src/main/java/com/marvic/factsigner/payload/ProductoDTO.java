package com.marvic.factsigner.payload;

import com.marvic.factsigner.model.sistema.extra.Categoria;
import com.marvic.factsigner.model.sistema.extra.Unidad;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDTO {

    private String id;

    @NotEmpty
    private String name;

    private boolean activo;

    private String codigo;

    @NotEmpty
    private String tipo;

    @NotNull
    private Integer codigoIva;

    // Categorias
    @NotNull
    private Unidad unidadVenta;

    @NotNull
    private Categoria categoria;

    // Control de stock
    @NotNull
    private String control;

    private boolean vendido;

    private boolean comprado;

}
