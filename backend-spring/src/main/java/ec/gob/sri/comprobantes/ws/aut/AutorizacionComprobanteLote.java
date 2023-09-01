package ec.gob.sri.comprobantes.ws.aut;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "autorizacionComprobanteLote",
        propOrder = {"claveAccesoLote"}
)
public class AutorizacionComprobanteLote {
    protected String claveAccesoLote;

    public AutorizacionComprobanteLote() {
    }

    public String getClaveAccesoLote() {
        return this.claveAccesoLote;
    }

    public void setClaveAccesoLote(String value) {
        this.claveAccesoLote = value;
    }
}
