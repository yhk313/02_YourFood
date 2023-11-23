package beforespring.yourfood.app.review.domain;

import beforespring.yourfood.app.review.domain.event.ReviewCreatedEvent;
import beforespring.yourfood.app.review.domain.event.ReviewUpdatedEvent;
import beforespring.yourfood.app.review.exception.MemberMismatchException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
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
    private Long memberId;

    @JoinColumn(name = "restaurant_id", nullable = false)
    private Long restaurantId;

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
        Long memberId,
        Long restaurantId,
        String content,
        Integer rating) {
        this.memberId = memberId;
        this.restaurantId = restaurantId;
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
    public void updateReview(String content, Integer rating, ApplicationEventPublisher publisher) {
        validateMemberId(memberId);
        this.content = content;
        this.beforeRating = this.rating;
        this.rating = rating;
        this.updatedAt = LocalDateTime.now();
        publisher.publishEvent(new ReviewUpdatedEvent(this.restaurantId, this.rating, this.beforeRating));
    }

    public Review posted(ApplicationEventPublisher publisher) {
        publisher.publishEvent(new ReviewCreatedEvent(this.restaurantId, this.rating));
        return this;
    }

    //todo 추후 리뷰를 검증하는 로직을 인터페이스로 분리하여 멤버의 권한, 금지어 검증 등을 할 수 있음

    /**
     * 리뷰를 작성한 회원과 리뷰를 수정하는 회원이 일치하는지 확인함
     *
     * @param memberId 리뷰 수정 member Id
     */
    public void validateMemberId(Long memberId) {
        if (!this.memberId.equals(memberId)) {
            throw new MemberMismatchException();
        }
    }
}
