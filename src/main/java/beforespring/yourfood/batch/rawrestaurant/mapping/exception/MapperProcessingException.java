package beforespring.yourfood.batch.rawrestaurant.mapping.exception;

public class MapperProcessingException extends RuntimeException{
    public MapperProcessingException() {
        super();
    }

    public MapperProcessingException(String message) {
        super(message);
    }

    public MapperProcessingException(String message, Throwable cause) {
        super(message, cause);
    }

    public MapperProcessingException(Throwable cause) {
        super(cause);
    }

    protected MapperProcessingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
