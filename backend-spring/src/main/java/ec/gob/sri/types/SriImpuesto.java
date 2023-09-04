package ec.gob.sri.types;

/**
 * TABLA 17
 * TipoImpuesto
 *
 * @author mrobayo Dec 24, 2014
 */
public enum SriImpuesto {

	IVA(2),
	ICE(3),
	IRBPNR(5),

	RET_RENTA(1),
	RET_IVA(2),
	RET_ISD(6);

	final int value;

	SriImpuesto(int value) {
		this.value = value;
	}

	public String value() {
		return ""+value;
	}

	public int intValue() {
		return value;
	}

	public static final SriImpuesto IMPUESTOS_TAB17[] = {
		IVA, ICE, IRBPNR
	};

	public static final SriImpuesto RETENCION_TAB20[] = {
		RET_RENTA, RET_IVA, RET_ISD
	};

	public static SriImpuesto[] getRetencionTab() {
		return RETENCION_TAB20;
	}

	public static SriImpuesto[] getImpuestosTab() {
		return IMPUESTOS_TAB17;
	}

}
