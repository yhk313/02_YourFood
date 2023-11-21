package beforespring.yourfood.app.restaurant.infra;

import beforespring.yourfood.app.restaurant.domain.Restaurant;
import beforespring.yourfood.app.restaurant.domain.RestaurantRatingUpdater;
import beforespring.yourfood.app.review.domain.Review;

import java.util.List;

public class RestaurantRatingUpdaterImpl implements RestaurantRatingUpdater {

    @Override
    public void updateNewRatings(Restaurant restaurant, List<Review> reviews) {
        if (reviews.isEmpty())
            return;
        restaurant.updateNewReviewRatings(reviews); // 리뷰 업데이터 -> restaurant, reviews
        for (Review review : reviews) {
            review.isReflectedRating(true);
        }
    }

    @Override
    public void updateModifiedRating(Restaurant restaurant, List<Review> reviews) {
        if (reviews.isEmpty())
            return;
        restaurant.updateModifiedReviewRatings(reviews);
        for (Review review : reviews) {
            review.isReflectedRating(true);
        }
    }
}
