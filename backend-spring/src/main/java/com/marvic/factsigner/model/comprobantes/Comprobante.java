package com.marvic.factsigner.model.comprobantes;

import com.marvic.factsigner.model.comprobantes.extra.PuntoVenta;
import com.marvic.factsigner.model.comprobantes.types.EstadoTipo;
import com.marvic.factsigner.model.sistema.Empresa;
import com.marvic.factsigner.model.sistema.Usuario;
import ec.gob.sri.types.SriAmbiente;
import ec.gob.sri.types.SriEnumIdentidad;
import ec.gob.sri.types.SriTipoDoc;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
// @SuperBuilder

@MappedSuperclass
public class Comprobante {

    @Column(name="numero", length = 20)
    private String name;

    @Column
    private Integer secuencia;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(20) default 'BORRADOR'")
    private EstadoTipo estadoDoc;

    // Envio lote
    @Column(name = "lote_id", length = 60)
    private String loteId;

    @Column(name = "clave_acceso", length = 60)
    private String claveAcceso;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private SriTipoDoc tipoDoc;

    @Column(name="fecha_emision")
    private LocalDate fechaEmision;

    @Column(name="fecha_hora")
    private LocalDateTime fechaHora;

    @Column(name="moneda", length = 20)
    private String moneda;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private SriAmbiente ambiente;

    @Column(nullable = false)
    private boolean obligado;

    @Column(name = "numero_contribuyente", length = 20)
    private String numeroContribuyente;

    @Column(length = 60)
    private String autorizacion;

    @Column(name = "mensaje_sri", length = 60)
    private String mensajeSri;

    @Column(name = "email_enviado", nullable = false)
    private boolean emailEnviado;

    @Column(name="sujeto_tipo", length = 20)
    private SriEnumIdentidad sujetoTipo;

    @Column(name="sujeto_razonsocial", length = 100)
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

//    @Column(name = "modifica_id", length = 20)
//    private String auditModifica;
//
//    @Column(name="fecha_modifica")
//    private LocalDateTime auditModificado;
//
//    @Column(name="creado_id", length = 20, nullable = false)
//    private String auditCrea;
//
//    @Column(name="fecha_creado", nullable = false)
//    private LocalDateTime auditCreado;

    // Aprobacion
    @Column(nullable = false)
    private boolean aprobado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="aprobado_id")
    private Usuario aprobador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="empresa_id", nullable = false)
    private Empresa empresa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="punto_venta_id", nullable = false)
    private PuntoVenta puntoVenta;

}
