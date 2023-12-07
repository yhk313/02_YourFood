package beforespring.yourfood.web.api.restaurant.response;

import beforespring.yourfood.app.restaurant.service.dto.RestaurantDto;
import java.util.List;


public record RestaurantListResponse(List<RestaurantDto> restaurantDtos) {

}

