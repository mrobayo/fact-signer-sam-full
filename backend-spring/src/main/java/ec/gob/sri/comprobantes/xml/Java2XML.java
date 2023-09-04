package ec.gob.sri.comprobantes.xml;

import ec.gob.sri.comprobantes.exception.ConvertidorXMLException;
import ec.gob.sri.comprobantes.modelo.factura.Factura;
import ec.gob.sri.comprobantes.modelo.guia.GuiaRemision;
import ec.gob.sri.comprobantes.modelo.notacredito.NotaCredito;
import ec.gob.sri.comprobantes.modelo.notadebito.NotaDebito;
import ec.gob.sri.comprobantes.modelo.rentencion.ComprobanteRetencion;
import ec.gob.sri.comprobantes.ws.RespuestaSolicitud;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Java2XML {
    public Java2XML() {
    }

    public static byte[] convertirAXml(Object comprobante) throws ConvertidorXMLException {
        try {
            StringWriter xmlComprobante = new StringWriter();
            JAXBContext context = JAXBContext.newInstance(new Class[]{comprobante.getClass()});
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty("jaxb.formatted.output", true);
            marshaller.marshal(comprobante, xmlComprobante);
            xmlComprobante.close();
            return xmlComprobante.toString().getBytes("UTF-8");
        } catch (IOException var4) {
            Logger.getLogger(Java2XML.class.getName()).log(Level.SEVERE, (String)null, var4);
            throw new ConvertidorXMLException("Se produjo un error al convetir el archivo al formato XML");
        } catch (JAXBException var5) {
            Logger.getLogger(Java2XML.class.getName()).log(Level.SEVERE, (String)null, var5);
            throw new ConvertidorXMLException("Se produjo un error al convetir el archivo al formato XML");
        }
    }

    public static String marshalFactura(Factura comprobante, String pathArchivoSalida) {
        String respuesta = null;

        try {
            JAXBContext context = JAXBContext.newInstance(Factura.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty("jaxb.encoding", "UTF-8");
            marshaller.setProperty("jaxb.formatted.output", true);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            OutputStreamWriter out = new OutputStreamWriter(byteArrayOutputStream, "UTF-8");
            marshaller.marshal(comprobante, out);
            OutputStream outputStream = new FileOutputStream(pathArchivoSalida);
            byteArrayOutputStream.writeTo(outputStream);
            byteArrayOutputStream.close();
            outputStream.close();
            return (String)respuesta;
        } catch (Exception var8) {
            Logger.getLogger(Java2XML.class.getName()).log(Level.SEVERE, (String)null, var8);
            return var8.getMessage();
        }
    }

    public static String marshalNotaDeDebito(NotaDebito comprobante, String pathArchivoSalida) {
        String respuesta = null;

        try {
            JAXBContext context = JAXBContext.newInstance(new Class[]{NotaDebito.class});
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty("jaxb.encoding", "UTF-8");
            marshaller.setProperty("jaxb.formatted.output", true);
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(pathArchivoSalida), "UTF-8");
            marshaller.marshal(comprobante, out);
            return (String)respuesta;
        } catch (Exception var6) {
            Logger.getLogger(Java2XML.class.getName()).log(Level.SEVERE, (String)null, var6);
            return var6.getMessage();
        }
    }

    public static String marshalNotaDeCredito(NotaCredito comprobante, String pathArchivoSalida) {
        String respuesta = null;

        try {
            JAXBContext context = JAXBContext.newInstance(new Class[]{NotaCredito.class});
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty("jaxb.encoding", "UTF-8");
            marshaller.setProperty("jaxb.formatted.output", true);
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(pathArchivoSalida), "UTF-8");
            marshaller.marshal(comprobante, out);
            return (String)respuesta;
        } catch (Exception var6) {
            Logger.getLogger(Java2XML.class.getName()).log(Level.SEVERE, (String)null, var6);
            return var6.getMessage();
        }
    }

    public static String marshalComprobanteRetencion(ComprobanteRetencion comprobante, String pathArchivoSalida) {
        String respuesta = null;

        try {
            JAXBContext context = JAXBContext.newInstance(new Class[]{ComprobanteRetencion.class});
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty("jaxb.encoding", "UTF-8");
            marshaller.setProperty("jaxb.formatted.output", true);
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(pathArchivoSalida), "UTF-8");
            marshaller.marshal(comprobante, out);
            return (String)respuesta;
        } catch (Exception var6) {
            Logger.getLogger(Java2XML.class.getName()).log(Level.SEVERE, (String)null, var6);
            return var6.getMessage();
        }
    }

    public static String marshalGuiaRemision(GuiaRemision comprobante, String pathArchivoSalida) {
        String respuesta = null;

        try {
            JAXBContext context = JAXBContext.newInstance(new Class[]{GuiaRemision.class});
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty("jaxb.encoding", "UTF-8");
            marshaller.setProperty("jaxb.formatted.output", true);
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(pathArchivoSalida), "UTF-8");
            marshaller.marshal(comprobante, out);
            return (String)respuesta;
        } catch (Exception var6) {
            Logger.getLogger(Java2XML.class.getName()).log(Level.SEVERE, (String)null, var6);
            return var6.getMessage();
        }
    }

    public static String marshalRespuestaSolicitud(RespuestaSolicitud respuesta, String pathArchivoSalida) {
        try {
            JAXBContext context = JAXBContext.newInstance(new Class[]{RespuestaSolicitud.class});
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty("jaxb.encoding", "UTF-8");
            marshaller.setProperty("jaxb.formatted.output", true);
            FileOutputStream fileOutputStream = new FileOutputStream(pathArchivoSalida);
            OutputStreamWriter out = new OutputStreamWriter(fileOutputStream, "UTF-8");
            marshaller.marshal(respuesta, out);
            fileOutputStream.close();
            return null;
        } catch (Exception var6) {
            Logger.getLogger(Java2XML.class.getName()).log(Level.SEVERE, (String)null, var6);
            return var6.getMessage();
        }
    }
}
