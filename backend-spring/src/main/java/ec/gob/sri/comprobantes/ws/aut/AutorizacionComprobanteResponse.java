package ec.gob.sri.comprobantes.ws.aut;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "autorizacionComprobanteResponse",
        propOrder = {"respuestaAutorizacionComprobante"}
)
public class AutorizacionComprobanteResponse {
    @XmlElement(
            name = "RespuestaAutorizacionComprobante"
    )
    protected RespuestaComprobante respuestaAutorizacionComprobante;

    public AutorizacionComprobanteResponse() {
    }

    public RespuestaComprobante getRespuestaAutorizacionComprobante() {
        return this.respuestaAutorizacionComprobante;
    }

    public void setRespuestaAutorizacionComprobante(RespuestaComprobante value) {
        this.respuestaAutorizacionComprobante = value;
    }
}
