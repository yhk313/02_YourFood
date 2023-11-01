package beforespring.yourfood.auth.jwt.domain;

import java.time.LocalDateTime;
import lombok.Builder;

public record RefreshToken(
    String refreshToken,
    Long issuedToId,
    String issuedToUsername,
    LocalDateTime expiresAt
) {
    @Builder
    public RefreshToken {
    }
}
