package com.marvic.factsigner.model.comprobantes.types;

import ec.gob.sri.types.SriAmbiente;
import ec.gob.sri.types.SriTipoDoc;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@NoArgsConstructor

@Embeddable
public class InfoTributaria {

    @Column(name="numero", length = 20)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name="ambiente", length = 20, nullable = false)
    private SriAmbiente ambienteSri;

    public String getAmbiente() {
        return ambienteSri != null ? ambienteSri.value() : null;
    }

    public String getTipoEmision() {
        return "1";
    }

    @Column(name="empresa_id", insertable = false, updatable = false)
    protected String ruc;

    @Column(name = "clave_acceso", length = 60)
    protected String claveAcceso;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private SriTipoDoc tipoDoc;

    public String getEstab() {
        return name != null ? name.substring(0, 3) : null;
    }

    public String getPtoEmi() {
        return name != null ? name.substring(4, 7) : null;
    }

    public String getCodDoc() {
        return tipoDoc != null ? tipoDoc.value() : null;
    }

    @Column
    protected Integer secuencia;

    public String getSecuencial() {
        return String.format("%09d", secuencia);
    }

    //protected String razonSocial;
    //protected String nombreComercial;
    //protected String dirMatriz;

    // protected String agenteRetencion;
    // protected String contribuyenteRimpe;

}
