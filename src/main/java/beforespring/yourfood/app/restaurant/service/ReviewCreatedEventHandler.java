package beforespring.yourfood.app.restaurant.service;

import beforespring.yourfood.app.exception.RestaurantNotFoundException;
import beforespring.yourfood.app.restaurant.domain.Restaurant;
import beforespring.yourfood.app.restaurant.domain.RestaurantRepository;
import beforespring.yourfood.app.review.domain.event.ReviewCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;

@RequiredArgsConstructor
public class ReviewCreatedEventHandler {

    private final RestaurantRepository restaurantRepository;

    /**
     * 새로운 리뷰 평점을 식당 평점에 업데이트함
     *
     * @param event 리뷰 생성 이벤트
     */
    @EventListener
    public void updateNewReviewRating(ReviewCreatedEvent event) {
        Restaurant restaurant = restaurantRepository.findById(event.getRestaurantId()).orElseThrow(RestaurantNotFoundException::new);
        restaurant.updateNewReviewRating(event.getRating());
    }
}
