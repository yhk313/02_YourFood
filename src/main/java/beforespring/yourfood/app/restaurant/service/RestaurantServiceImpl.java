package beforespring.yourfood.app.restaurant.service;

import beforespring.yourfood.app.restaurant.domain.Restaurant;
import beforespring.yourfood.app.restaurant.domain.RestaurantRepository;
import beforespring.yourfood.app.restaurant.exception.RestaurantNotFoundException;
import beforespring.yourfood.app.restaurant.service.dto.RestaurantWithReviewDto;
import beforespring.yourfood.app.review.domain.Review;
import beforespring.yourfood.app.restaurant.service.dto.ReviewDto;
import beforespring.yourfood.app.review.domain.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final ReviewRepository reviewRepository;

    public RestaurantWithReviewDto getRestaurantDetail(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(RestaurantNotFoundException::new);
        List<Review> reviews = reviewRepository.findByRestaurantId(restaurantId);
        List<ReviewDto> reviewDtos = ReviewDto.mapReviewsToReviewDtos(reviews);

        return RestaurantWithReviewDto.createFrom(restaurant, reviewDtos);

    }

}

