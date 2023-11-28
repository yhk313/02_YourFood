package beforespring.yourfood.web.api.review;

import beforespring.yourfood.app.utils.OrderBy;
import beforespring.yourfood.web.api.common.GenericResponse;
import beforespring.yourfood.web.api.review.request.ReviewCreateRequest;
import beforespring.yourfood.web.api.review.request.ReviewUpdateRequest;
import beforespring.yourfood.app.restaurant.service.dto.ReviewDto;
import beforespring.yourfood.app.review.service.ReviewService;
import beforespring.yourfood.web.api.common.PageResponse;
import beforespring.yourfood.web.api.review.response.ReviewResponse;
import org.springframework.data.web.PageableDefault;
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


    /**
     * 특정 레스토랑에 대한 리뷰 목록을 페이징하여 조회
     *
     * @param desc         정렬을 내림차순으로 할지 여부. 기본값은 false
     * @param orderBy      정렬 기준
     * @param restaurantId 레스토랑의 ID
     * @param pageable     페이지 크기
     * @return 리뷰 목록
     */
    @GetMapping("")
    public GenericResponse<PageResponse<ReviewDto>> findReviewsByRestaurantIdOrderBy(
        @RequestParam(value = "desc", required = false, defaultValue = "false") boolean desc,
        @RequestParam(value = "orderBy", required = false) OrderBy orderBy,
        @RequestParam("restaurantId") Long restaurantId,
        @PageableDefault(size = 5) Pageable pageable
    ) {
        Page<ReviewDto> reviewPage = reviewService.findReviewsByRestaurantIdOrderBy(desc, orderBy, restaurantId, pageable);
        PageResponse<ReviewDto> pageResponse = PageResponse.<ReviewDto>builder()
            .contents(reviewPage.getContent())
            .page(reviewPage.getNumber())
            .size(reviewPage.getSize())
            .totalElements(reviewPage.getTotalElements()).build();

        return GenericResponse.<PageResponse<ReviewDto>>builder()
            .statusCode(200)
            .data(pageResponse)
            .message("Success")
            .build();
    }



}
