package ec.gob.sri.comprobantes.ws.aut;

import javax.xml.namespace.QName;
import javax.xml.ws.*;
import java.net.URL;

@WebServiceClient(
        name = "AutorizacionComprobantesOfflineService",
        targetNamespace = "http://ec.gob.sri.ws.autorizacion",
        wsdlLocation = "/META-INF/wsdl/AutorizacionComprobantes.wsdl"
)
public class AutorizacionComprobantesOfflineService extends Service {
    private static final URL AUTORIZACIONCOMPROBANTESSERVICE_WSDL_LOCATION = AutorizacionComprobantesOfflineService.class.getResource("/META-INF/wsdl/AutorizacionComprobantes.wsdl");
    private static final WebServiceException AUTORIZACIONCOMPROBANTESSERVICE_EXCEPTION;
    private static final QName AUTORIZACIONCOMPROBANTESSERVICE_QNAME = new QName("http://ec.gob.sri.ws.autorizacion", "AutorizacionComprobantesOfflineService");

    public AutorizacionComprobantesOfflineService() {
        super(__getWsdlLocation(), AUTORIZACIONCOMPROBANTESSERVICE_QNAME);
    }

    public AutorizacionComprobantesOfflineService(URL wsdlLocation) {
        super(wsdlLocation, AUTORIZACIONCOMPROBANTESSERVICE_QNAME);
    }

    public AutorizacionComprobantesOfflineService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    @WebEndpoint(
            name = "AutorizacionComprobantesOfflinePort"
    )
    public AutorizacionComprobantesOffline getAutorizacionComprobantesOfflinePort() {
        return (AutorizacionComprobantesOffline)super.getPort(new QName("http://ec.gob.sri.ws.autorizacion", "AutorizacionComprobantesOfflinePort"), AutorizacionComprobantesOffline.class);
    }

    @WebEndpoint(
            name = "AutorizacionComprobantesOfflinePort"
    )
    public AutorizacionComprobantesOffline getAutorizacionComprobantesOfflinePort(WebServiceFeature... features) {
        return (AutorizacionComprobantesOffline)super.getPort(new QName("http://ec.gob.sri.ws.autorizacion", "AutorizacionComprobantesOfflinePort"), AutorizacionComprobantesOffline.class, features);
    }

    private static URL __getWsdlLocation() {
        if (AUTORIZACIONCOMPROBANTESSERVICE_EXCEPTION != null) {
            throw AUTORIZACIONCOMPROBANTESSERVICE_EXCEPTION;
        } else {
            return AUTORIZACIONCOMPROBANTESSERVICE_WSDL_LOCATION;
        }
    }

    static {
        WebServiceException e = null;
        if (AUTORIZACIONCOMPROBANTESSERVICE_WSDL_LOCATION == null) {
            e = new WebServiceException("Cannot find '/META-INF/wsdl/AutorizacionComprobantes.wsdl' wsdl. Place the resource correctly in the classpath.");
        }

        AUTORIZACIONCOMPROBANTESSERVICE_EXCEPTION = e;
    }
}
