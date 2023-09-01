package ec.gob.sri.comprobantes.administracion.modelo;

import java.math.BigInteger;

public class Comprobante {
    private String codigo;
    private BigInteger inicioSecuencia;

    public Comprobante(String codigo, BigInteger inicioSecuencia) {
        this.codigo = codigo;
        this.inicioSecuencia = inicioSecuencia;
    }

    public Comprobante() {
    }

    public String getCodigo() {
        return this.codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public BigInteger getInicioSecuencia() {
        return this.inicioSecuencia;
    }

    public void setInicioSecuencia(BigInteger inicioSecuencia) {
        this.inicioSecuencia = inicioSecuencia;
    }
}
