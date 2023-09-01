package ec.gob.sri.comprobantes.ws.aut;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

@WebService(
        name = "AutorizacionComprobantesOffline",
        targetNamespace = "http://ec.gob.sri.ws.autorizacion"
)
@XmlSeeAlso({ObjectFactory.class})
public interface AutorizacionComprobantesOffline {
    @WebMethod
    @WebResult(
            name = "RespuestaAutorizacionComprobante",
            targetNamespace = ""
    )
    @RequestWrapper(
            localName = "autorizacionComprobante",
            targetNamespace = "http://ec.gob.sri.ws.autorizacion",
            className = "ec.gob.sri.comprobantes.ws.aut.AutorizacionComprobante"
    )
    @ResponseWrapper(
            localName = "autorizacionComprobanteResponse",
            targetNamespace = "http://ec.gob.sri.ws.autorizacion",
            className = "ec.gob.sri.comprobantes.ws.aut.AutorizacionComprobanteResponse"
    )
    RespuestaComprobante autorizacionComprobante(@WebParam(name = "claveAccesoComprobante",targetNamespace = "") String var1);

    @WebMethod
    @WebResult(
            name = "RespuestaAutorizacionLote",
            targetNamespace = ""
    )
    @RequestWrapper(
            localName = "autorizacionComprobanteLote",
            targetNamespace = "http://ec.gob.sri.ws.autorizacion",
            className = "ec.gob.sri.comprobantes.ws.aut.AutorizacionComprobanteLote"
    )
    @ResponseWrapper(
            localName = "autorizacionComprobanteLoteResponse",
            targetNamespace = "http://ec.gob.sri.ws.autorizacion",
            className = "ec.gob.sri.comprobantes.ws.aut.AutorizacionComprobanteLoteResponse"
    )
    RespuestaLote autorizacionComprobanteLote(@WebParam(name = "claveAccesoLote",targetNamespace = "") String var1);
}
