package beforespring.yourfood.auth.authmember.service.exception;

public class ConfirmStateException extends IllegalStateException {

    public ConfirmStateException() {
    }

    public ConfirmStateException(String s) {
        super(s);
    }

    public ConfirmStateException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfirmStateException(Throwable cause) {
        super(cause);
    }
}
