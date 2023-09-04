package ec.gob.sri.comprobantes.xml;

import org.xml.sax.SAXException;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;

public class ValidadorEstructuraDocumento {
    private File archivoXSD;
    private File archivoXML;

    public ValidadorEstructuraDocumento() {
    }

    public String validacion() {
        this.validarArchivo(this.archivoXSD, "archivoXSD");
        this.validarArchivo(this.archivoXML, "archivoXML");
        String mensaje = null;
        SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");

        Schema schema;
        try {
            schema = schemaFactory.newSchema(this.archivoXSD);
        } catch (SAXException var7) {
            throw new IllegalStateException("Existe un error en la sintaxis del esquema", var7);
        }

        Validator validator = schema.newValidator();

        try {
            validator.validate(new StreamSource(this.archivoXML));
            return (String)mensaje;
        } catch (Exception var6) {
            return var6.getMessage();
        }
    }

    protected void validarArchivo(File archivo, String nombre) throws IllegalStateException {
        if (null == archivo || archivo.length() <= 0L) {
            throw new IllegalStateException(nombre + " es nulo o esta vacio");
        }
    }

    public File getArchivoXSD() {
        return this.archivoXSD;
    }

    public void setArchivoXSD(File archivoXSD) {
        this.archivoXSD = archivoXSD;
    }

    public File getArchivoXML() {
        return this.archivoXML;
    }

    public void setArchivoXML(File archivoXML) {
        this.archivoXML = archivoXML;
    }
}
