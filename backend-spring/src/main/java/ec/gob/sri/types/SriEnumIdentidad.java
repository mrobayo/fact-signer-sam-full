package ec.gob.sri.types;

/**
 * TABLA 6
 * Identificacion SRI
 * @author mrobayo Dec 24, 2014
 */
public enum SriEnumIdentidad {

	RUC("04"),
	CED("05"),
	PAS("06"),
	FIN("07"),
	EXT("08");

	final String value;

	SriEnumIdentidad(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}


}
