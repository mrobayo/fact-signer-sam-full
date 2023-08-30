package com.marvic.factsigner.model.sistema;
import ec.gob.sri.types.SriAmbiente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name="si_empresa",
        uniqueConstraints = @UniqueConstraint(name="si_empresa_name_uk", columnNames = {"name"}))
public class Empresa {

    @Id
    @Column(name = "ruc", nullable = false, length = 20)
    private String id;

    @Column(nullable = false, length = 100)
    private String name;

    public String getRuc() {
        return id;
    }

    @Column(nullable = false, length = 60)
    private String color;

    @Column(nullable = false, length = 100)
    private String comercial;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private SriAmbiente ambiente;

    @Column(nullable = false)
    private BigDecimal tarifaIva;

    @Column(length = 20)
    private String numeroContribuyente;   // Num de Contribuyente Especial

    @Column(nullable = false)
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

}
