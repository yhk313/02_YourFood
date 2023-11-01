package beforespring.yourfood.auth.jwt.domain;

public record AuthToken(
    String accessToken,
    String refreshToken
) {

}
