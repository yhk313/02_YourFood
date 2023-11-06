package beforespring.yourfood.web.api.review.request;

import lombok.Builder;

public record ReviewUpdateRequest(Long memberId, int rating, String content) {
    @Builder
    public ReviewUpdateRequest {
    }
}
