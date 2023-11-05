package beforespring.yourfood.app.restaurant.service.dto;

import beforespring.yourfood.app.restaurant.domain.AddressCode;
import beforespring.yourfood.app.restaurant.domain.CuisineType;
import beforespring.yourfood.app.restaurant.domain.Restaurant;
import beforespring.yourfood.app.utils.Coordinate;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

public record RestaurantWithReviewDto(String name,
                                      String address,
                                      String description,
                                      AddressCode addressCode,
                                      Coordinate coordinate,
                                      CuisineType cuisineType,
                                      BigDecimal rating,
                                      boolean operating,
                                      boolean deleted,
                                      List<ReviewDto> reviews) {
    @Builder
    public RestaurantWithReviewDto {
    }

    public static RestaurantWithReviewDto createFromRestaurant(
        Restaurant restaurant, List<ReviewDto> reviewDtos) {
        return new RestaurantWithReviewDto(
            restaurant.getName(),
            restaurant.getAddress(),
            restaurant.getDescription(),
            restaurant.getAddressCode(),
            restaurant.getCoordinate(),
            restaurant.getCuisineType(),
            restaurant.getRating(),
            restaurant.isOperating(),
            restaurant.isDeleted(),
            reviewDtos
        );
    }
}
