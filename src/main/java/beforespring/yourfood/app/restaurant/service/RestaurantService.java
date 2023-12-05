package beforespring.yourfood.app.restaurant.service;

import beforespring.yourfood.app.restaurant.service.dto.CuisineGroup;
import beforespring.yourfood.app.restaurant.service.dto.RestaurantWithReviewDto;
import beforespring.yourfood.app.utils.Coordinates;
import beforespring.yourfood.app.utils.OrderBy;
import beforespring.yourfood.app.restaurant.service.dto.RestaurantDto;

import java.util.List;

public interface RestaurantService {
    /**
     * 레스토랑 상세 정보 조회
     *
     * @param restaurantId 레스토랑 id
     * @return 레스토랑 및 리뷰 정보
     */
    RestaurantWithReviewDto getRestaurantDetail(Long restaurantId);

    List<RestaurantDto> getRestaurants(OrderBy orderBy, boolean descendingOrder, Coordinates coordinates, int rangeInMeter);

    /**
     * 현재 좌표와 반경 내에서 찾은 레스토랑을 CuisineType 에 따라 그룹화하여 조회
     *
     * @param currentCoords 현재 좌표
     * @param rangeInMeters 반경
     * @return CuisineInfo  CuisineType 과 해당 CuisineType 에 속하는 상위 5개(평점순) 맛집
     */
    List<CuisineGroup> getRestaurantGroupedBy(OrderBy orderBy, boolean descendingOrder, Coordinates currentCoords, int rangeInMeters, int limitByCuisineType);
}
