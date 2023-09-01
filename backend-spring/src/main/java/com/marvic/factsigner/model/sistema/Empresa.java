package com.marvic.factsigner.model.sistema;
import ec.gob.sri.types.SriAmbiente;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name="si_empresa",
        uniqueConstraints = @UniqueConstraint(name="si_empresa_name_uk", columnNames = {"name"}))
public class Empresa {

    @Id
    @Column(name = "id", nullable = false, length = 20)
    private String id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name="id", updatable = false, insertable = false)
    private String ruc;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean activo;

    @Column(nullable = false, columnDefinition = "varchar(20) default '#EEEEEE'")
    private String color;

    @Column(nullable = false, length = 100)
    private String comercial;

    @Column(nullable = false, columnDefinition = "varchar(20) default 'PRUEBAS'")
    @Enumerated(EnumType.STRING)
    private SriAmbiente ambiente;

    @Column(name = "tarifa_iva", nullable = false)
    private BigDecimal tarifaIva;

    @Column(name = "numero_contribuyente", length = 20)
    private String numeroContribuyente;   // Num de Contribuyente Especial

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean obligado;   // Obligado a llevar contabilidad

    @Column(length = 300)
    private String logo;

    @Column(nullable = false, length = 100)
    private String direccion;

    @Column(length = 300)
    private String certFile;

    @Column(length = 100)
    private String claveCert;

    @Column(length = 100)
    private String telefono;

    @Column(nullable = false, columnDefinition = "varchar(20) default 'DOLAR'")
    private String moneda;

}
