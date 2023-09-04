package ec.gob.sri.comprobantes.xml;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LectorXPath {
    private String xmlFile;
    private Document xmlDocument;
    private XPath xPath;

    public LectorXPath(String xmlFile) {
        this.xmlFile = xmlFile;
        this.inicializar();
    }

    private void inicializar() {
        try {
            this.xmlDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(this.xmlFile);
            this.xPath = XPathFactory.newInstance().newXPath();
        } catch (IOException var2) {
            Logger.getLogger(LectorXPath.class.getName()).log(Level.SEVERE, (String)null, var2);
        } catch (SAXException var3) {
            Logger.getLogger(LectorXPath.class.getName()).log(Level.SEVERE, (String)null, var3);
        } catch (ParserConfigurationException var4) {
            Logger.getLogger(LectorXPath.class.getName()).log(Level.SEVERE, (String)null, var4);
        }

    }

    public Object leerArchivo(String expression, QName returnType) {
        try {
            XPathExpression xPathExpression = this.xPath.compile(expression);
            return xPathExpression.evaluate(this.xmlDocument, returnType);
        } catch (XPathExpressionException var4) {
            Logger.getLogger(LectorXPath.class.getName()).log(Level.SEVERE, (String)null, var4);
            return null;
        }
    }
}
