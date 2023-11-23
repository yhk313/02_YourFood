package beforespring.yourfood.app.review.service;

import beforespring.yourfood.app.exception.ReviewNotFoundException;
import beforespring.yourfood.app.restaurant.service.dto.ReviewDto;
import beforespring.yourfood.app.review.domain.Review;
import beforespring.yourfood.app.review.domain.ReviewRepository;
import beforespring.yourfood.app.review.exception.MemberMismatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ApplicationEventPublisher publisher;

    @Override
    public void saveReview(Long restaurantId, Long memberId, String content, Integer rating) {
        Review review = Review.builder()
                            .memberId(memberId)
                            .restaurantId(restaurantId)
                            .content(content)
                            .rating(rating)
                            .build();
        reviewRepository.save(review.posted(publisher));
    }

    @Override
    public void updateReview(Long reviewId, Long memberId, String content, Integer rating) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(ReviewNotFoundException::new);
        review.updateReview(content, rating, publisher);
    }

    @Override
    public Page<ReviewDto> getReviewsByRestaurantId(Long restaurantId, Pageable pageable) {
        Page<Review> reviewsPages = reviewRepository.findReviewsByRestaurantIdPaged(restaurantId, pageable);
        return reviewsPages.map(
            review -> new ReviewDto(
                review.getId(),
                review.getMemberId(),
                review.getRestaurantId(),
                review.getContent(),
                review.getRating(),
                review.getCreatedAt(),
                review.getUpdatedAt()
            )
        );
    }
}
