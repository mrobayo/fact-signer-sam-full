package ec.gob.sri.comprobantes.exception;

public class SecuencialException extends Exception {
    private static final long serialVersionUID = 6933150456422042905L;

    public SecuencialException(String message, Throwable cause) {
        super(message, cause);
    }

    public SecuencialException(String message) {
        super(message);
    }

    public SecuencialException(Throwable cause) {
        super(cause);
    }
}
