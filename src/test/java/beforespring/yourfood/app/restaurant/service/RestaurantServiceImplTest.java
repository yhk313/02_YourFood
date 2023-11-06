package beforespring.yourfood.app.restaurant.service;

import beforespring.yourfood.app.member.domain.Member;
import beforespring.yourfood.app.restaurant.domain.Restaurant;
import beforespring.yourfood.app.restaurant.domain.RestaurantRepository;
import beforespring.yourfood.app.restaurant.service.dto.RestaurantWithReviewDto;
import beforespring.yourfood.app.review.domain.Review;
import beforespring.yourfood.app.review.domain.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class RestaurantServiceImplTest {
    @InjectMocks
    private RestaurantServiceImpl restaurantService;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private ReviewRepository reviewRepository;
    @Test
    public void testGetRestaurantDetail() {
        Long restaurantId = 1L;
        String restaurantName = "Test restaurantName";
        String restaurantAddress = "Test restaurantAddress";
        Boolean operating = true;
        Member member = Member.builder()
            .username("Test username")
            .build();

        Restaurant restaurant = Restaurant.builder()
            .name(restaurantName).operating(operating).address(restaurantAddress).build();
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));

        List<Review> reviews = new ArrayList<>();
        reviews.add(Review.builder().restaurant(restaurant).content("내용1").member(member).rating(4).build());
        reviews.add(Review.builder().restaurant(restaurant).content("내용2").member(member).rating(5).build());
        when(reviewRepository.findByRestaurantId(restaurantId)).thenReturn(reviews);

        RestaurantWithReviewDto result = restaurantService.getRestaurantDetail(restaurantId);

        assertNotNull(result);
        assertEquals(restaurantName, result.name());
        assertEquals(restaurantAddress, result.address());
        assertEquals(2, result.reviews().size());
    }
}