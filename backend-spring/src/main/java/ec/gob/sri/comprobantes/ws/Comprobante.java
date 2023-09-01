package ec.gob.sri.comprobantes.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "comprobante",
        propOrder = {"claveAcceso", "mensajes"}
)
public class Comprobante {
    protected String claveAcceso;
    protected Mensajes mensajes;

    public Comprobante() {
    }

    public String getClaveAcceso() {
        return this.claveAcceso;
    }

    public void setClaveAcceso(String value) {
        this.claveAcceso = value;
    }

    public Mensajes getMensajes() {
        return this.mensajes;
    }

    public void setMensajes(Mensajes value) {
        this.mensajes = value;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(
            name = "",
            propOrder = {"mensaje"}
    )
    public static class Mensajes {
        protected List<Mensaje> mensaje;

        public Mensajes() {
        }

        public List<Mensaje> getMensaje() {
            if (this.mensaje == null) {
                this.mensaje = new ArrayList();
            }

            return this.mensaje;
        }
    }
}
