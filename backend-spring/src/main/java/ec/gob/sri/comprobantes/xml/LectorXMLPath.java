package ec.gob.sri.comprobantes.xml;

import ec.gob.sri.comprobantes.exception.ConvertidorXMLException;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LectorXMLPath {
    private byte[] archivoXML;
    private QName returnType;

    public LectorXMLPath(byte[] archivoXML, QName returnType) {
        this.archivoXML = archivoXML;
        this.returnType = returnType;
    }

    private Document parseDocumento(byte[] archivoXML) throws ConvertidorXMLException {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputStream inputStream = new ByteArrayInputStream(archivoXML);
            Document document = db.parse(new InputSource(inputStream));
            return document;
        } catch (SAXException var6) {
            Logger.getLogger(LectorXPath.class.getName()).log(Level.SEVERE, (String)null, var6);
            throw new ConvertidorXMLException("Se produjo un error al convetir el archivo al formato XML");
        } catch (IOException var7) {
            Logger.getLogger(LectorXPath.class.getName()).log(Level.SEVERE, (String)null, var7);
            throw new ConvertidorXMLException("Se produjo un error al convetir el archivo al formato XML");
        } catch (ParserConfigurationException var8) {
            Logger.getLogger(LectorXPath.class.getName()).log(Level.SEVERE, (String)null, var8);
            throw new ConvertidorXMLException("Se produjo un error al convetir el archivo al formato XML");
        }
    }

    public Object obtenerValorXML(String expression) throws ConvertidorXMLException {
        try {
            Document xmlDocument = this.parseDocumento(this.archivoXML);
            XPath xPath = XPathFactory.newInstance().newXPath();
            XPathExpression xPathExpression = xPath.compile(expression);
            return xPathExpression.evaluate(xmlDocument, this.returnType);
        } catch (XPathExpressionException var5) {
            Logger.getLogger(LectorXMLPath.class.getName()).log(Level.SEVERE, (String)null, var5);
            throw new ConvertidorXMLException("Se produjo un error al convetir el archivo al formato XML");
        }
    }

    public String getClaveAcceso() throws ConvertidorXMLException {
        return (String)this.obtenerValorXML("/*/infoTributaria/claveAcceso");
    }

    public String getCodDoc() throws ConvertidorXMLException {
        return (String)this.obtenerValorXML("/*/infoTributaria/codDoc");
    }
}
