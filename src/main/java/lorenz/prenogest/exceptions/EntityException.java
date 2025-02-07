package lorenz.prenogest.exceptions;

public class EntityException extends Exception {

    private static final long serialVersionUID = 1L;

    public EntityException(String message) {
        super(message);
    }

    public EntityException(Throwable cause) {
        super(cause);
    }

    public EntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public EntityException(String[] args) {
        super(String.format("%s entity error occurred. [%s], [%s], [%s]",
                args[0].toUpperCase(),
                args[1].toUpperCase(),
                args[2].toUpperCase(),
                args[3].toUpperCase()));
    }

}
