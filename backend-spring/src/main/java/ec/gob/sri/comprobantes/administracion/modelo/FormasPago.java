package ec.gob.sri.comprobantes.administracion.modelo;

import java.math.BigDecimal;

public class FormasPago {
    private String codigo;
    private String descripcion;
    private BigDecimal total;
    private int plazo;
    private String unidadTiempo;

    public FormasPago() {
    }

    public String getCodigo() {
        return this.codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getTotal() {
        return this.total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public int getPlazo() {
        return this.plazo;
    }

    public void setplazo(int plazo) {
        this.plazo = plazo;
    }

    public String getUnidadTiempo() {
        return this.unidadTiempo;
    }

    public void setUnidadTiempo(String unidadTiempo) {
        this.unidadTiempo = unidadTiempo;
    }

    public String toString() {
        return this.getCodigo() != null ? this.getCodigo() + " - " + this.getDescripcion() : "Seleccione....";
    }
}
