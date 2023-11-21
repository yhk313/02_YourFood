package beforespring.yourfood.app.review.domain;

import beforespring.yourfood.app.member.domain.Member;
import beforespring.yourfood.app.restaurant.domain.Restaurant;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Review {
    @Id
    @Column(name = "review_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member; //추후 memberId만 참조하는 것으로 변경

    @JoinColumn(name = "restaurant_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Restaurant restaurant;

    private String content;

    @Column(columnDefinition = "TINYINT", nullable = false)
    private Integer beforeRating;

    @Column(columnDefinition = "TINYINT", nullable = false)
    private Integer rating;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder
    public Review(
        Member member,
        Restaurant restaurant,
        String content,
        Integer rating) {
        this.member = member;
        this.restaurant = restaurant;
        this.content = content;
        this.rating = rating;
        this.createdAt = LocalDateTime.now();
        this.beforeRating = 0;
    }

    /**
     * 리뷰 업데이트.
     *
     * @param content 수정할 내용
     * @param rating  수정할 평점
     */
    public void updateReview(String content, Integer rating) {
        this.content = content;
        this.beforeRating = this.rating;
        this.rating = rating;
        this.updatedAt = LocalDateTime.now();
    }
}
