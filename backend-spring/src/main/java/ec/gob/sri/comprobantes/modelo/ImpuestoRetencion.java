package ec.gob.sri.comprobantes.modelo;

import java.math.BigDecimal;
import java.util.List;

public class ImpuestoRetencion {
    private Integer codigo;
    private String codigoRetencion;
    private BigDecimal baseImponible;
    private BigDecimal porcentajeRetener;
    private BigDecimal valorRetenido;
    private String codDocSustento;
    private String numDocSustento;
    private String fechaEmisionDocSustento;
    private String nombreImpuesto;
    private List<CampoAdicional> camposAdicionales;

    public ImpuestoRetencion(Integer codigo, String codigoRetencion, BigDecimal porcentajeRetener, BigDecimal baseImponible, BigDecimal valorRetenido, String codDocSustento, String numDocSustento, String fechaEmisionDocSustento) {
        this.codigo = codigo;
        this.codigoRetencion = codigoRetencion;
        this.porcentajeRetener = porcentajeRetener;
        this.baseImponible = baseImponible;
        this.valorRetenido = valorRetenido;
        this.codDocSustento = codDocSustento;
        this.numDocSustento = numDocSustento;
        this.fechaEmisionDocSustento = fechaEmisionDocSustento;
    }

    public String getNombreImpuesto() {
        return this.nombreImpuesto;
    }

    public void setNombreImpuesto(String nombreImpuesto) {
        this.nombreImpuesto = nombreImpuesto;
    }

    public Integer getCodigo() {
        return this.codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getCodigoRetencion() {
        return this.codigoRetencion;
    }

    public void setCodigoRetencion(String codigoRetencion) {
        this.codigoRetencion = codigoRetencion;
    }

    public BigDecimal getPorcentajeRetener() {
        return this.porcentajeRetener;
    }

    public void setPorcentajeRetener(BigDecimal porcentajeRetener) {
        this.porcentajeRetener = porcentajeRetener;
    }

    public BigDecimal getBaseImponible() {
        return this.baseImponible;
    }

    public void setBaseImponible(BigDecimal baseImponible) {
        this.baseImponible = baseImponible;
    }

    public BigDecimal getValorRetenido() {
        return this.valorRetenido;
    }

    public void setValorRetenido(BigDecimal valorRetenido) {
        this.valorRetenido = valorRetenido;
    }

    public String getCodDocSustento() {
        return this.codDocSustento;
    }

    public void setCodDocSustento(String codDocSustento) {
        this.codDocSustento = codDocSustento;
    }

    public String getNumDocSustento() {
        return this.numDocSustento;
    }

    public void setNumDocSustento(String numDocSustento) {
        this.numDocSustento = numDocSustento;
    }

    public String getFechaEmisionDocSustento() {
        return this.fechaEmisionDocSustento;
    }

    public void setFechaEmisionDocSustento(String fechaEmisionDocSustento) {
        this.fechaEmisionDocSustento = fechaEmisionDocSustento;
    }

    public List<CampoAdicional> getCamposAdicionales() {
        return this.camposAdicionales;
    }

    public void setCamposAdicionales(List<CampoAdicional> camposAdicionales) {
        this.camposAdicionales = camposAdicionales;
    }
}
