package com.marvic.factsigner.service;

import ec.gob.sri.firmaxades.test.PassStoreKS;
import es.mityc.firmaJava.libreria.utilidades.UtilidadFicheros;
import es.mityc.firmaJava.libreria.xades.DataToSign;
import es.mityc.firmaJava.libreria.xades.FirmaXML;
import es.mityc.firmaJava.libreria.xades.XAdESSchemas;
import es.mityc.javasign.EnumFormatoFirma;
import es.mityc.javasign.pkstore.CertStoreException;
import es.mityc.javasign.pkstore.IPKStoreManager;
import es.mityc.javasign.pkstore.keystore.KSStore;
import es.mityc.javasign.xml.refs.InternObjectToSign;
import es.mityc.javasign.xml.refs.ObjectToSign;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.nio.ByteBuffer;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

@Component
public class SignerService {

    private IPKStoreManager getPKStoreManager(InputStream certificate, String clave) {
        IPKStoreManager storeManager = null;

        try {
            KeyStore ks = KeyStore.getInstance("PKCS12");
            ks.load(certificate, clave.toCharArray());
            storeManager = new KSStore(ks, new PassStoreKS(clave));
        } catch (KeyStoreException ex) {
            System.err.println("No se puede generar KeyStore PKCS12");
            ex.printStackTrace();
            //exit(-1)
        } catch (NoSuchAlgorithmException ex) {
            System.err.println("No se puede generar KeyStore PKCS12");
            ex.printStackTrace();
            //exit(-1)
        } catch (CertificateException ex) {
            System.err.println("No se puede generar KeyStore PKCS12");
            ex.printStackTrace();
            //exit(-1)
        } catch (IOException ex) {
            System.err.println("No se puede generar KeyStore PKCS12");
            ex.printStackTrace();
            //exit(-1)
        }
        return storeManager;
    }

    private X509Certificate getFirstCertificate(
            final IPKStoreManager storeManager) {
        List<X509Certificate> certs = null;

        try {
            certs = storeManager.getSignCertificates();

        } catch (CertStoreException ex) {
            System.err.println("Fallo obteniendo listado de certificados");
            //exit(-1)
        }

        if ((certs == null) || (certs.size() == 0)) {
            System.err.println("Lista de certificados vacía");
            //exit(-1)
        }

        X509Certificate certificate = certs.get(0);
        return certificate;
    }

    private Document getDocument(String xml) throws ParserConfigurationException, SAXException, IOException {
        Document doc = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        InputSource inputSource = new InputSource(new StringReader(xml));
        doc = db.parse(inputSource);
        return doc;
    }


    private DataToSign createDataToSign(String xmlContent) throws ParserConfigurationException, IOException, SAXException {
        DataToSign dataToSign = new DataToSign();
        dataToSign.setXadesFormat(EnumFormatoFirma.XAdES_BES);
        dataToSign.setEsquema(XAdESSchemas.XAdES_132);
        dataToSign.setXMLEncoding("UTF-8");

        // Se añade un rol de firma
        dataToSign.setEnveloped(true);
        dataToSign.setParentSignNode("comprobante");
        dataToSign
                .addObject(new ObjectToSign(new InternObjectToSign(
                        "comprobante"), "contenido comprobante", null,
                        "text/xml", null));
        Document docToSign = getDocument(xmlContent);
        dataToSign.setDocument(docToSign);
        return dataToSign;
    }

    public Document signDocument(String xmlContent) throws ParserConfigurationException, IOException, SAXException {
        InputStream cert = new FileInputStream("/Users/marvic/Home/ssl/OPENJSOFTSOFTWARECONSULTING.pfx");
        String clave = "YVES9837";
        return signDocument(xmlContent, cert, clave);
    }

    public Document signDocument(String xmlContent, InputStream certificateBytes, String password) throws ParserConfigurationException, IOException, SAXException {
        // Obtencion del gestor de claves
        IPKStoreManager storeManager = getPKStoreManager(certificateBytes, password);
        if (storeManager == null) {
            return null;
        }

        // Obtencion del certificado para firmar. Utilizaremos el primer
        // certificado del almacen.
        X509Certificate certificate = getFirstCertificate(storeManager);
        if (certificate == null) {
            return null;
        }

        // Obtención de la clave privada asociada al certificado
        PrivateKey privateKey;
        try {
            privateKey = storeManager.getPrivateKey(certificate);
        } catch (CertStoreException e) {
            return null;
        }

        // Obtención del provider encargado de las labores criptográficas
        Provider provider = storeManager.getProvider(certificate);
        System.out.println(" #" + provider.getName() + "# " + provider.getInfo());
        Provider[] providers = Security.getProviders();
        for(int i = 0; i< providers.length; i++) {
            System.out.println(i + ". #" + providers[i].getName() + "# " + providers[i].getInfo());
        }
        provider = Security.getProvider("SunRsaSign");

        /*
         * Creación del objeto que contiene tanto los datos a firmar como la
         * configuración del tipo de firma
         */
        DataToSign dataToSign = createDataToSign(xmlContent);

        /*
         * Creación del objeto encargado de realizar la firma
         */
        FirmaXML firma = new FirmaXML();

        // Firmamos el documento
        Document docSigned = null;
        try {
            Object[] res = firma.signFile(certificate, dataToSign, privateKey, provider);
            docSigned = (Document) res[0];
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        // Guardamos la firma a un fichero en el home del usuario
        //ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        //UtilidadFicheros.writeXML(docSigned, outputStream);
        //byte[] sss = outputStream.toByteArray();
        return docSigned;
    }




}
