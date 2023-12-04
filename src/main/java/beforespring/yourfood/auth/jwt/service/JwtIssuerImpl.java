package beforespring.yourfood.auth.jwt.service;

import beforespring.yourfood.auth.jwt.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class JwtIssuerImpl implements JwtIssuer {

    private final AccessTokenGenerator accessTokenGenerator;

    private final RefreshTokenManager refreshTokenManager;

    @Override
    public AuthToken issue(Long authMemberId, String username, Long serviceMemberId) {
        RefreshToken refreshToken = refreshTokenManager.issue(authMemberId, username);
        String accessToken = accessTokenGenerator.generate(authMemberId, username, serviceMemberId);
        return new AuthToken(accessToken, refreshToken.refreshToken());
    }

    @Override
    public AuthToken renew(String refreshToken, String username, Long serviceMemberId) {
        RefreshToken renewedRefreshToken = refreshTokenManager.renew(refreshToken, username);
        String accessToken = accessTokenGenerator.generate(
            renewedRefreshToken.issuedToId(),
            renewedRefreshToken.issuedToUsername(),
            serviceMemberId
        );
        return new AuthToken(accessToken, renewedRefreshToken.refreshToken());
    }
}
