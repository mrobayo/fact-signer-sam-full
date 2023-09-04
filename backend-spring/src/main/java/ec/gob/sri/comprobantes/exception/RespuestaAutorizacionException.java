package ec.gob.sri.comprobantes.exception;

public class RespuestaAutorizacionException extends Exception {
    private static final long serialVersionUID = 6933150456422042905L;

    public RespuestaAutorizacionException(String message, Throwable cause) {
        super(message, cause);
    }

    public RespuestaAutorizacionException(String message) {
        super(message);
    }

    public RespuestaAutorizacionException(Throwable cause) {
        super(cause);
    }
}
