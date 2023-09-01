package ec.gob.sri.comprobantes.administracion.modelo;

import ec.gob.sri.comprobantes.modelo.InformacionAdicionalProducto;

import java.math.BigDecimal;
import java.util.List;

public class Producto {
    private Integer codigo;
    private String codigoPrincipal;
    private String codigoAuxiliar;
    private String nombre;
    private BigDecimal valorUnitario;
    private String tipoProducto;
    private List<InformacionAdicionalProducto> infoAdicionalList;
    private List<ImpuestoProducto> impuestoProducto;
    private List<ImpuestoValor> impuestoValor;
    private Integer codigoImpuesto;
    private String iva;
    private String ice;
    private String irbpnr;
    private BigDecimal baseImponileIRBPNR;
    private Double cantidad;

    public Producto() {
    }

    public Integer getCodigo() {
        return this.codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getCodigoPrincipal() {
        return this.codigoPrincipal;
    }

    public void setCodigoPrincipal(String codigoPrincipal) {
        this.codigoPrincipal = codigoPrincipal;
    }

    public String getCodigoAuxiliar() {
        return this.codigoAuxiliar;
    }

    public void setCodigoAuxiliar(String codigoAuxiliar) {
        this.codigoAuxiliar = codigoAuxiliar;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getValorUnitario() {
        return this.valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public String getTipoProducto() {
        return this.tipoProducto;
    }

    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public List<InformacionAdicionalProducto> getInfoAdicionalList() {
        return this.infoAdicionalList;
    }

    public void setInfoAdicionalList(List<InformacionAdicionalProducto> infoAdicionalList) {
        this.infoAdicionalList = infoAdicionalList;
    }

    public List<ImpuestoProducto> getImpuestoProducto() {
        return this.impuestoProducto;
    }

    public void setImpuestoProducto(List<ImpuestoProducto> impuestoProducto) {
        this.impuestoProducto = impuestoProducto;
    }

    public String getIva() {
        return this.iva;
    }

    public void setIva(String iva) {
        this.iva = iva;
    }

    public String getIce() {
        return this.ice;
    }

    public void setIce(String ice) {
        this.ice = ice;
    }

    public List<ImpuestoValor> getImpuestoValor() {
        return this.impuestoValor;
    }

    public void setImpuestoValor(List<ImpuestoValor> impuestoValor) {
        this.impuestoValor = impuestoValor;
    }

    public Double getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getCodigoImpuesto() {
        return this.codigoImpuesto;
    }

    public void setCodigoImpuesto(Integer codigoImpuesto) {
        this.codigoImpuesto = codigoImpuesto;
    }

    public String getIrbpnr() {
        return this.irbpnr;
    }

    public void setIrbpnr(String irbpnr) {
        this.irbpnr = irbpnr;
    }

    public BigDecimal getBaseImponileIRBPNR() {
        return this.baseImponileIRBPNR;
    }

    public void setBaseImponileIRBPNR(BigDecimal baseImponileIRBPNR) {
        this.baseImponileIRBPNR = baseImponileIRBPNR;
    }

    public boolean verificarProducto(String codigoPrincipal, String codigoAuxiliar, String nombre) {
        return this.codigoPrincipal.equals(codigoPrincipal) && this.codigoAuxiliar.equals(codigoAuxiliar) && this.nombre.equals(nombre);
    }
}
