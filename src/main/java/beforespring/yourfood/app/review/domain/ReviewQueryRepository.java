package beforespring.yourfood.app.review.domain;

import beforespring.yourfood.app.utils.OrderBy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewQueryRepository{
    Page<Review> findReviewsByRestaurantIdOrderBy(boolean desc, OrderBy orderBy, Long restaurantId, Pageable pageable);
}
