package ec.gob.sri.types;

public enum SriTarifaIva {

    IVA_VENTA_0(0),
    IVA_VENTA_12(2),
    IVA_NO_OBJETO(6),
    IVA_EXCENTO(7);

    private final int value;

    SriTarifaIva(int value) {
    	this.value = value;
    }

    public String value() {
		return ""+value;
	}

	public int intValue() {
		return value;
	}

}
