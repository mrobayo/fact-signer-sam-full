package ec.gob.sri.types;

public enum SriAmbiente {

	PRUEBAS("1"),
	PRODUCCION("2");

	final String value;

	SriAmbiente(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}

	public static SriAmbiente fromCode(String code) {
		for(SriAmbiente s : values()) {
			if (s.value.equals(code)) return s;
		}
		return null;
	}

}
