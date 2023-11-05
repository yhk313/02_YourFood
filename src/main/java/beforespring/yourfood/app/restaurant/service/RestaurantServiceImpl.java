package beforespring.yourfood.app.restaurant.service;

import beforespring.yourfood.app.restaurant.domain.Restaurant;
import beforespring.yourfood.app.restaurant.domain.RestaurantRepository;
import beforespring.yourfood.app.restaurant.service.dto.RestaurantWithReviewDto;
import beforespring.yourfood.app.review.domain.Review;
import beforespring.yourfood.app.restaurant.service.dto.ReviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;

    @Override
    public RestaurantWithReviewDto getRestaurantDetail(Long restaurantId) {
        List<Object[]> results = restaurantRepository.findRestaurantWithReviews(restaurantId);

        Restaurant restaurant = (Restaurant) results.get(0)[0];
        List<Review> reviews = results.stream()
            .map(result -> (Review) result[1])
            .collect(Collectors.toList());

        List<ReviewDto> reviewDtos = ReviewDto.mapReviewsToReviewDtos(reviews);

        return RestaurantWithReviewDto.createFromRestaurant(restaurant, reviewDtos);
    }

}
