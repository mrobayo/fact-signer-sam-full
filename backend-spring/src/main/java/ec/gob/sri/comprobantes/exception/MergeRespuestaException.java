package ec.gob.sri.comprobantes.exception;

public class MergeRespuestaException extends Exception {
    private static final long serialVersionUID = 6933150456422042905L;

    public MergeRespuestaException(String message, Throwable cause) {
        super(message, cause);
    }

    public MergeRespuestaException(String message) {
        super(message);
    }

    public MergeRespuestaException(Throwable cause) {
        super(cause);
    }
}
