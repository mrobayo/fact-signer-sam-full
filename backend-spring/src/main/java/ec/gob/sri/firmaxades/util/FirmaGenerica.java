package ec.gob.sri.firmaxades.util;

import es.mityc.firmaJava.libreria.utilidades.UtilidadTratarNodo;
import es.mityc.firmaJava.libreria.xades.DataToSign;
import es.mityc.firmaJava.libreria.xades.FirmaXML;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.cert.X509Certificate;

public abstract class FirmaGenerica {
    private static final Logger LOG = LoggerFactory.getLogger(FirmaGenerica.class);
    private String directorioSalidaFirma;
    private Provider provider;
    private X509Certificate certificado;
    private PrivateKey privateKey;

    protected FirmaGenerica(String directorioSalidaFirma, Provider provider, X509Certificate certificado, PrivateKey privateKey) {
//        BasicConfigurator.configure();
//        Logger.getRootLogger().setLevel(Level.ERROR);
        this.directorioSalidaFirma = directorioSalidaFirma;
        this.provider = provider;
        this.certificado = certificado;
        this.privateKey = privateKey;
    }

    public FirmaGenerica() {
//        BasicConfigurator.configure();
//        Logger.getRootLogger().setLevel(Level.INFO);
    }

    protected void execute() throws ParserConfigurationException, SAXException, IOException {
        DataToSign datosAFirmar = this.createDataToSign();
        Document documentoFirmado = null;
        FirmaXML firma = null;
        firma = this.createFirmaXML();
        Object[] res = null;

        try {
            res = firma.signFile(this.certificado, datosAFirmar, this.privateKey, this.provider);
        } catch (Exception var12) {
            LOG.error("Error al firmar el documento ", var12);
        }

        documentoFirmado = (Document)res[0];

        try {
            (new StringBuilder()).append(this.directorioSalidaFirma).append(File.separatorChar).append(this.getSignatureFileName()).toString();
            this.saveDocumentToFile(documentoFirmado, this.getSignatureFileName());
        } catch (Exception var10) {
            LOG.error("Error al guardar el documento en el directorio de destino ", var10);
        } finally {
            firma = null;
            datosAFirmar = null;
            documentoFirmado = null;
        }

    }

    protected abstract DataToSign createDataToSign() throws ParserConfigurationException, SAXException, IOException;

    protected abstract String getSignatureFileName();

    protected FirmaXML createFirmaXML() {
        return new FirmaXML();
    }

    private void saveDocumentToFile(Document document, String pathfile) {
        try {
            FileOutputStream fos = new FileOutputStream(pathfile);
            UtilidadTratarNodo.saveDocumentToOutputStream(document, fos, true);
        } catch (FileNotFoundException var4) {
            LOG.error("FileNotFoundException: Error al salvar el documento", var4);
        }

    }

    protected Document getDocument(String filepath) throws ParserConfigurationException, SAXException, IOException {
        Document doc = null;
        File file = new File(filepath);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        doc = db.parse(file);
        return doc;
    }

    protected String getDocumentAsString(String resource) throws ParserConfigurationException, SAXException, IOException {
        Document doc = this.getDocument(resource);
        TransformerFactory tfactory = TransformerFactory.newInstance();
        StringWriter stringWriter = new StringWriter();

        try {
            Transformer serializer = tfactory.newTransformer();
            serializer.transform(new DOMSource(doc), new StreamResult(stringWriter));
        } catch (TransformerException var7) {
            LOG.error("Error al imprimir el documento", var7);
            return null;
        }

        return stringWriter.toString();
    }

    public String getDirectorioSalidaFirma() {
        return this.directorioSalidaFirma;
    }

    public void setDirectorioSalidaFirma(String directorioSalidaFirma) {
        this.directorioSalidaFirma = directorioSalidaFirma;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public void setCertificado(X509Certificate certificado) {
        this.certificado = certificado;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }
}
