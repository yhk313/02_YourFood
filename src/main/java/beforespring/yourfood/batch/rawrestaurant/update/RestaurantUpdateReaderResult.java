package beforespring.yourfood.batch.rawrestaurant.update;

import beforespring.yourfood.app.restaurant.domain.Restaurant;
import beforespring.yourfood.batch.rawrestaurant.model.RawRestaurant;
import com.querydsl.core.annotations.QueryProjection;


public record RestaurantUpdateReaderResult(
    RawRestaurant rawRestaurant,
    Restaurant restaurant
) {

    @QueryProjection
    public RestaurantUpdateReaderResult {
    }
}
