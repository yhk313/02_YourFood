package beforespring.yourfood.batch.rawrestaurant.fetch;

/**
 * 페이지 기반으로 최신 음식점 정보를 가져오는 책임을 추상화한 클래스.
 * HTTP 요청을 통해 가져오는 상황을 가정하였음.
 */
public interface RawRestaurantFetcher {

    RawRestaurantFetchResult find(int page, int pageSize);

}
