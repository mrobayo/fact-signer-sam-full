package ec.gob.sri.comprobantes.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "validarComprobanteResponse",
        propOrder = {"respuestaRecepcionComprobante"}
)
public class ValidarComprobanteResponse {
    @XmlElement(
            name = "RespuestaRecepcionComprobante"
    )
    protected RespuestaSolicitud respuestaRecepcionComprobante;

    public ValidarComprobanteResponse() {
    }

    public RespuestaSolicitud getRespuestaRecepcionComprobante() {
        return this.respuestaRecepcionComprobante;
    }

    public void setRespuestaRecepcionComprobante(RespuestaSolicitud value) {
        this.respuestaRecepcionComprobante = value;
    }
}
