package beforespring.yourfood.batch.rawrestaurant;

import org.junit.jupiter.api.Test;

class DummyRawRestaurantFetcherTest {

    DummyRawRestaurantFetcher mockRawRestaurantFetcher = new DummyRawRestaurantFetcher(51L);

    @Test
    void find() {
        RawRestaurantFetchResult rawRestaurantFetchResult = mockRawRestaurantFetcher.find(5, 10);
        RawRestaurantFetchResult rawRestaurantFetchResult2 = mockRawRestaurantFetcher.find(6, 10);
        System.out.println();
    }
}