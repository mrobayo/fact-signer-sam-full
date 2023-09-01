package ec.gob.sri.comprobantes.administracion.modelo;

public class ImpuestoProducto {
    private Integer codigoProducto;
    private String codigoImpuesto;

    public ImpuestoProducto() {
    }

    public ImpuestoProducto(String codigoImpuesto) {
        this.codigoImpuesto = codigoImpuesto;
    }

    public Integer getCodigoProducto() {
        return this.codigoProducto;
    }

    public void setCodigoProducto(Integer codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getCodigoImpuesto() {
        return this.codigoImpuesto;
    }

    public void setCodigoImpuesto(String codigoImpuesto) {
        this.codigoImpuesto = codigoImpuesto;
    }
}
