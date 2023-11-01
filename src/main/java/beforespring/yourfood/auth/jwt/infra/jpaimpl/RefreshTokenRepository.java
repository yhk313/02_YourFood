package beforespring.yourfood.auth.jwt.infra.jpaimpl;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenSpecific, Long> {

    Optional<RefreshTokenSpecific> findByToken(UUID token);
}
