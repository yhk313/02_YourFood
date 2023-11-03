package beforespring.yourfood.app.member.domain;

import beforespring.yourfood.app.utils.Coordinates;
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
    @Embedded
    private Coordinates coordinates;

    @Column(name = "lunch_noti_status")
    private boolean lunchNotiStatus;

    @Column(name = "noti_agreed_at")
    private LocalDateTime notiAgreedAt;

    @Builder
    public Member(String username) {
        this.username = username;
        this.lunchNotiStatus = false;
    }

    /**
     * 회원의 위도와 경도 업데이트
     *
     * @param coordinates 좌표
     */
    public void updateMemberLocation(Coordinates coordinates) {
        this.coordinates = coordinates;
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
