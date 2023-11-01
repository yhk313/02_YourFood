package beforespring.yourfood.web.api.review;

import beforespring.yourfood.web.request.review.ReviewRequest;
import beforespring.yourfood.web.response.review.ReviewResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {
    /**
     * 맛집 리뷰 생성
     * @param reviewRequest 새 리뷰
     * @return
     */
    @PostMapping
    public ReviewResponse createReview(@RequestBody ReviewRequest reviewRequest) {
        return new ReviewResponse();
    }
}
