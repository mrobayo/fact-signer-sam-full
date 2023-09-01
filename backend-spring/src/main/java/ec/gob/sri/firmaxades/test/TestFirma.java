package ec.gob.sri.firmaxades.test;

import es.mityc.firmaJava.libreria.utilidades.UtilidadTratarNodo;
import es.mityc.firmaJava.libreria.xades.DataToSign;
import es.mityc.firmaJava.libreria.xades.FirmaXML;
import es.mityc.firmaJava.libreria.xades.XAdESSchemas;
import es.mityc.javasign.EnumFormatoFirma;
import es.mityc.javasign.pkstore.CertStoreException;
import es.mityc.javasign.pkstore.IPKStoreManager;
import es.mityc.javasign.pkstore.keystore.KSStore;
import es.mityc.javasign.xml.refs.InternObjectToSign;
import es.mityc.javasign.xml.refs.ObjectToSign;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

public class TestFirma {

    String clave = "YVES9837";
    String certPath = "C:\\home\\ssl\\OPENJSOFTSOFTWARECONSULTING.pfx";

    File outputDir = new File("C:\\Dev\\OpenJSoft\\signer-ws\\contents\\documentos\\firmados");

    String signedFileName = "factura.signed.xml";

    String fileToSign = "C:\\Dev\\OpenJSoft\\signer-ws\\contents\\documentos\\generados\\0307202301091665655600110010010000000011234567810.xml";

    private IPKStoreManager getPKStoreManager() {
        IPKStoreManager storeManager = null;

        try {
            //this.getClass().getResourceAsStream(PKCS12_RESOURCE)
            FileInputStream in = new FileInputStream(new File(certPath));
            KeyStore ks = KeyStore.getInstance("PKCS12");
            ks.load(in, clave.toCharArray());
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

    protected File getFilePath() {
        if (outputDir == null) {
            throw new RuntimeException("outputDir not set");
        }
        File filePath = new File(outputDir, signedFileName);
        return filePath;
    }

    private void saveDocumentToFile(Document document, File pathfile) {

        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(pathfile);
            UtilidadTratarNodo.saveDocumentToOutputStream(document, fos, true);

        } catch (FileNotFoundException e) {
            System.err.println("Error al salvar el documento");
            e.printStackTrace();

        } finally {

            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                fos = null;
            }
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

    protected DataToSign createDataToSign() throws ParserConfigurationException, IOException, SAXException {
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
        Document docToSign = getDocument(fileToSign);
        dataToSign.setDocument(docToSign);
        return dataToSign;
    }

    public String execute() throws ParserConfigurationException, IOException, SAXException {

        // Obtencion del gestor de claves
        IPKStoreManager storeManager = getPKStoreManager();
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
        DataToSign dataToSign = createDataToSign();

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
        File f = getFilePath();
        String filePath = f.getAbsolutePath();
        //System.out.println("Firma salvada en: " + filePath);

        saveDocumentToFile(docSigned, f);
        return filePath;
    }

    private X509Certificate getFirstCertificate(
            final IPKStoreManager storeManager) {
        List<X509Certificate> certs = null;

        try {
//        	List<X509Certificate> pub = storeManager.getPublicCertificates();
//        	System.out.println(pub.size());
//
//        	List<X509Certificate> tru = storeManager.getTrustCertificates();
//        	System.out.println(tru.size());

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

    public void testFirma() {
        try {
            String result = execute();
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        new TestFirma().testFirma();
    }
}
