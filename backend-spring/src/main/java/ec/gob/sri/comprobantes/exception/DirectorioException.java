package ec.gob.sri.comprobantes.exception;

public class DirectorioException extends Exception {
    private static final long serialVersionUID = 6933150456422042905L;

    public DirectorioException(String message, Throwable cause) {
        super(message, cause);
    }

    public DirectorioException(String message) {
        super(message);
    }

    public DirectorioException(Throwable cause) {
        super(cause);
    }
}
