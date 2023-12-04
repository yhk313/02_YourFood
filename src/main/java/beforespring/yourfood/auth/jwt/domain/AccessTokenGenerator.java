package beforespring.yourfood.auth.jwt.domain;

public interface AccessTokenGenerator {
    String generate(Long authMemberId, String username, Long serviceMemberId);
}
