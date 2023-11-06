package beforespring.yourfood.batch.rawrestaurant;

import beforespring.yourfood.batch.rawrestaurant.model.RawRestaurant;
import java.util.List;

/**
 *
 * @param page          불러온 페이지에 대한 정보
 * @param requestedSize 요청한 아이템 숫자 (공공API는 최대 1000)
 * @param totalItems    총 아이템 숫자
 * @see RawRestaurantFetcher
 */
public record RawRestaurantFetchResult(
    int page,
    int requestedSize,
    int totalItems,
    List<RawRestaurant> rawRestaurants) {

    public RawRestaurantFetchResult(
        int page,
        int requestedSize,
        int totalItems,
        List<RawRestaurant> rawRestaurants
    ) {
        this.page = page;
        this.requestedSize = requestedSize;
        this.totalItems = totalItems;
        if (rawRestaurants == null) {
            this.rawRestaurants = List.of();
        } else {
            this.rawRestaurants = rawRestaurants.stream().toList();
        }
    }
}
