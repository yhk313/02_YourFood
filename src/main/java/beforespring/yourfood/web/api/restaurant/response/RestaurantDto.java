package beforespring.yourfood.web.api.restaurant.response;

import beforespring.yourfood.app.restaurant.domain.CuisineType;

import java.math.BigDecimal;
import java.util.Set;

public record RestaurantDto(Long id,
                            String name,
                            String description,
                            String address,
                            Set<CuisineType> cuisineType,
                            BigDecimal rating) {
}
