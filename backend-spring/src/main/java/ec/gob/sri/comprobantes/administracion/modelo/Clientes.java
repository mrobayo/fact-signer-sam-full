package ec.gob.sri.comprobantes.administracion.modelo;

public class Clientes {
    private Integer codCliente;
    private String apellido;
    private String tipoIdentificacion;
    private String numeroIdentificacio;
    private String telefonoConvencional;
    private String extencion;
    private String celular;
    private String correo;
    private String direccion;
    private String tipoCliente;

    public Clientes() {
    }

    public Integer getCodCliente() {
        return this.codCliente;
    }

    public void setCodCliente(Integer codCliente) {
        this.codCliente = codCliente;
    }

    public String getApellido() {
        return this.apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTipoIdentificacion() {
        return this.tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getNumeroIdentificacio() {
        return this.numeroIdentificacio;
    }

    public void setNumeroIdentificacio(String numeroIdentificacio) {
        this.numeroIdentificacio = numeroIdentificacio;
    }

    public String getTelefonoConvencional() {
        return this.telefonoConvencional;
    }

    public void setTelefonoConvencional(String telefonoConvencional) {
        this.telefonoConvencional = telefonoConvencional;
    }

    public String getExtencion() {
        return this.extencion;
    }

    public void setExtencion(String extencion) {
        this.extencion = extencion;
    }

    public String getCelular() {
        return this.celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCorreo() {
        return this.correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTipoCliente() {
        return this.tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }
}
