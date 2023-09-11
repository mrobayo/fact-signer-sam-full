package ec.gob.sri.comprobantes.util;

import ec.gob.sri.comprobantes.modelo.Respuesta;
import ec.gob.sri.comprobantes.ws.*;

import javax.xml.namespace.QName;
import javax.xml.ws.WebServiceException;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EnvioComprobantesWs {
    private static RecepcionComprobantesOfflineService service;
    private static final String VERSION = "1.0.0";
    public static final String ESTADO_RECIBIDA = "RECIBIDA";
    public static final String ESTADO_DEVUELTA = "DEVUELTA";

    public EnvioComprobantesWs(String wsdlLocation) throws MalformedURLException, WebServiceException {
        URL url = new URL(wsdlLocation);
        QName qname = new QName("http://ec.gob.sri.ws.recepcion", "RecepcionComprobantesOfflineService");
        service = new RecepcionComprobantesOfflineService(url, qname);
    }

    public static final Object webService(String wsdlLocation) {
        try {
            QName qname = new QName("http://ec.gob.sri.ws.recepcion", "RecepcionComprobantesOfflineService");
            URL url = new URL(wsdlLocation);
            service = new RecepcionComprobantesOfflineService(url, qname);
            return null;
        } catch (MalformedURLException var3) {
            Logger.getLogger(EnvioComprobantesWs.class.getName()).log(Level.SEVERE, (String)null, var3);
            return var3;
        } catch (WebServiceException var4) {
            Logger.getLogger(EnvioComprobantesWs.class.getName()).log(Level.SEVERE, (String)null, var4);
            return var4;
        }
    }

    public RespuestaSolicitud enviarComprobante(String ruc, byte[] xmlContents, String tipoComprobante, String versionXsd) {
        RespuestaSolicitud response = null;

        try {
            RecepcionComprobantesOffline port = service.getRecepcionComprobantesOfflinePort();
            response = port.validarComprobante(xmlContents); //ArchivoUtils.archivoToByte(xmlFile));
            return response;
        } catch (Exception var7) {
            Logger.getLogger(EnvioComprobantesWs.class.getName()).log(Level.SEVERE, (String)null, var7);
            response = new RespuestaSolicitud();
            response.setEstado(var7.getMessage());
            return response;
        }
    }

    public RespuestaSolicitud enviarComprobanteLotes(String ruc, byte[] xml, String tipoComprobante, String versionXsd) {
        RespuestaSolicitud response = null;

        try {
            RecepcionComprobantesOffline port = service.getRecepcionComprobantesOfflinePort();
            response = port.validarComprobante(xml);
            return response;
        } catch (Exception var7) {
            Logger.getLogger(EnvioComprobantesWs.class.getName()).log(Level.SEVERE, (String)null, var7);
            response = new RespuestaSolicitud();
            response.setEstado(var7.getMessage());
            return response;
        }
    }

    public static RespuestaSolicitud obtenerRespuestaEnvio(byte[] xmlContents, String ruc, String tipoComprobante, String claveDeAcceso, String urlWsdl) {
        RespuestaSolicitud respuesta = new RespuestaSolicitud();
        EnvioComprobantesWs cliente = null;
        try {
            cliente = new EnvioComprobantesWs(urlWsdl);
        } catch (Exception var8) {
            Logger.getLogger(EnvioComprobantesWs.class.getName()).log(Level.SEVERE, (String)null, var8);
            respuesta.setEstado(var8.getMessage());
            return respuesta;
        }
        respuesta = cliente.enviarComprobante(ruc, xmlContents, tipoComprobante, "1.0.0");
        return respuesta;
    }

    public static Respuesta guardarRespuesta(String claveDeAcceso, String archivo, String estado, Date fecha) {
        try {
            java.sql.Date sqlDate = new java.sql.Date(fecha.getTime());
            Respuesta item = new Respuesta((Integer)null, claveDeAcceso, archivo, estado, sqlDate);
            return item;
        } catch (Exception var7) {
            Logger.getLogger(EnvioComprobantesWs.class.getName()).log(Level.SEVERE, (String)null, var7);
        }
        return null;
    }

    public static String obtenerMensajeRespuesta(RespuestaSolicitud respuesta) {
        StringBuilder mensajeDesplegable = new StringBuilder();
        if (respuesta.getEstado().equals("DEVUELTA")) {
            RespuestaSolicitud.Comprobantes comprobantes = respuesta.getComprobantes();
            Iterator i$ = comprobantes.getComprobante().iterator();

            while(i$.hasNext()) {
                Comprobante comp = (Comprobante)i$.next();
                mensajeDesplegable.append(comp.getClaveAcceso());
                mensajeDesplegable.append("\n");
                Iterator m$ = comp.getMensajes().getMensaje().iterator();

                while(m$.hasNext()) {
                    Mensaje m = (Mensaje)i$.next();
                    mensajeDesplegable.append(m.getMensaje()).append(" :\n");
                    mensajeDesplegable.append(m.getInformacionAdicional() != null ? m.getInformacionAdicional() : "");
                    mensajeDesplegable.append("\n");
                }

                mensajeDesplegable.append("\n");
            }
        }

        return mensajeDesplegable.toString();
    }
}
