package beforespring.yourfood.app.restaurant.service.dto;

import beforespring.yourfood.app.restaurant.domain.CuisineType;
import java.util.List;


public record CuisineGroup(
    CuisineType cuisineType,
    List<RestaurantDto> restaurants
) {
}