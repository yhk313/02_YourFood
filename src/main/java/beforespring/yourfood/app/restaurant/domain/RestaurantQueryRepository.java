package beforespring.yourfood.app.restaurant.domain;

import beforespring.yourfood.app.utils.Coordinates;
import java.util.List;


public interface RestaurantQueryRepository {
    /**
     * 반경 n 미터 안의 모든 식당을 불러옴.
     * 현재 필요상 반경 n 미터 안의 데이터를 불러올 때 DB에서 기준(ex. 평점순) 정렬을 할 필요는 없어보임.
     * 정렬은 애플리케이션 단에서 해도 될 것 같음.
     */
    List<Restaurant> findAllWithin(Coordinates currentCoords, int rangeInMeter);
}
