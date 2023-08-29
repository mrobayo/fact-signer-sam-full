package ec.gob.sri.types;


/**
 * TABLA 4 SRI
 * @author mrobayo Dec 24, 2014
 */
public enum SriTipoDoc {

	LOTE("00", "lote.xsd", "LOTE"),
	FACTURA("01", "factura.xsd", "FACTURA"),
	NOTACREDITO("04", "nota-credito.xsd", "NOTA DE CREDITO"),
	NOTADEBITO("05", "nota-debito.xsd", "NOTA DE DEBITO"),
	REMISION("06", "guia-remision.xsd", "GUIA DE REMISION"),
	RETENCION("07", "comprobante-rentencion.xsd", "COMP. RETENCION"),
	LIQCOMPRAS("03", "", "LIQUIDACION DE COMPRAS");

	final String value, xsd, title;

	SriTipoDoc(String value, String xsd, String title) {
		this.value = value;
		this.xsd = xsd;
		this.title = title;
	}

	public String value() {
		return value;
	}

	public String xsd() {
		return xsd;
	}

	public int intValue() {
		return Integer.parseInt(value);
	}

	public static SriTipoDoc tipo(String id) {
		if (id != null) {
			for(SriTipoDoc codigo : values()) {
				if (codigo.value.equals(id)) return codigo;
			}
		}

		return null;
	}

	public static SriTipoDoc tipo(Integer id) {
		if (id != null) {
			for(SriTipoDoc codigo : values()) {
				if (codigo.intValue() == id) return codigo;
			}
		}

		return null;
	}

	public String getTitle() {
		return title;
	}

	public static final SriTipoDoc[] GENERA_RETENCION = {
		SriTipoDoc.FACTURA, SriTipoDoc.LIQCOMPRAS
	};

	public static final SriTipoDoc[] GENERA_GUIA_REMISION = {
		SriTipoDoc.FACTURA, SriTipoDoc.LIQCOMPRAS, SriTipoDoc.NOTACREDITO, SriTipoDoc.NOTADEBITO
	};

	public static SriTipoDoc[] getGeneraRetencion() {
		return GENERA_RETENCION;
	}

	public static SriTipoDoc[] getGeneraGuiaRemision() {
		return GENERA_GUIA_REMISION;
	}


}
