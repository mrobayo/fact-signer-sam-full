package com.marvic.factsigner.util;

import com.marvic.factsigner.exception.AppException;
import ec.gob.sri.types.SriAmbiente;
//import ec.gob.sri.types.SriEmision;
import ec.gob.sri.types.SriTipoDoc;
//import ec.gob.sri.wsdl.ArchivoUtil;
//import openjsoft.ciper.services.xbes.types.DirectorioEnum;
//import openjsoft.core.model.exception.AppException;
//import openjsoft.core.utils.DateUtil;
//import openjsoft.core.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Locale;

/**
 * Clase utilitaria SRI
 * http://www.itx.ec/modulo-validacion-11/
 * http://www.eclectica.ca/howto/modulus-11-self-check.php
 *
 * @author mrobayo Dec 27, 2014
 */
public class SriUtil {

	protected static final Logger LOG = LoggerFactory.getLogger(SriUtil.class);

	public static final int AUTORIZACION_LEN = 37;

	public static final String VERSION_1_0 = "1.0.0", VERSION_1_1 = "1.1.0";
	public static final String FMT_SEC = "%09d", FMT_PTO = "%03d";
	public static final BigDecimal LIMITE_CONSUMIDOR_FINAL = new BigDecimal(20);

	public static final int MAX_FILE_SIZE = 500 * 1024;

	private static NumberFormat currency = new DecimalFormat("#,###,##0.00",
					DecimalFormatSymbols.getInstance(new Locale("es", "EC")));

	/**
	 * Formato con decimles
	 */
	public static String currency(BigDecimal n) {
		return currency.format(n);
	}

	/**
	 * Formats a NumDoc 001001000000001 -> 001-001-000000001
	 */
	public static String fmtNum(String nombre) throws AppException {
		String sucursal = "", pnto = "", secuencia = "";
		if (nombre != null) {
			if (nombre.length() >= 3) sucursal = nombre.substring(0, 3);
			if (nombre.length() >= 6) pnto = nombre.substring(3, 6);
			if (nombre.length() > 6) secuencia = nombre.substring(6);
		}
		return String.format("%s-%s-%s", sucursal, pnto, secuencia);
	}

	/**
	 * Fix numdoc: 1-1-1 -> 001001000000001
	 */
	public static String fixNumFmt(String numdoc, boolean throwsException) throws AppException {
		String num = StringUtils.trimToEmpty(numdoc).replaceAll("[^\\d\\-]", "");

		if (num.isEmpty()) {
			return "";
		}
		else if (num.length() != 15) {

			if (num.indexOf("-") > 0) {
				Integer items[] = Utils.splitToInt(num, "-");
				if (items.length != 3) {
					if (throwsException)
						throw new AppException("Documento de Sustento invalido caracteres");
					else {
						return null;
					}
				}
				else {
					num = String.format(SriUtil.FMT_PTO, items[0]) + "" + String.format(SriUtil.FMT_PTO, items[1]) + "" + String.format(SriUtil.FMT_SEC, items[2]);
					return num;
				}
			}
			else if (throwsException) {
				throw new AppException("Numero de Documento debe tener 15 digitos");
			}
			else {
				return null;
			}
		}
		else {
			return num;
		}
	}

	/**
	 * Genera document XML
	 *
	 * @param xmlObject
	 * @param tipoDoc
	 * @return
	 * @throws AppException
	 */
	public static void validaXml(Object xmlObject, SriTipoDoc tipoDoc) throws AppException {
		String versionXsd = SriUtil.VERSION_1_0;
		if (tipoDoc == SriTipoDoc.FACTURA || tipoDoc == SriTipoDoc.REMISION || tipoDoc == SriTipoDoc.NOTACREDITO) {
			versionXsd = SriUtil.VERSION_1_1;
		}

		String xmlContent = xmlNotSigned(xmlObject, tipoDoc);
		String result = ArchivoXML.validaXmlContent(tipoDoc, xmlContent, versionXsd);

		if (result != null) {
			throw new AppException(result);
		}
	}

	/**
	 * Genera Archivo XML
	 *
	 * @param xmlObject
	 * @param tipoDoc
	 * @param claveAcceso
	 * @return
	 * @throws AppException
	 */
	public static String generarXml(Object xmlObject, SriTipoDoc tipoDoc, String claveAcceso) throws AppException {
		// Generar documento XML
		try {
			//DirectorioEnum directorio = DirectorioEnum.GENERADOS;
			String xmlContent = xmlNotSigned(xmlObject, tipoDoc);

			String path = ""; // TODO repo.guardarArchivo(xmlContent, tipoDoc, claveAcceso, directorio).getAbsolutePath();
			LOG.info("GENERADO: {}", path);

			return path;

		} catch(Exception e) {
			e.printStackTrace();
			throw new AppException(e);
		}
	}

	/**
	 * Firma documento y genera archivo XML
	 *
	 * @param xmlObject
	 * @param tipoDoc
	 * @param claveAcceso
	 * @return
	 * @throws AppException
	 */
//	public static String firmarXml(Object xmlObject, SriTipoDoc tipoDoc,
//			String claveAcceso) throws AppException {
//		// Firmar documento XML
//		Document docToSign = null;
//		try {
//			docToSign = getDocument(xmlObject);
//
//		} catch (ParserConfigurationException | JAXBException e) {
//			e.printStackTrace();
//			throw new AppException(e);
//		}
//
//		String docName = ""; //TODO RepositorioSri.getSignedDoc(tipoDoc, claveAcceso);
//		XAdESBESSignature signature = new XAdESBESSignature(docToSign, docName, cert);
//
//		signature.setOutputDir( repo.getDir(DirectorioEnum.FIRMADOS) );
//
//        String signedFile = signature.execute();
//        if (StringUtils.isEmpty(signedFile)) {
//        	throw new AppException("FALLO FIRMA DE DOCUMENTO");
//        }
//
//        LOG.info("FIRMADO: {}", signedFile);
//        return signedFile;
//	}

	/**
	 * W3C Document
	 *
	 * @param jaxb
	 * @return
	 * @throws ParserConfigurationException
	 * @throws JAXBException
	 */
	public static Document getDocument(Object jaxb)
			throws ParserConfigurationException, JAXBException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		// dbf.setNamespaceAware(true);
		Document doc = dbf.newDocumentBuilder().newDocument();

		JAXBContext context = JAXBContext.newInstance(jaxb.getClass());
		context.createMarshaller().marshal(jaxb, doc);

		return doc;
	}

	/**
	 * XML Not Signed
	 *
	 * @param xmlObject
	 * @param tipoDoc
	 * @return
	 */
	public static String xmlNotSigned(Object xmlObject, SriTipoDoc tipoDoc) {
		StringWriter xmlContent = new StringWriter();

		try {
			JAXBContext context = JAXBContext.newInstance(tipoDoc.getJaxbClass());
			Marshaller m = context.createMarshaller();

			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.marshal(xmlObject, xmlContent);

		} catch (JAXBException e) {
			e.printStackTrace();
		}

		return xmlContent.toString();
	}

	/**
	 * Genera un codigo de 49 digitos
	 *
	 * @param fecha
	 * @param tipo
	 * @param ruc
	 * @param ambiente
	 * @param codigoNumerico
	 * @return
	 */
	public static String claveAcceso(
			Date fecha,
			SriTipoDoc tipo,
			String ruc,
			SriAmbiente ambiente,
			String serie,
			String secuencial,
			String codigoNumerico
	) {
		return claveAcceso(ddmmyyyy(fecha),
				tipo, ruc, ambiente, serie, "", secuencial, codigoNumerico);
	}

	public static String claveAcceso(
			String fechaDMY,
			SriTipoDoc tipo,
			String ruc,
			SriAmbiente ambiente,
			String estab,
			String punto,
			String secuencial,
			String codigoNumerico
	) {
		StringBuffer clave = new StringBuffer();

		String dmy = fechaDMY.replaceAll("\\D", ""); //non digit: [^0-9]

		clave.append(dmy);    // 8
		clave.append(tipo.value());       // 2
		clave.append(ruc);          //13
		clave.append(ambiente.value());   // 1
		clave.append(estab);              // 3
		clave.append(punto);              // 3
		clave.append(secuencial);         // 9
		clave.append(codigoNumerico);     // 8
		clave.append(1);                  // 1

		String cadena = clave.toString();
		final int digitoVerificador = modulo11(cadena);

		cadena = clave.append(digitoVerificador).toString();

		if (cadena.length() != 49) {
			throw new AppException("Cadena " + cadena.length() + " incorrecta -> " + cadena);
		}

		return cadena;
	}

	/**
	 * Clave contingencia
	 *
	 * @param fechaEmision
	 * @param tipoComprobante
	 * @param clavesContigencia
	 * @return
	 * @throws InputMismatchException
	 */
	public static String claveContingencia(Date fechaEmision, SriTipoDoc tipoComprobante, String clavesContigencia)
			throws InputMismatchException {

        String fecha = ddmmyyyy(fechaEmision);
		StringBuilder clave = new StringBuilder(fecha);
		clave.append(tipoComprobante.value());
		clave.append(clavesContigencia);
		clave.append(1);

		final Integer verificador = modulo11(clave.toString());

		String cadena = clave.append(verificador).toString();
		if (cadena.length() != 49) {
			throw new AppException("Cadena " + cadena.length() + " incorrecta -> " + cadena);
		}

	    return cadena;
	}

	public static void validaMod11(final String cadena) throws AppException {
		int a = modulo11(StringUtils.substring(cadena, 0, -1));
		int b = Integer.parseInt(StringUtils.substring(cadena, -1));
		if (a != b) {
			throw new AppException("%s != %s", a, b);
		}
		else {
			//System.out.println(cadena + " OK" );
		}
	}

	/**
	 * Calcula el modulo 11
	 * 11->0
	 * 10->1
	 */
	public static int modulo11(final String cadena) {

		int pivote = 2;
		final String invertida = invertirCadena(cadena);
        int longitudCadena = invertida.length();

        int cantidadTotal = 0;
        int b = 1;

        for (int i = 0; i < longitudCadena; i++) {
            if (pivote == 8) {
                pivote = 2;
            }
            int temporal = Integer.parseInt("" + invertida.substring(i, b));
            b++;
            temporal *= pivote;
            pivote++;
            cantidadTotal += temporal;
        }
        cantidadTotal = 11 - cantidadTotal % 11;

        switch (cantidadTotal) {
        	case 10:
        		cantidadTotal = 1;
        		break;
        	case 11:
        		cantidadTotal = 0;
        		break;
        }

        return cantidadTotal;
	}

	private static String invertirCadena(final String cadena) {
        String cadenaInvertida = "";
        for (int x = cadena.length() - 1; x >= 0; x--) {
            cadenaInvertida = cadenaInvertida + cadena.charAt(x);
        }
        return cadenaInvertida;
    }

	private static String ddmmyyyy(Date fecha) {
		return Utils.fmtDMY(fecha).replaceAll("\\D", ""); //non digit: [^0-9]
	}

	/**
	 * TipoDoc: Tipo de Comprobante: 8,2
	 */
	public static SriTipoDoc getTipoDocClaveAcceso(String claveAcceso) {
		String tipo = claveAcceso.substring(8, 10);
		return SriTipoDoc.tipo(tipo);
	}

}
