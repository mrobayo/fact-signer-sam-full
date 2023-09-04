package ec.gob.sri.comprobantes.exception;

public class TotalDescuentoException extends Exception {
    private static final long serialVersionUID = 6933150456422042905L;

    public TotalDescuentoException(String message, Throwable cause) {
        super(message, cause);
    }

    public TotalDescuentoException(String message) {
        super(message);
    }

    public TotalDescuentoException(Throwable cause) {
        super(cause);
    }
}
