package beforespring.yourfood.batch.rawrestaurant.update;

import beforespring.yourfood.app.restaurant.domain.Restaurant;
import beforespring.yourfood.app.restaurant.domain.RestaurantRepository;
import java.util.List;
import org.springframework.batch.item.ItemWriter;

public class RestaurantUpdateDataJpaWriter implements ItemWriter<Restaurant> {

    private final RestaurantRepository restaurantRepository;

    public RestaurantUpdateDataJpaWriter(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public void write(List<? extends Restaurant> items) throws Exception {
        List<Restaurant> filtered = (List<Restaurant>) items.stream().filter(
            restaurant -> restaurant.getId() == null
        ).toList();

        restaurantRepository.saveAll(filtered);
    }
}
