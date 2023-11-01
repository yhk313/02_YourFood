package beforespring.yourfood.auth.authmember.domain;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
class AuthMemberTest {

    private PasswordHasher defaultHasher;

    @BeforeEach
    public void setup() {
        defaultHasher = new PasswordHasher() {
            @Override
            public String hash(String toHash) {
                return toHash;
            }

            @Override
            public boolean isMatch(String raw, String hashed) {
                return hash(raw).equals(hashed);
            }
        };
    }

    @Test
    @DisplayName("빌더를 통해 멤버 생성")
    public void create_member_use_builder() {
        // given

        AuthMember authMember = AuthMember.builder()
                            .username("User1")
                            .raw("1234")
                            .id(1L)
                            .hasher(defaultHasher)
                            .build();

        // when then
        assertEquals(1L, authMember.getId());
        assertEquals("User1", authMember.getUsername());
        assertEquals("1234", authMember.getPassword());
    }
}