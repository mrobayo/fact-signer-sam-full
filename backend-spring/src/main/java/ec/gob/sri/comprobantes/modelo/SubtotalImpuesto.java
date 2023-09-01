package ec.gob.sri.comprobantes.modelo;

import ec.gob.sri.comprobantes.administracion.modelo.Producto;
import java.math.BigDecimal;

public class SubtotalImpuesto {
    Producto producto;
    private Integer codigoImpuesto;
    private String codigo;
    private BigDecimal porcentaje;
    private BigDecimal baseImponible;
    private BigDecimal subtotal;
    private BigDecimal valorIce;

    public BigDecimal getValorIce() {
        return this.valorIce;
    }

    public void setValorIce(BigDecimal valorIce) {
        this.valorIce = valorIce;
    }

    public SubtotalImpuesto(Producto producto, Integer codigoImpuesto, String codigo, BigDecimal porcentaje, BigDecimal baseImponible, BigDecimal subtotal) {
        this.producto = producto;
        this.codigoImpuesto = codigoImpuesto;
        this.codigo = codigo;
        this.porcentaje = porcentaje;
        this.baseImponible = baseImponible;
        this.subtotal = subtotal;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public BigDecimal getSubtotal() {
        return this.subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public Integer getCodigoImpuesto() {
        return this.codigoImpuesto;
    }

    public void setCodigoImpuesto(Integer codigoImpuesto) {
        this.codigoImpuesto = codigoImpuesto;
    }

    public BigDecimal getPorcentaje() {
        return this.porcentaje;
    }

    public void setPorcentaje(BigDecimal porcentaje) {
        this.porcentaje = porcentaje;
    }

    public BigDecimal getBaseImponible() {
        return this.baseImponible;
    }

    public void setBaseImponible(BigDecimal baseImponible) {
        this.baseImponible = baseImponible;
    }

    public boolean verificarProducto(Producto producto) {
        return this.producto.verificarProducto(producto.getCodigoPrincipal(), producto.getCodigoAuxiliar(), producto.getNombre());
    }
}
