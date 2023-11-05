package beforespring.yourfood.app.restaurant.service;

import beforespring.yourfood.app.restaurant.service.dto.RestaurantWithReviewDto;

public interface RestaurantService {
    RestaurantWithReviewDto getRestaurantDetail(Long restaurantId);
}
