package beforespring.yourfood.app.restaurant.service;

import beforespring.yourfood.app.utils.OrderBy;
import beforespring.yourfood.app.restaurant.domain.Restaurant;
import beforespring.yourfood.app.restaurant.domain.RestaurantQueryRepository;
import beforespring.yourfood.app.restaurant.domain.RestaurantRepository;
import beforespring.yourfood.app.restaurant.exception.RestaurantNotFoundException;
import beforespring.yourfood.app.restaurant.service.dto.RestaurantWithReviewDto;
import beforespring.yourfood.app.restaurant.service.dto.ReviewDto;
import beforespring.yourfood.app.review.domain.Review;
import beforespring.yourfood.app.review.domain.ReviewRepository;
import beforespring.yourfood.app.review.exception.ReviewNotFoundException;
import beforespring.yourfood.app.utils.Coordinates;
import beforespring.yourfood.web.api.restaurant.response.RestaurantDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static beforespring.yourfood.app.utils.RestaurantComparators.byDistance;
import static beforespring.yourfood.app.utils.RestaurantComparators.byRatingAverage;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final ReviewRepository reviewRepository;
    private final RestaurantQueryRepository restaurantQueryRepository;

    @Override
    @Transactional(readOnly = true)
    public RestaurantWithReviewDto getRestaurantDetail(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(RestaurantNotFoundException::new);
        List<Review> reviews = reviewRepository.findByRestaurantId(restaurantId);
        List<ReviewDto> reviewDtos = ReviewDto.mapReviewsToReviewDtos(reviews);

        return RestaurantWithReviewDto.createFrom(restaurant, reviewDtos);
    }

    @Override
    public List<Restaurant> getRestaurantsByRating(boolean descendingOrder, Coordinates coordinates, int rangeInMeter) {
        List<Restaurant> restaurantsInLocation = restaurantQueryRepository.findAllWithin(coordinates, rangeInMeter);
        // 평점순 정렬
        restaurantsInLocation.sort(byRatingAverage(descendingOrder));
        return restaurantsInLocation;
    }

    @Override
    public void updateNewReviewRating(Long restaurantId, Long reviewId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(RestaurantNotFoundException::new);
        Review review = reviewRepository.findById(reviewId).orElseThrow(ReviewNotFoundException::new);
        restaurant.updateNewReviewRating(review);
    }

    @Override
    public void updateModifiedReviewRating(Long restaurantId, Long reviewId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(RestaurantNotFoundException::new);
        Review review = reviewRepository.findById(reviewId).orElseThrow(ReviewNotFoundException::new);
        restaurant.updateModifiedReviewRating(review);
    }
    
    @Override
    public List<Restaurant> getRestaurantsByDistance(boolean descendingOrder, Coordinates coordinates, int rangeInMeter) {
        List<Restaurant> restaurantsInLocation = restaurantQueryRepository.findAllWithin(coordinates, rangeInMeter);
        // 거리순 정렬
        restaurantsInLocation.sort(byDistance(descendingOrder, coordinates));
        return restaurantsInLocation;
    }
  
    @Override
    public List<RestaurantDto> getRestaurants(OrderBy orderBy, boolean descendingOrder, Coordinates coordinates, int rangeInMeter) {
        List<Restaurant> restaurantsInLocation = new ArrayList<>(restaurantQueryRepository.findAllWithin(coordinates, rangeInMeter));

        restaurantsInLocation.sort(
            (orderBy == OrderBy.RATING) ?
                byRatingAverage(descendingOrder) :
                byDistance(descendingOrder, coordinates)
        );

        return restaurantsInLocation.stream()
            .map(restaurant -> new RestaurantDto(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getDescription(),
                restaurant.getAddress(),
                restaurant.getCuisineType(),
                restaurant.getRating()
            ))
            .collect(Collectors.toList());
    }
}

