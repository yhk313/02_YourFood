package beforespring.yourfood.app.restaurant.service.dto;

import beforespring.yourfood.app.restaurant.domain.CuisineType;

import beforespring.yourfood.app.restaurant.domain.Restaurant;
import java.math.BigDecimal;
import java.util.Set;

public record RestaurantDto(Long id,
                            String name,
                            String description,
                            String address,
                            Set<CuisineType> cuisineType,
                            BigDecimal rating) {

    public static RestaurantDto mapFrom(Restaurant restaurant) {
        return new RestaurantDto(
            restaurant.getId(),
            restaurant.getName(),
            restaurant.getDescription(),
            restaurant.getAddress(),
            restaurant.getCuisineType(),
            restaurant.getRating()
        );
    }
}
