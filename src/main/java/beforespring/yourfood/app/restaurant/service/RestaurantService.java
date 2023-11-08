package beforespring.yourfood.app.restaurant.service;

import beforespring.yourfood.app.restaurant.domain.Restaurant;
import beforespring.yourfood.app.restaurant.service.dto.RestaurantWithReviewDto;
import beforespring.yourfood.app.utils.Coordinates;

import java.util.List;

public interface RestaurantService {
    /**
     * 레스토랑 상세 정보 조회
     * @param restaurantId 레스토랑 id
     * @return 레스토랑 및 리뷰 정보
     */
    RestaurantWithReviewDto getRestaurantDetail(Long restaurantId);

    /**
     * 평점순으로 레스토랑 목록 조회
     * @param descendingOrder 정렬 여부
     * @param coordinates 좌표
     * @param rangeInMeter 반경
     * @return 평점순으로 정렬된 레스토랑 목록
     */
    List<Restaurant> getRestaurantsByRating(boolean descendingOrder, Coordinates coordinates, int rangeInMeter);

    /**
     * 거리순으로 레스토랑 목록 조회
     * @param descendingOrder 정렬 여부
     * @param coordinates 좌표
     * @param rangeInMeter 반경
     * @return 거리순으로 정렬된 레스토랑 목록
     */
    List<Restaurant> getRestaurantsByDistance(boolean descendingOrder, Coordinates coordinates, int rangeInMeter);
}
