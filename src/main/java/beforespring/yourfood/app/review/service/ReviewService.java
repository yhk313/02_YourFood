package beforespring.yourfood.app.review.service;

import beforespring.yourfood.app.restaurant.service.dto.ReviewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {
    /**
     * 신규 리뷰 등록
     * @param memberId 리뷰 작성 회원 id
     * @param restaurantId 식당 id
     * @param content 리뷰 내용
     * @param rating 식당 평점
     */
    public void saveReview(Long memberId, Long restaurantId, String content, Integer rating);

    /**
     * 리뷰 수정
     * @param reviewId 수정할 리뷰 id
     * @param memberId 수정할 회원 id
     * @param content 리뷰 내용
     * @param rating 식당 평점
     */
    public void updateReview(Long reviewId, Long memberId, String content, Integer rating);

    /**
     * 특정 레스토랑의 리뷰
     * @param restaurantId 레스토랑 id
     * @param pageable 페이지 처리를 위한 정보
     * @return 리뷰
     */
    Page<ReviewDto> getReviewsByRestaurantId(Long restaurantId, Pageable pageable);
}
