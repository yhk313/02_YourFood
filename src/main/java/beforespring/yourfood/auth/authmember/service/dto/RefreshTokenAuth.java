package beforespring.yourfood.auth.authmember.service.dto;

public record RefreshTokenAuth(
    String username,
    String refreshToken
) {

}
