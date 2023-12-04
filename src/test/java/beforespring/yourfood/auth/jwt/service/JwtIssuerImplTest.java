package beforespring.yourfood.auth.jwt.service;

import static beforespring.Fixture.randString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.BDDMockito.given;

import beforespring.Fixture;
import beforespring.yourfood.auth.jwt.domain.AccessTokenGenerator;
import beforespring.yourfood.auth.jwt.domain.AuthToken;
import beforespring.yourfood.auth.jwt.domain.RefreshToken;
import beforespring.yourfood.auth.jwt.domain.RefreshTokenManager;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class JwtIssuerImplTest {

    JwtIssuerImpl jwtIssuer;

    @Mock
    AccessTokenGenerator accessTokenGenerator;

    @Mock
    RefreshTokenManager refreshTokenManager;

    Long givenAuthMemberId;
    Long givenServiceMemberId;
    String givenUsername;
    String expectingAccessToken;
    String expectingRefreshToken;
    String expectingRenewedRefreshToken;

    @BeforeEach
    void init() {
        givenAuthMemberId = Fixture.randomPositiveLong();
        givenServiceMemberId = Fixture.randomPositiveLong();
        givenUsername = randString();
        expectingAccessToken = givenUsername + givenAuthMemberId.toString();
        expectingRefreshToken = UUID.randomUUID().toString();
        jwtIssuer = new JwtIssuerImpl(accessTokenGenerator, refreshTokenManager);
    }


    @Test
    void issue() {
        // given
        given(accessTokenGenerator.generate(givenAuthMemberId, givenUsername, givenServiceMemberId))
            .willReturn(expectingAccessToken);

        given(refreshTokenManager.issue(givenAuthMemberId, givenUsername))
            .willReturn(new RefreshToken(expectingRefreshToken, givenAuthMemberId, givenUsername, LocalDateTime.now()));

        // when
        AuthToken issued = jwtIssuer.issue(givenAuthMemberId, givenUsername, givenServiceMemberId);

        // then
        assertThat(tuple(issued.accessToken(), issued.refreshToken()))
            .isEqualTo(tuple(expectingAccessToken, expectingRefreshToken));
    }

    @Test
    void renew() {
        // given
        given(accessTokenGenerator.generate(givenAuthMemberId, givenUsername, givenServiceMemberId))
            .willReturn(expectingAccessToken);

        given(refreshTokenManager.renew(expectingRefreshToken, givenUsername))
            .willReturn(new RefreshToken(expectingRenewedRefreshToken, givenAuthMemberId, givenUsername, LocalDateTime.now()));

        String givenRefreshToken = expectingRefreshToken;

        // when
        AuthToken renew = jwtIssuer.renew(givenRefreshToken, givenUsername, givenServiceMemberId);

        // then
        assertThat(renew)
            .isNotNull()
            .isEqualTo(new AuthToken(expectingAccessToken, expectingRenewedRefreshToken));
    }
}