package com.marvic.factsigner.model.sistema;

import com.marvic.factsigner.model.sistema.extra.Categoria;
import com.marvic.factsigner.model.sistema.extra.Unidad;
import com.marvic.factsigner.model.sistema.types.ProductoTipo;
import com.marvic.factsigner.model.sistema.types.StockTipo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

@Entity
@Table(name="si_producto",
        uniqueConstraints = @UniqueConstraint(name="si_producto_uk", columnNames = {"empresa_id", "codigo"}))
public class Producto {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String codigo;

    @Column(nullable = false)
    private boolean activo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="empresa_id", nullable = false)
    private Empresa empresa;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ProductoTipo tipo;

    @Column(nullable = false, name = "codigo_iva")
    private Integer codigoIva;

    // Categorias
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="unidad_venta_id", nullable = false)
    private Unidad unidadVenta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="categoria_id", nullable = false)
    private Categoria categoria;

    // Control de stock
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name="control_stock", length = 20)
    private StockTipo control;

    @Column(nullable = false)
    private boolean vendido;

    @Column(nullable = false)
    private boolean comprado;

    // Lista de precio
    // private ListaPrecio listaPrecio;


}
