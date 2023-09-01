package ec.gob.sri.comprobantes.ws.aut;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "respuestaComprobante",
        propOrder = {"claveAccesoConsultada", "numeroComprobantes", "autorizaciones"}
)
public class RespuestaComprobante {
    protected String claveAccesoConsultada;
    protected String numeroComprobantes;
    protected Autorizaciones autorizaciones;

    public RespuestaComprobante() {
    }

    public String getClaveAccesoConsultada() {
        return this.claveAccesoConsultada;
    }

    public void setClaveAccesoConsultada(String value) {
        this.claveAccesoConsultada = value;
    }

    public String getNumeroComprobantes() {
        return this.numeroComprobantes;
    }

    public void setNumeroComprobantes(String value) {
        this.numeroComprobantes = value;
    }

    public Autorizaciones getAutorizaciones() {
        return this.autorizaciones;
    }

    public void setAutorizaciones(Autorizaciones value) {
        this.autorizaciones = value;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(
            name = "",
            propOrder = {"autorizacion"}
    )
    public static class Autorizaciones {
        protected List<Autorizacion> autorizacion;

        public Autorizaciones() {
        }

        public List<Autorizacion> getAutorizacion() {
            if (this.autorizacion == null) {
                this.autorizacion = new ArrayList();
            }

            return this.autorizacion;
        }
    }
}
