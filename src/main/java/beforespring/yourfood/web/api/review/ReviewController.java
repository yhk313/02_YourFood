package beforespring.yourfood.web.api.review;

import beforespring.yourfood.web.api.common.GenericResponse;
import beforespring.yourfood.web.api.review.request.ReviewCreateRequest;
import beforespring.yourfood.web.api.review.request.ReviewUpdateRequest;
import beforespring.yourfood.web.api.review.response.ReviewResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {
    /**
     * 맛집 리뷰 생성
     * @param reviewCreateRequest 새 리뷰
     */
    @PostMapping
    public GenericResponse<ReviewResponse> createReview(@RequestBody ReviewCreateRequest reviewCreateRequest) {
        return null;
    }
    /**
     * 리뷰 수정
     * @param reviewId 수정할 리뷰의 ID
     * @param reviewUpdateRequest 수정된 리뷰 내용
     */
    @PutMapping("/{reviewId}")
    public GenericResponse<ReviewResponse> updateReview(@PathVariable Long reviewId, @RequestBody ReviewUpdateRequest reviewUpdateRequest) {
        return null;
    }

    /**
     * 리뷰 삭제
     * @param reviewId 삭제할 리뷰의 ID
     */
    @DeleteMapping("/{reviewId}")
    public GenericResponse<ReviewResponse> deleteReview(@PathVariable Long reviewId) {
        return null;
    }
}
