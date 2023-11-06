package beforespring.yourfood.config.exception;

public class CsvIOException extends RuntimeException {
    public CsvIOException() {
        super();
    }

    public CsvIOException(String message) {
        super(message);
    }

    public CsvIOException(String message, Throwable cause) {
        super(message, cause);
    }

    public CsvIOException(Throwable cause) {
        super(cause);
    }
}
