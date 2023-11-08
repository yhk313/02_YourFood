package beforespring.yourfood.auth.authmember.domain;

import beforespring.yourfood.auth.authmember.exception.PasswordMismatchException;
import beforespring.yourfood.auth.authmember.service.exception.ConfirmStateException;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "auth_member",
    indexes = {
        @Index(
            name = "idx__auth_member__username",
            columnList = "username",
            unique = true
        ),
        @Index(
            name = "idx__auth_member__your_food_id",
            columnList = "your_food_id",
            unique = true
        )
    }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AuthMember {

    @Id
    @Column(name = "auth_member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ConfirmStatus status;
    @Column(name = "your_food_id")
    private Long yourFoodId;  // yourFood 서비스의 MemberId.

    @Builder
    protected AuthMember(
        Long id,
        String username,
        String raw,
        PasswordHasher hasher
    ) {
        this.id = id;
        this.username = username;
        this.password = hasher.hash(raw);
        this.status = ConfirmStatus.UNAUTHORIZED;
    }

    /**
     * 입력받은 rawPassword를 Hasher를 통해 변환했을 때, 저장된 password와 같은 지 확인하는 메서드
     *
     * @param rawPassword
     * @param hasher
     */
    public void verifyPassword(String rawPassword, PasswordHasher hasher) {
        if (!hasher.isMatch(rawPassword, password)) {
            throw new PasswordMismatchException();
        }
    }

    /**
     * 입력받은 비밀번호의 유효성을 검사한 후에 올바르면 업데이트하는 메서드
     *
     * @param rawPassword
     * @param hasher
     */
    public void updatePassword(String rawPassword, PasswordValidator validator,
        PasswordHasher hasher) {
        validator.validate(this, rawPassword, hasher);
        this.password = hasher.hash(rawPassword);
    }

    /**
     * 가입 승인 시 상태를 승인으로 변경
     */
    public void joinConfirm() {
        if (this.status == ConfirmStatus.UNAUTHORIZED) {
            this.status = ConfirmStatus.AUTHORIZED;
        }
    }

    public void updateYourFoodId(Long yourFoodMemberId) {
        this.yourFoodId = yourFoodMemberId;
    }

    public void verifyConfirmState() {
        if (!this.status.equals(ConfirmStatus.AUTHORIZED)) {
            throw new ConfirmStateException();
        }
    }
}
