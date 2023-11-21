package beforespring.yourfood.app.review.service;

import beforespring.yourfood.app.exception.RestaurantNotFoundException;
import beforespring.yourfood.app.exception.ReviewNotFoundException;
import beforespring.yourfood.app.member.domain.Member;
import beforespring.yourfood.app.member.domain.MemberRepository;
import beforespring.yourfood.app.restaurant.domain.Restaurant;
import beforespring.yourfood.app.restaurant.domain.RestaurantRepository;
import beforespring.yourfood.app.restaurant.service.dto.ReviewDto;
import beforespring.yourfood.app.review.domain.Review;
import beforespring.yourfood.app.review.domain.ReviewRepository;
import beforespring.yourfood.app.review.exception.MemberMismatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final RestaurantRepository restaurantRepository;

    @Override
    public void saveReview(Long restaurantId, Long memberId, String content, Integer rating) {
        Member member = memberRepository.getReferenceById(memberId);
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(RestaurantNotFoundException::new);
        Review review = Review.builder()
                            .member(member)
                            .restaurant(restaurant)
                            .content(content)
                            .rating(rating)
                            .build();
        restaurant.updateNewReviewRating(review);
        reviewRepository.save(review);
    }

    @Override
    public void updateReview(Long reviewId, Long memberId, String content, Integer rating) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(ReviewNotFoundException::new);
        if (!memberId.equals(review.getMember().getId())) {
            throw new MemberMismatchException();
        }
        review.updateReview(content, rating);
        Restaurant restaurant = review.getRestaurant();
        restaurant.updateModifiedReviewRating(review);
    }

    @Override
    public Page<ReviewDto> getReviewsByRestaurantId(Long restaurantId, Pageable pageable) {
        Page<Review> reviewsPages = reviewRepository.findReviewsByRestaurantIdPaged(restaurantId, pageable);
        return reviewsPages.map(
            review -> new ReviewDto(
                review.getId(),
                review.getMember().getId(),
                review.getRestaurant().getId(),
                review.getContent(),
                review.getRating(),
                review.getCreatedAt(),
                review.getUpdatedAt()
            )
        );
    }
}
