package beforespring.yourfood.auth.authmember.domain;

public interface PasswordValidator {

    void validate(AuthMember authMember, String rawPassword, PasswordHasher passwordHasher);
}
