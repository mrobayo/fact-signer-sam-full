package ec.gob.sri.comprobantes.modelo;

import java.math.BigDecimal;

public class TotalImpuesto {
    Integer codigo;
    String codigoPorcentaje;
    BigDecimal baseImponible;
    BigDecimal valor;

    public TotalImpuesto() {
    }

    public BigDecimal getBaseImponible() {
        return this.baseImponible;
    }

    public void setBaseImponible(BigDecimal baseImponible) {
        this.baseImponible = baseImponible;
    }

    public Integer getCodigo() {
        return this.codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getCodigoPorcentaje() {
        return this.codigoPorcentaje;
    }

    public void setCodigoPorcentaje(String codigoPorcentaje) {
        this.codigoPorcentaje = codigoPorcentaje;
    }

    public BigDecimal getValor() {
        return this.valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
