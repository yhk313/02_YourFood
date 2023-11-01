package beforespring.yourfood.auth.jwt.service;

import beforespring.yourfood.auth.jwt.domain.AccessTokenGenerator;
import beforespring.yourfood.auth.jwt.domain.AuthToken;
import beforespring.yourfood.auth.jwt.domain.RefreshToken;
import beforespring.yourfood.auth.jwt.domain.RefreshTokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class JwtIssuerImpl implements JwtIssuer {

    private final AccessTokenGenerator accessTokenGenerator;

    private final RefreshTokenManager refreshTokenManager;

    @Override
    public AuthToken issue(Long memberId, String username) {
        RefreshToken refreshToken = refreshTokenManager.issue(memberId, username);
        String accessToken = accessTokenGenerator.generate(memberId, username);
        return new AuthToken(accessToken, refreshToken.refreshToken());
    }

    @Override
    public AuthToken renew(String refreshToken, String username) {
        RefreshToken renewedRefreshToken = refreshTokenManager.renew(refreshToken, username);
        String accessToken = accessTokenGenerator.generate(
            renewedRefreshToken.issuedToId(),
            renewedRefreshToken.issuedToUsername()
        );
        return new AuthToken(accessToken, renewedRefreshToken.refreshToken());
    }
}
