package beforespring.yourfood.auth.jwt.domain;

public interface AccessTokenGenerator {

    String generate(Long memberId, String username);
}
