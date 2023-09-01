package ec.gob.sri.comprobantes.modelo.guia;

import ec.gob.sri.comprobantes.modelo.InfoTributaria;
import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {
    public ObjectFactory() {
    }

    public GuiaRemision.Destinatarios createGuiaRemisionDestinatarios() {
        return new GuiaRemision.Destinatarios();
    }

    public Detalle.DetallesAdicionales createDetalleDetallesAdicionales() {
        return new Detalle.DetallesAdicionales();
    }

    public Destinatario createDestinatario() {
        return new Destinatario();
    }

    public Detalle.DetallesAdicionales.DetAdicional createDetalleDetallesAdicionalesDetAdicional() {
        return new Detalle.DetallesAdicionales.DetAdicional();
    }

    public Destinatario.Detalles createDestinatarioDetalles() {
        return new Destinatario.Detalles();
    }

    public GuiaRemision createGuiaRemision() {
        return new GuiaRemision();
    }

    public GuiaRemision.InfoAdicional.CampoAdicional createGuiaRemisionInfoAdicionalCampoAdicional() {
        return new GuiaRemision.InfoAdicional.CampoAdicional();
    }

    public GuiaRemision.InfoAdicional createGuiaRemisionInfoAdicional() {
        return new GuiaRemision.InfoAdicional();
    }

    public InfoTributaria createInfoTributaria() {
        return new InfoTributaria();
    }

    public GuiaRemision.InfoGuiaRemision createGuiaRemisionInfoGuiaRemision() {
        return new GuiaRemision.InfoGuiaRemision();
    }

    public Detalle createDetalle() {
        return new Detalle();
    }
}
