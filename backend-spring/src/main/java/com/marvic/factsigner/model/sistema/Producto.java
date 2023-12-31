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

@Entity
@Table(name="si_producto",
        uniqueConstraints = @UniqueConstraint(name="si_producto_uk", columnNames = {"empresa_id", "codigo"}))
public class Producto {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 60)
    private String codigo;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean activo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="empresa_id", nullable = false)
    private Empresa empresa;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(20) default 'BIEN'")
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
    @Column(nullable = false, name="control_stock", columnDefinition = "varchar(20) default 'ALMACENABLE'")
    private StockTipo control;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean vendido;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean comprado;

    // Lista de precio
    // private ListaPrecio listaPrecio;

}
