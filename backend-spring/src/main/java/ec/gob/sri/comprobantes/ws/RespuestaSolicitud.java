package ec.gob.sri.comprobantes.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "respuestaSolicitud",
        propOrder = {"estado", "comprobantes"}
)
public class RespuestaSolicitud {
    protected String estado;
    protected Comprobantes comprobantes;

    public RespuestaSolicitud() {
    }

    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String value) {
        this.estado = value;
    }

    public Comprobantes getComprobantes() {
        return this.comprobantes;
    }

    public void setComprobantes(Comprobantes value) {
        this.comprobantes = value;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(
            name = "",
            propOrder = {"comprobante"}
    )
    public static class Comprobantes {
        protected List<Comprobante> comprobante;

        public Comprobantes() {
        }

        public List<Comprobante> getComprobante() {
            if (this.comprobante == null) {
                this.comprobante = new ArrayList();
            }

            return this.comprobante;
        }
    }
}
