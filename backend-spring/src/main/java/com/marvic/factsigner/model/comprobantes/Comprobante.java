package com.marvic.factsigner.model.comprobantes;

import com.marvic.factsigner.model.comprobantes.extra.PuntoVenta;
import com.marvic.factsigner.model.sistema.Empresa;
import com.marvic.factsigner.model.sistema.Usuario;
import ec.gob.sri.types.SriAmbiente;
import ec.gob.sri.types.SriEnumIdentidad;
import ec.gob.sri.types.SriTipoDoc;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor

//@Entity
//@Table(name="si_comprobante"
//      //  , uniqueConstraints = @UniqueConstraint(name="si_comprobante_numero_uk", columnNames = {"numero", "empresaId"})
//)
public class Comprobante {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID id;

    @Column(name="numero", length = 20)
    private String name;

    @Column
    private Integer secuencia;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EstadoTipo estadoDoc;

    // Aprobacion
    @Column(nullable = false)
    private Boolean aprobado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="aprobado_id", nullable = false)
    private Usuario aprobadoPor;

    // Envio lote
    private Integer publicado;

    @Column(name = "lote_id", length = 60)
    private String loteId;

    @Column(name = "lote_id", length = 60)
    private String periodoFiscal;

    @Column(name = "clave_acceso", length = 60)
    private String claveAcceso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="empresa_id", nullable = false)
    private Empresa empresa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="punto_venta_id", nullable = false)
    private PuntoVenta puntoVenta;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private SriTipoDoc tipoDoc;

    @Column(name="fecha_emision")
    private LocalDate fechaEmision;

    @Column(name="fecha_hora")
    private LocalDateTime fechaHora;

    @Column(name="moneda")
    private String moneda;

    @Column(name="tipo_emision")
    private Integer tipoEmision;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private SriAmbiente ambiente;

    @Column(length = 60)
    private String autorizacion;

    @Column(length = 60)
    private String mensajeSri;

    @Column(nullable = false)
    private Boolean emailEnviado;

    @Column(name="sujeto_tipo", length = 20)
    private SriEnumIdentidad sujetoTipo;

    @Column(name="sujeto_razonsocial", length = 300)
    private String sujetoRazonSocial;

    @Column(name="sujeto_identidad", length = 20)
    private String sujetoIdentidad;

    @Column(name="sujeto_direccion", length = 100)
    private String sujetoDireccion;

    @Column(name="sujeto_telefono", length = 100)
    private String sujetoTelefono;

    @Column(name="sujeto_email", length = 100)
    private String sujetoEmail;

    //private AuditObject audit;
    //private List<DatoAdicional> adicionales = new ArrayList<DatoAdicional>();

    @Column(name = "modifica_id")
    private String auditModifica;

    @Column(name="fecha_modifica")
    private LocalDateTime auditModificado;

    @Column(name="creado_id", nullable = false)
    private String auditCrea;

    @Column(name="fecha_creado", nullable = false)
    private LocalDateTime auditCreado;

}
