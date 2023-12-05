package beforespring.yourfood.app.restaurant.service;
import static beforespring.yourfood.app.utils.RestaurantComparators.sortBy;
import beforespring.yourfood.app.restaurant.domain.Restaurant;
import beforespring.yourfood.app.restaurant.domain.RestaurantQueryRepository;
import beforespring.yourfood.app.restaurant.domain.RestaurantRepository;
import beforespring.yourfood.app.restaurant.exception.RestaurantNotFoundException;
import beforespring.yourfood.app.restaurant.service.dto.CuisineGroup;
import beforespring.yourfood.app.restaurant.service.dto.RestaurantWithReviewDto;
import beforespring.yourfood.app.restaurant.service.dto.ReviewDto;
import beforespring.yourfood.app.review.domain.Review;
import beforespring.yourfood.app.review.domain.ReviewRepository;
import beforespring.yourfood.app.utils.Coordinates;
import beforespring.yourfood.app.utils.OrderBy;
import beforespring.yourfood.app.restaurant.service.dto.RestaurantDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final ReviewRepository reviewRepository;
    private final RestaurantQueryRepository restaurantQueryRepository;
    private final RestaurantGrouper restaurantGrouper;

    @Override
    @Transactional(readOnly = true)
    public RestaurantWithReviewDto getRestaurantDetail(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                                    .orElseThrow(RestaurantNotFoundException::new);
        List<Review> reviews = reviewRepository.findByRestaurantId(restaurantId);
        List<ReviewDto> reviewDtos = reviews.stream().map(ReviewDto::fromReview).collect(Collectors.toList());

        return RestaurantWithReviewDto.createFrom(restaurant, reviewDtos);
    }


    @Override
    public List<RestaurantDto> getRestaurants(
        OrderBy orderBy,
        boolean descendingOrder,
        Coordinates coordinates,
        int rangeInMeter
    ) {
        return restaurantQueryRepository
                   .findAllWithin(coordinates, rangeInMeter)
                   .stream()
                   .sorted(sortBy(orderBy, descendingOrder, coordinates))
                   .map(RestaurantDto::mapFrom)
                   .toList();
    }

    @Override
    public List<CuisineGroup> getRestaurantGroupedBy(
        OrderBy orderBy,
        boolean descendingOrder,
        Coordinates currentCoords,
        int rangeInMeters,
        int limitByCuisineType
    ) {
        return restaurantGrouper.groupByCuisineGroup(
            getRestaurants(orderBy, descendingOrder, currentCoords, rangeInMeters), limitByCuisineType);
    }

}

