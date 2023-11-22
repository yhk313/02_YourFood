package beforespring.yourfood.app.restaurant.service;

import beforespring.yourfood.app.exception.RestaurantNotFoundException;
import beforespring.yourfood.app.restaurant.domain.Restaurant;
import beforespring.yourfood.app.restaurant.domain.RestaurantRepository;
import beforespring.yourfood.app.review.service.event.ReviewUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;

@RequiredArgsConstructor
public class ReviewUpdatedEventHandler {

    private final RestaurantRepository restaurantRepository;

    /**
     * 수정된 리뷰의 평점을 식당 평점에 업데이트함
     *
     * @param event 리뷰 수정 이벤트
     */
    @EventListener
    public void updateModifiedReviewRating(ReviewUpdatedEvent event) {
        Restaurant restaurant = restaurantRepository.findById(event.getRestaurantId()).orElseThrow(RestaurantNotFoundException::new);
        restaurant.updateModifiedReviewRating(event.getBeforeRating(), event.getRating());
    }

}
