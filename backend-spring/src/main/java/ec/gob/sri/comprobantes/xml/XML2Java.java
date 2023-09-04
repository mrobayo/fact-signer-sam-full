package ec.gob.sri.comprobantes.xml;

import ec.gob.sri.comprobantes.modelo.factura.Factura;
import ec.gob.sri.comprobantes.modelo.guia.GuiaRemision;
import ec.gob.sri.comprobantes.modelo.notacredito.NotaCredito;
import ec.gob.sri.comprobantes.modelo.notadebito.NotaDebito;
import ec.gob.sri.comprobantes.modelo.rentencion.ComprobanteRetencion;
import ec.gob.sri.comprobantes.ws.aut.Autorizacion;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;

public class XML2Java {
    public XML2Java() {
    }

    public static Factura unmarshalFactura(String pathArchivo) throws Exception {
        JAXBContext context = JAXBContext.newInstance("ec.gob.sri.comprobantes.modelo.factura");
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Factura item = (Factura)unmarshaller.unmarshal(new InputStreamReader(new FileInputStream(pathArchivo), "UTF-8"));
        return item;
    }

    public static NotaDebito unmarshalNotaDebito(String pathArchivo) throws Exception {
        JAXBContext context = JAXBContext.newInstance("ec.gob.sri.comprobantes.modelo.notadebito");
        Unmarshaller unmarshaller = context.createUnmarshaller();
        NotaDebito item = (NotaDebito)unmarshaller.unmarshal(new InputStreamReader(new FileInputStream(pathArchivo), "UTF-8"));
        return item;
    }

    public static NotaCredito unmarshalNotaCredito(String pathArchivo) throws Exception {
        JAXBContext context = JAXBContext.newInstance("ec.gob.sri.comprobantes.modelo.notacredito");
        Unmarshaller unmarshaller = context.createUnmarshaller();
        NotaCredito item = (NotaCredito)unmarshaller.unmarshal(new InputStreamReader(new FileInputStream(pathArchivo), "UTF-8"));
        return item;
    }

    public static GuiaRemision unmarshalGuiaRemision(String pathArchivo) throws Exception {
        JAXBContext context = JAXBContext.newInstance("ec.gob.sri.comprobantes.modelo.guia");
        Unmarshaller unmarshaller = context.createUnmarshaller();
        GuiaRemision item = (GuiaRemision)unmarshaller.unmarshal(new InputStreamReader(new FileInputStream(pathArchivo), "UTF-8"));
        return item;
    }

    public static ComprobanteRetencion unmarshalComprobanteRetencion(String pathArchivo) throws Exception {
        JAXBContext context = JAXBContext.newInstance("ec.gob.sri.comprobantes.modelo.rentencion");
        Unmarshaller unmarshaller = context.createUnmarshaller();
        ComprobanteRetencion item = (ComprobanteRetencion)unmarshaller.unmarshal(new InputStreamReader(new FileInputStream(pathArchivo), "UTF-8"));
        return item;
    }

    public static Autorizacion unmarshalAutorizacion(String pathArchivo) throws Exception {
        JAXBContext context = JAXBContext.newInstance("ec.gob.sri.comprobantes.ws.aut.Autorizacion");
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Autorizacion item = (Autorizacion)unmarshaller.unmarshal(new FileReader(pathArchivo));
        return item;
    }
}
