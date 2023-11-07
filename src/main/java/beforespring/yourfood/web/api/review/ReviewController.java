package beforespring.yourfood.web.api.review;

import beforespring.yourfood.web.api.common.GenericResponse;
import beforespring.yourfood.web.api.review.request.ReviewCreateRequest;
import beforespring.yourfood.web.api.review.request.ReviewUpdateRequest;
import beforespring.yourfood.app.restaurant.service.dto.ReviewDto;
import beforespring.yourfood.app.review.service.ReviewService;
import beforespring.yourfood.web.api.common.PageResponse;
import beforespring.yourfood.web.api.review.response.ReviewResponse;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
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

    @GetMapping("restaurant/{restaurantId}")
    public GenericResponse<PageResponse<ReviewDto>> getReviewsByRestaurantId(
        @PathVariable Long restaurantId,
        @RequestParam(defaultValue = "1") int page, // 페이지 번호
        @RequestParam(defaultValue = "5") int size // 페이지 크기
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ReviewDto> reviews = reviewService.getReviewsByRestaurantId(restaurantId, pageable);
        PageResponse<ReviewDto> pageResponse = PageResponse.<ReviewDto>builder()
            .page(reviews.getNumber())
            .size(reviews.getSize())
            .totalPages(reviews.getTotalPages())
            .totalElements(reviews.getTotalElements())
            .contents(reviews.getContent())
            .build();

        return GenericResponse.<PageResponse<ReviewDto>>builder()
            .statusCode(200)
            .data(pageResponse)
            .message("Success")
            .build();
    }

}
