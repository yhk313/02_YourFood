package beforespring.yourfood.member.domain;

import beforespring.yourfood.utils.Coordinate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "member",
    indexes = {
        @Index(
            name = "idx__member__username__lunch_noti_status",
            columnList = "username, lunch_noti_status",
            unique = true
        ),
        @Index(
            name = "idx__member__email",
            columnList = "email",
            unique = true
        )
    }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "auth_profile_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private AuthProfile authProfile;

    @Embedded
    private Coordinate coordinate;

    @Column(name = "lunch_noti_status", columnDefinition = "TINYINT(1)")
    private boolean lunchNotiStatus;

    @Column(name = "noti_agreed_at")
    private LocalDateTime notiAgreedAt;

    @Builder
    public Member(AuthProfile authProfile) {
        this.username = authProfile.getUsername();
        this.authProfile = authProfile;
        this.lunchNotiStatus = false;
    }

    /**
     * 회원의 위도와 경도 업데이트
     *
     * @param coordinate 좌표
     */
    public void updateMemberLocation(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    /**
     * 회원의 점심 추천 동의 상태 업데이트
     *
     * @param lunchNotiStatus 동의 상태
     * @param date   동의 날짜
     */
    public void updateLunchNotiStatus(boolean lunchNotiStatus, LocalDateTime date) {
        this.lunchNotiStatus = lunchNotiStatus;
        this.notiAgreedAt = date;
    }
}
