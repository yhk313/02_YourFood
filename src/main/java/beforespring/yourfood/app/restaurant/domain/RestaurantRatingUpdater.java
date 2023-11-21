package beforespring.yourfood.app.restaurant.domain;

import beforespring.yourfood.app.review.domain.Review;

import java.util.List;

public interface RestaurantRatingUpdater {
    /**
     * 새로운 리뷰 중 아직 반영되지 않은 평점을 평균 평점에 반영함.
     *
     * @param restaurant 수정할 레스토랑
     * @param reviews 평점이 반영되지 않은 새로운 리뷰
     */
    public void updateNewRatings(Restaurant restaurant, List<Review> reviews);

    /**
     * 수정할 리뷰 중 아직 반영되지 않은 평점을 평균 평점에 반영함.
     *
     * @param restaurant 수정할 레스토랑
     * @param reviews 평점이 반영되지 않은 수정된 리뷰
     */
    public void updateModifiedRating(Restaurant restaurant, List<Review> reviews);

}
