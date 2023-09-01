package ec.gob.sri.firmaxades.test;

import es.mityc.firmaJava.libreria.xades.ExtraValidators;
import es.mityc.firmaJava.libreria.xades.ResultadoValidacion;
import es.mityc.firmaJava.libreria.xades.ValidarFirmaXML;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class ValidacionBasica {
    private static final Logger LOG = LoggerFactory.getLogger(ValidacionBasica.class);
    private static final String ARCHIVO_XADES_VALIDO = "/repositorio/factura-XAdES-BES.xml";

    public ValidacionBasica() {
    }

    public static void main(String[] args) {
//        BasicConfigurator.configure();
//        Logger.getRootLogger().setLevel(Level.ERROR);
        ValidacionBasica validador = new ValidacionBasica();
        if (validador.validarFichero(ValidacionBasica.class.getResourceAsStream("/repositorio/factura-XAdES-BES.xml"))) {
            LOG.info("archivo valido");
        }

    }

    public boolean validarArchivo(File archivo) {
        ValidacionBasica validador = new ValidacionBasica();
        boolean esValido = false;

        try {
            InputStream is = new FileInputStream(archivo);
            esValido = validador.validarFichero(is);
        } catch (FileNotFoundException var5) {
            LOG.error(var5.getMessage());
        }

        return esValido;
    }

    public boolean validarFichero(InputStream archivo) {
        boolean esValido = true;
        ArrayList<ResultadoValidacion> results = null;
        Document doc = this.parseaDoc(archivo);
        if (doc != null) {
            ValidarFirmaXML result;
            try {
                result = new ValidarFirmaXML();
                results = result.validar(doc, "./", (ExtraValidators)null);
            } catch (Exception var7) {
                LOG.error(var7.getMessage());
            }

            result = null;
            Iterator<ResultadoValidacion> it = results.iterator();

            while(it.hasNext()) {
                ResultadoValidacion resultValidacion = (ResultadoValidacion)it.next();
                esValido = resultValidacion.isValidate();
                if (esValido) {
                    LOG.info("La firma es valida = " + resultValidacion.getNivelValido() + "\nFirmado el: " + resultValidacion.getDatosFirma().getFechaFirma());
                } else {
                    LOG.info("La firma NO es valida\n" + resultValidacion.getLog());
                }
            }
        }

        return esValido;
    }

    private Document parseaDoc(InputStream fichero) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder db = null;

        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException var11) {
            LOG.error("Error interno al parsear la firma", var11);
            return null;
        }

        Document doc = null;

        try {
            doc = db.parse(fichero);
            Document var5 = doc;
            return var5;
        } catch (SAXException var12) {
            doc = null;
        } catch (IOException var13) {
            LOG.error("Error interno al validar firma", var13);
        } finally {
            dbf = null;
            db = null;
        }

        return null;
    }
}
