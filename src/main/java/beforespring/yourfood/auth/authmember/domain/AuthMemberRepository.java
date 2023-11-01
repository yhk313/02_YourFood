package beforespring.yourfood.auth.authmember.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface AuthMemberRepository extends JpaRepository<AuthMember, Long> {
    Optional<AuthMember> findByUsername(String username);
}