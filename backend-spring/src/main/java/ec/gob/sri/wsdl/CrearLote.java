package ec.gob.sri.wsdl;

//import ec.gob.sri.ce.lote.ComprobanteXml;
//import ec.gob.sri.ce.lote.LoteXml;

/**
 * Crea un lote
 *
 * @author mrobayo Dec 30, 2014
 */
public class CrearLote {

//	private String claveAcceso;
//	private String ruc;
//	private SriAmbiente ambiente;

	public final String VERSION = "1.0.0";
	public final int MAX_FILE_SIZE = 500;
	public String PARAM_TIPO_LOTE = "0";

//	private int TIEMPO_ESPERA = 10;

//	public CrearLote(Empresa emisor) {
//		String serie = "";
//		String secuencial = "";
//		String claveInterna = "";
//
//		claveAcceso = (new ClaveDeAcceso()).generaClave(new Date(),
//				SriTipoDoc.LOTE.value(), emisor.getRuc(), emisor
//						.getSriAmbiente().value(), serie, secuencial,
//				claveInterna, SriEmision.NORMAL.value());
//	}

//	public void crearXmlLote() throws MalformedURLException, WebServiceException {
//		List<File> archivos = new ArrayList<File>();
//		List respuestasAutorizacion;
//		String xmlLote = creaXmlLote(archivos);
//
//		String rutaArchivo = "path";// (new
//									// StringBuilder()).append(directorioFirmados).append(File.separator).append("lote-").append(dateFormat.format(new
//									// Date())).append(".xml").toString();
//		File archivoLote = ArchivoUtil.stringToArchivo(rutaArchivo, xmlLote);
//		EnvioComprobantesWs cliente = null;
//
//		if (archivoLote.length() / 1024L < (long) MAX_FILE_SIZE) {
//			String respuestaValidacion = ArchivoUtil.validaArchivoXml(
//					SriTipoDoc.LOTE, archivoLote.getPath(), SriUtil.VERSION_1_0);
//
//			if (respuestaValidacion == null) {
//				// FormGenerales.devuelveUrlWs(emisor.getTipoAmbiente(), "RecepcionComprobantes")
//				cliente = new EnvioComprobantesWs(
//						ClienteFileWs.PRUEBAS_RECEPCION_WSDL);
//				RespuestaSolicitud respuesta = cliente.enviarComprobanteLotes(
//						ruc, archivoLote, PARAM_TIPO_LOTE, VERSION);
//
//				String respuestaEstado = respuesta.getEstado();
//				// actualizaClaveAcceso(SriCodDoc.LOTE.value());
//
//				if (respuestaEstado.equals("RECIBIDA")) {
//					respuestasAutorizacion =
//						AutorizacionComprobantesWs.autorizarComprobanteLote(
//							claveAcceso, rutaArchivo, TIEMPO_ESPERA, archivos.size(), ambiente.value());
//
//					if (respuestasAutorizacion.isEmpty()) {
//
//					} else {
//
//					}
//
//				} else {
//
//				}
//
//			} else {
//
//			}
//
//		} else {
//
//		}
//	}

//	private String creaXmlLote(List<File> archivosSeleccionados) {
//		StringBuilder mensajes;
//		Writer writer = null;
//		String xmlLote = null;
//		mensajes = new StringBuilder();
//
//		Lote lote = new Lote();
//		lote.setClaveAcceso(claveAcceso);
//		lote.setRuc(ruc);
//		lote.setVersion(VERSION);
//
//		for (File archivo : archivosSeleccionados) {
//
//			String tipoComprobante = ArchivoUtil.obtenerValorXML(archivo,
//					"/*/infoTributaria/codDoc");
//			if (tipoComprobante != null && tipoComprobante.length() > 0) {
//
//				Comprobante item = new Comprobante();
//				item.setValue(ArchivoUtil.archivoToString(archivo.getPath()));
//				//item.setFileXML(ArchivoUtil.archivoToString(archivo.getPath()));
//				lote.getComprobantes().getComprobante().add(item);
//			} else {
//				mensajes.append("\n El archivo: ");
//				mensajes.append(archivo.getName());
//				mensajes.append(" no posee informaci\363n en <codDoc> por lo tanto fue excluido");
//			}
//		}
//
//		if (mensajes.toString().length() != 0) {
//			// JOptionPane.showMessageDialog(new JFrame(), mensajes.toString(),
//			// "Error", 0);
//
//		} else {
//			try {
//
//				XStream xstream = XStreamUtil.getLoteXStream();
//				xstream.useAttributeFor(Lote.class, "version");
//				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//				writer = new OutputStreamWriter(outputStream, "UTF-8");
//				writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
//				xstream.toXML(lote, writer);
//				xmlLote = outputStream.toString("UTF-8");
//			} catch (Exception e) {
//				if (writer != null) {
//					try {
//						writer.close();
//					} catch (IOException e1) {
//						e1.printStackTrace();
//					}
//				}
//
//			}
//		}
//
//		return xmlLote;
//	}

}
