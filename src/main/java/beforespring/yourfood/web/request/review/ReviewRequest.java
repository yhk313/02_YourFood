package beforespring.yourfood.web.request.review;

public record ReviewRequest(Long reviewId, Long restaurantId, int rating, String content, double restaurantRating) {
}
