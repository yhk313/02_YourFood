package beforespring.yourfood.batch.rawrestaurant.fetch;

import beforespring.yourfood.batch.rawrestaurant.model.RawRestaurant;

public record RawRestaurantReaderResult(
    RawRestaurant newlyFetched,
    RawRestaurant existing
) {

}
