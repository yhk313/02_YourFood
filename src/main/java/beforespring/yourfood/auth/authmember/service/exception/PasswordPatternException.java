package beforespring.yourfood.auth.authmember.service.exception;

public class PasswordPatternException extends RuntimeException {
    public PasswordPatternException(String message) {
        super(message);
    }
}
