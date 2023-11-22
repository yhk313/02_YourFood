package beforespring.yourfood.app.restaurant.domain;

import beforespring.yourfood.app.review.domain.Review;
import beforespring.yourfood.app.utils.Coordinates;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class RestaurantTest {
    Restaurant restaurant;

    @BeforeEach
    void setUp() {
        restaurant = Restaurant.builder()
                         .name("맛나식당")
                         .addressCode(new AddressCode("sido", "sigungu"))
                         .address("서울시 종로구")
                         .description("찐 맛집")
                         .coordinates(new Coordinates(new BigDecimal(37), new BigDecimal(127)))
                         .operating(true)
                         .build();
    }


    @Test
    @DisplayName("레스토랑에 적용된 리뷰가 100개 이하면 새로운 리뷰가 반영되어야 함")
    void new_review_has_to_be_reflected() {
        //given
        Review mockReview = mock(Review.class);

        //when
        when(mockReview.getRating()).thenReturn(5);
        restaurant.updateNewReviewRating(BigDecimal.valueOf(mockReview.getRating()).setScale(5, RoundingMode.HALF_UP) );
        BigDecimal resultRating = restaurant.getRating();

        //then
        assertThat(resultRating)
            .describedAs("레스토랑의 평점이 5.00000 이어야 함")
            .isEqualTo(BigDecimal.valueOf(5.0).setScale(5, RoundingMode.HALF_UP));
        assertThat(restaurant.getUpdatedRatingNum())
            .describedAs("반영된 리뷰가 1개여야 합니다.")
            .isEqualTo(1);
    }

    @Test
    @DisplayName("레스토랑에 적용된 리뷰가 100개 초과면 새로운 리뷰가 즉시 반영되지 않아야 함")
    void new_review_has_not_to_be_reflected() {
        //given
        List<Review> mockReviews = initNewReviews(101);
        Integer sumRating = mockReviews.stream()
                                .map(Review::getRating)
                                .reduce(0, Integer::sum) - mockReviews.get(100).getRating();
        BigDecimal expectedAgvRating = BigDecimal.valueOf(sumRating).setScale(5)
                                           .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP)
                                           .setScale(5, RoundingMode.HALF_UP);

        //when
        for (Review review : mockReviews) {
            restaurant.updateNewReviewRating(BigDecimal.valueOf(review.getRating()).setScale(5, RoundingMode.HALF_UP));
        }
        BigDecimal resultRating = restaurant.getRating();

        //then
        assertThat(restaurant.getUpdatedRatingNum())
            .describedAs("즉시 반영되는 리뷰는 100개 이하여야 합니다.")
            .isEqualTo(100);

        assertThat(resultRating.setScale(2, RoundingMode.HALF_UP))
            .isEqualTo(expectedAgvRating.setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    @DisplayName("레스토랑에 적용된 리뷰가 100개 이하면 수정된 리뷰가 반영되어야 함")
    void modified_review_has_to_be_reflected() {
        //given
        List<Review> mockReviews = initNewReviews(6);
        for (Review mockReview : mockReviews) {
            restaurant.updateNewReviewRating(BigDecimal.valueOf(mockReview.getRating()).setScale(5, RoundingMode.HALF_UP));
        }
        BigDecimal beforeRating = restaurant.getRating();
        Integer beforeRatingNum = restaurant.getUpdatedRatingNum();

        Review modifiedMockReview = mock(Review.class);

        //when
        when(modifiedMockReview.getRating()).thenReturn(5);
        when(modifiedMockReview.getBeforeRating()).thenReturn(0);
        restaurant.updateModifiedReviewRating(BigDecimal.valueOf(modifiedMockReview.getBeforeRating()), BigDecimal.valueOf(modifiedMockReview.getRating()));
        BigDecimal afterRating = restaurant.getRating();
        Integer afterRatingNum = restaurant.getUpdatedRatingNum();

        //then
        assertThat(afterRatingNum)
            .describedAs("수정된 리뷰 반영시 리뷰 개수는 이전과 같아야 합니다.")
            .isEqualTo(beforeRatingNum);
        assertThat(afterRating)
            .describedAs("반영된 리뷰가 100개를 이하면 수정된 리뷰가 즉시 반영되어야 합니다.")
            .isNotEqualTo(beforeRating)
            .describedAs("예상되는 평균 평점은 3.33333 입니다.")
            .isEqualTo(BigDecimal.valueOf(20).setScale(5).divide(BigDecimal.valueOf(6), RoundingMode.HALF_UP));
    }

    @Test
    @DisplayName("레스토랑에 적용된 리뷰가 100개 초과면 수정된 리뷰가 즉시 반영되지 않아야 함")
    void modified_review_has_not_to_be_reflected() {
        //given
        List<Review> mockReviews = initNewReviews(100);
        for (Review mockReview : mockReviews) {
            restaurant.updateNewReviewRating(BigDecimal.valueOf(mockReview.getRating()));
        }
        BigDecimal beforeRating = restaurant.getRating();
        List<Review> modifiedMockReviews = initModifiedReviews(5);

        //when
        for (Review modifiedMockReview : modifiedMockReviews) {
            restaurant.updateModifiedReviewRating(BigDecimal.valueOf(modifiedMockReview.getBeforeRating()), BigDecimal.valueOf(modifiedMockReview.getRating()));
        }
        BigDecimal afterRating = restaurant.getRating();

        //then
        assertThat(afterRating)
            .describedAs("반영된 리뷰가 100개를 초과하면 수정된 리뷰가 즉시 반영되지 않아야 합니다.")
            .isEqualTo(beforeRating);
    }

    List<Review> initNewReviews(Integer instanceNum) {
        return IntStream.rangeClosed(0, instanceNum - 1)
                   .mapToObj(i -> {
                       Review review = mock(Review.class);
                       lenient().when(review.getRating()).thenReturn(i % 6);
                       return review;
                   })
                   .toList();
    }

    List<Review> initModifiedReviews(Integer instanceNum) {
        return IntStream.rangeClosed(0, instanceNum - 1)
                   .mapToObj(i -> {
                       Review review = mock(Review.class);
                       lenient().when(review.getRating()).thenReturn(i % 6);
                       lenient().when(review.getBeforeRating()).thenReturn(i + 2 % 6);
                       return review;
                   })
                   .toList();
    }
}
