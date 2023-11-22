package beforespring.yourfood.app.restaurant.service.dto;

import beforespring.yourfood.app.review.domain.Review;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


public record ReviewDto(Long id,
                        Long memberId,
                        Long restaurantId,
                        String content,
                        int rating,
                        LocalDateTime createdAt,
                        LocalDateTime updatedAt
) {
    @Builder
    public ReviewDto {
    }

    public static List<ReviewDto> mapReviewsToReviewDtos(List<Review> reviews) {
        return reviews.stream()
            .map(review -> ReviewDto.builder()
                .id(review.getId())
                .memberId(review.getMemberId())
                .restaurantId(review.getRestaurantId())
                .content(review.getContent())
                .rating(review.getRating())
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .build())
            .collect(Collectors.toList());
    }
}
