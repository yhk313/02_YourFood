package beforespring.yourfood.web.api.restaurant.response;
import lombok.Builder;
import java.util.List;


public record RestaurantListResponse(List<RestaurantDto> restaurantDtos) {
    @Builder
    public RestaurantListResponse {
    }
}

