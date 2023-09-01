package ec.gob.sri.comprobantes.administracion.modelo;

public class ConfiguracionDirectorio {
    private Integer codigoDirectorio;
    private String path;

    public ConfiguracionDirectorio() {
    }

    public ConfiguracionDirectorio(int codigoDirectorio, String path) {
        this.codigoDirectorio = codigoDirectorio;
        this.path = path;
    }

    public Integer getCodigoDirectorio() {
        return this.codigoDirectorio;
    }

    public String getPath() {
        return this.path;
    }

    public void setCodigoDirectorio(Integer codigoDirectorio) {
        this.codigoDirectorio = codigoDirectorio;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
