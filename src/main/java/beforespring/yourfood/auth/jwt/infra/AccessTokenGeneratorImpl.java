package beforespring.yourfood.auth.jwt.infra;

import beforespring.yourfood.config.JwtProperties;
import beforespring.yourfood.auth.jwt.domain.AccessTokenGenerator;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccessTokenGeneratorImpl implements AccessTokenGenerator {

    private final JwtProperties jwtProperties;
    private final JwtEncoder jwtEncoder;

    @Override
    public String generate(Long memberId, String username) {
        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                                     .issuer(jwtProperties.getIssuer())
                                     .issuedAt(Instant.now())
                                     .expiresAt(
                                         Instant.now()
                                             .plusSeconds(jwtProperties.getAccessTokenLifespanInMinutes() * 60)
                                     )
                                     .subject(jwtProperties.getSubject())
                                     .claim("memberId", memberId.toString())
                                     .claim("username", username)
                                     .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }
}
