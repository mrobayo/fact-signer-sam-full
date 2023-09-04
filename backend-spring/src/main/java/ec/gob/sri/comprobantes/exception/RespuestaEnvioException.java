package ec.gob.sri.comprobantes.exception;

public class RespuestaEnvioException extends Exception {
    private static final long serialVersionUID = 6933150456422042905L;

    public RespuestaEnvioException(String message, Throwable cause) {
        super(message, cause);
    }

    public RespuestaEnvioException(String message) {
        super(message);
    }

    public RespuestaEnvioException(Throwable cause) {
        super(cause);
    }
}
