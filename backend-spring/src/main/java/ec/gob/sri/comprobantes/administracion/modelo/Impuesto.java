package ec.gob.sri.comprobantes.administracion.modelo;

public class Impuesto {
    private Integer codigo;
    private String descripcion;
    private String estado;
    private Double porcentaje;

    public Impuesto() {
    }

    public Integer getCodigo() {
        return this.codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String toString() {
        return this.getDescripcion();
    }

    public Double getPorcentaje() {
        return this.porcentaje;
    }

    public void setPorcentaje(Double porcentaje) {
        this.porcentaje = porcentaje;
    }
}
