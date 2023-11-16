package beforespring.yourfood.app.restaurant.service;

import beforespring.yourfood.app.utils.OrderBy;
import beforespring.yourfood.app.restaurant.service.dto.RestaurantWithReviewDto;
import beforespring.yourfood.app.utils.Coordinates;
import beforespring.yourfood.web.api.restaurant.response.RestaurantDto;

import java.util.List;

public interface RestaurantService {
    /**
     * 레스토랑 상세 정보 조회
     * @param restaurantId 레스토랑 id
     * @return 레스토랑 및 리뷰 정보
     */
    RestaurantWithReviewDto getRestaurantDetail(Long restaurantId);

    List<RestaurantDto> getRestaurants(OrderBy orderBy, boolean descendingOrder, Coordinates coordinates, int rangeInMeter);
}
