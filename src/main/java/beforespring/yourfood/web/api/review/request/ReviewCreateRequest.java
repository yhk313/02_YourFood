package beforespring.yourfood.web.api.review.request;

import lombok.Builder;

public record ReviewCreateRequest(Long reviewId, Long restaurantId, int rating, String content) {
    @Builder
    public ReviewCreateRequest {
    }
}
