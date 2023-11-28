package beforespring.yourfood.app.review.exception;

public class InvalidOrderByException extends IllegalArgumentException{
    public InvalidOrderByException(String orderBy) {
        super("Invalid orderBy: " + orderBy);
    }
}
