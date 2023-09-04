package ec.gob.sri.comprobantes.exception;

public class ArchivoException extends Exception {
    private static final long serialVersionUID = 6933150456422042905L;

    public ArchivoException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArchivoException(String message) {
        super(message);
    }

    public ArchivoException(Throwable cause) {
        super(cause);
    }
}
