package beforespring.yourfood.auth.authmember.domain;

public interface TokenSender {
    void sendEmail(String email, String token);

    String generateToken();
}
