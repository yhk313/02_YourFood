package beforespring.yourfood.app.recommendation.infra;

import beforespring.yourfood.app.recommendation.domain.Recommendation;
import beforespring.yourfood.app.recommendation.domain.Recommender;
import beforespring.yourfood.app.recommendation.domain.Subscriber;
import beforespring.yourfood.app.restaurant.service.RestaurantService;
import beforespring.yourfood.app.restaurant.service.dto.CuisineGroup;
import beforespring.yourfood.app.utils.OrderBy;

import java.util.List;

/**
 * 위치기반으로 주변 식당을 카테고리별로 추천한다.
 * <ol>
 *  <li>반경 n미터 내에 있는 음식점을 조회한다.</li>
 *  <li>카테고리 별로 음식점을 그룹화 한다.</li>
 *  <li>평점순으로 상위 n개의 음식점믈 반환한다.</li>
 * </ol>
 */
public class RecommenderImpl implements Recommender {

    private final RestaurantService restaurantService;
    private final int rangeInMeters;
    private final int limitByCuisineType;

    /**
     * @param restaurantService  음식점 서비스
     * @param rangeInMeters      범위 (미터)
     * @param limitByCuisineType 카테고리별 음식점 제한 수
     */
    public RecommenderImpl(RestaurantService restaurantService, int rangeInMeters,
        int limitByCuisineType) {
        this.restaurantService = restaurantService;
        this.rangeInMeters = rangeInMeters;
        this.limitByCuisineType = limitByCuisineType;
    }

    @Override
    public Recommendation getRecommendation(Subscriber subscriber) {
        List<CuisineGroup> cuisineGroups = restaurantService.getRestaurantGroupedBy(
            OrderBy.RATING,
            true, // true: 평점 높은 순 false: 평점 낮은 순
            subscriber.coordinates(),
            rangeInMeters,
            limitByCuisineType);
        return new Recommendation(subscriber, cuisineGroups);
    }
}
