package beforespring.yourfood.auth.jwt.domain.exception;

public class RefreshTokenExpirationException extends RefreshTokenRenewException {

    static final public RefreshTokenExpirationException INSTANCE = new RefreshTokenExpirationException();
}
