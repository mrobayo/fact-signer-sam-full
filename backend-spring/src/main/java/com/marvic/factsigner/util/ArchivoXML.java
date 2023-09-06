package com.marvic.factsigner.util;

import com.marvic.factsigner.exception.AppException;
import com.marvic.factsigner.model.comprobantes.Comprobante;
import com.marvic.factsigner.model.comprobantes.FacturaComp;
import ec.gob.sri.comprobantes.modelo.factura.Factura;
import ec.gob.sri.comprobantes.xml.ValidadorEstructuraDocumento;
import ec.gob.sri.types.SriTipoDoc;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class ArchivoXML {

    private static final Logger LOG = LoggerFactory.getLogger(ArchivoXML.class);

    private static Object getJaxbDocument(Comprobante doc) {
        if (StringUtils.isBlank(doc.getClaveAcceso())) {
            throw new AppException("CLAVE DE ACCESO ES REQUERIDA");
        }
        try {
            Factura xml = Model2XML.generarComprobante((FacturaComp) doc);
            return xml;
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException(e);
        }
    }

    private static String guardarDoc(Comprobante entidad) {
        SriTipoDoc tipoDoc = entidad.getTipoDoc();
        // RepositorioSri repoSri = getRepositorioSri(auth);

        // Valida documento
        Object xmlObject = getJaxbDocument(entidad);
        //SriUtil.validaXml(xmlObject, tipoDoc);

        // Generar documento
        SriUtil.generarXml(xmlObject, tipoDoc, entidad.getClaveAcceso());

        // Firmar documento
        // RutaCert cert = RutaCert.init(getDao().findById(Empresa.class, auth.getEmpresaId()));
        String signeFile = ""; //SriUtil.firmarXml(xmlObject, tipoDoc, entidad.getInfoTributaria().getClaveAcceso(), repoSri, cert);

        // Actualiza el estado
        //Doc doc = (Doc) getDao().findById(Doc.getSubType(tipoDoc), entidad.getId());

        //doc.setDirectorio(DirectorioEnum.FIRMADOS);
        //doc.setEstadoDoc(EstadoDoc.EMITIDO);
        //getDao().storeEntity(doc);
        return signeFile;
    }

    public static String validaXmlContent(SriTipoDoc tipo, String xmlContent,
                                          String version) {
        String respuesta = null;

        String rutaXsd = ""; // rutaXsd(tipo, version);

        try {
            ValidadorEstructuraDocumento validador = new ValidadorEstructuraDocumento();

            validador.setArchivoXSD(new File(rutaXsd));
            respuesta = ""; //validador.validacion(xmlContent);

        } catch (Exception ex) {
            LOG.error(null, ex);
        }

        return respuesta;
    }

}
