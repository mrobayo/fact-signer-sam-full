package com.marvic.factsigner.model.comprobantes;
import com.marvic.factsigner.model.comprobantes.extra.PuntoVenta;
import com.marvic.factsigner.model.comprobantes.types.EstadoTipo;
import com.marvic.factsigner.model.sistema.Empresa;
import ec.gob.sri.types.SriAmbiente;
import ec.gob.sri.types.SriTipoDoc;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
// @SuperBuilder

@MappedSuperclass
public class Comprobante {

    @Column(name="fecha_emision")
    private LocalDate fechaEmision;

    @Column(name="document_url", length = 300)
    private String documentUrl;

    @Column(name="numero", length = 20)
    private String name;

    @Column(name="empresa_id", insertable = false, updatable = false)
    protected String ruc;

    public String getEstab() {
        return name != null ? name.substring(0, 3) : null;
    }

    public String getPtoEmi() {
        return name != null ? name.substring(4, 7) : null;
    }

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private SriTipoDoc tipoDoc;

    public String getCodDoc() {
        return tipoDoc != null ? tipoDoc.value() : null;
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(20) default 'BORRADOR'")
    private EstadoTipo estadoDoc;

    @Column
    protected Integer secuencia;

    public String getSecuencial() {
        return String.format("%09d", secuencia);
    }

    @Column(name = "clave_acceso", length = 60)
    protected String claveAcceso;

    @Enumerated(EnumType.STRING)
    @Column(name="ambiente", length = 20, nullable = false)
    private SriAmbiente ambienteSri;

    public String getAmbiente() {
        return ambienteSri != null ? ambienteSri.value() : null;
    }

    // Envio lote
    @Column(name = "lote_id", length = 60)
    private String loteId;

    @Column(name="fecha_hora")
    private LocalDateTime fechaHora;

    @Column(length = 60)
    private String autorizacion;

    @Column(name = "mensaje_sri", length = 60)
    private String mensajeSri;

    @Column(name = "email_enviado", nullable = false)
    private boolean emailEnviado;

    @Column(name="sujeto_email", length = 100)
    private String sujetoEmail;

//    @Embedded
//    private InfoTributaria infoTributaria;

    //private AuditObject audit;
    //private List<DatoAdicional> adicionales = new ArrayList<DatoAdicional>();

//    @Column
//    private Integer secuencia;

//    @Column(name = "clave_acceso", length = 60)
//    private String claveAcceso;

//    @Column(name="fecha_emision")
//    private LocalDate fechaEmision;

//    @Column(name="moneda", length = 20)
//    private String moneda;

//    @Enumerated(EnumType.STRING)
//    @Column(length = 20, nullable = false)
//    private SriAmbiente ambiente;

//    @Column(nullable = false)
//    private boolean obligado;

//    @Column(name = "numero_contribuyente", length = 20)
//    private String numeroContribuyente;

//    @Column(name="sujeto_tipo", length = 20)
//    private SriEnumIdentidad sujetoTipo;

//    @Column(name="sujeto_razonsocial", length = 100)
//    private String sujetoRazonSocial;
//
//    @Column(name="sujeto_identidad", length = 20)
//    private String sujetoIdentidad;

//    @Column(name="sujeto_direccion", length = 100)
//    private String sujetoDireccion;

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

    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name="aprobado_id")
    @Column(name="aprobado_id", length = 20)
    private String aprobador;

    @Column(name="fecha_aprobado")
    private LocalDateTime fechaAprobado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="empresa_id", nullable = false)
    private Empresa empresa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="punto_venta_id", nullable = false)
    private PuntoVenta puntoVenta;

    @Column(length = 300)
    private String observacion;

}
