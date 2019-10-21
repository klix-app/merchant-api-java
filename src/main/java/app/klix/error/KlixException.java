package app.klix.error;

public class KlixException extends RuntimeException {

    public KlixException(String message) {
        super(message);
    }

    public KlixException(String message, Throwable cause) {
        super(message, cause);
    }

}
