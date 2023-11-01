package beforespring.yourfood.auth.authmember.domain;

import beforespring.yourfood.auth.authmember.exception.TokenMismatchException;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(
    name = "confirm",
    indexes = {
        @Index(
            name = "idx__confirm__member_id",
            columnList = "member_id",
            unique = true
        )
    }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Confirm {

    @Id
    @Column(name = "confirm_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private AuthMember authMember;
    @Column(nullable = false)
    private String token;

    @Builder
    public Confirm(AuthMember authMember, String token) {
        this.authMember = authMember;
        this.token = token;
    }

    public void updateToken(String token) {
        this.token = token;
    }

    /**
     * 생성된 토큰과 사용자가 입력한 토큰이 동일한지 검증
     *
     * @param token
     */
    public void verifyToken(String token) {
        if (!this.token.equals(token))
            throw new TokenMismatchException();
    }

}