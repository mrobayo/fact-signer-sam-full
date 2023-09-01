package com.marvic.factsigner.model.comprobantes.types;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@NoArgsConstructor

@Embeddable
public class InfoTributaria {

        protected String ambiente;
        protected String tipoEmision;
        protected String razonSocial;
        protected String nombreComercial;
        protected String ruc;
        protected String claveAcceso;
        protected String codDoc;

        protected String estab;
        protected String ptoEmi;
        protected String secuencial;

        protected String dirMatriz;
        protected String agenteRetencion;
        protected String contribuyenteRimpe;

}
