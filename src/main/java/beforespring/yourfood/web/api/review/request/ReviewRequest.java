package beforespring.yourfood.web.api.review.request;

public record ReviewRequest(Long reviewId, Long restaurantId, int rating, String content, double restaurantRating) {
}
