package beforespring.yourfood.app.review.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewQueryRepository extends JpaRepository<Review, Long> {
    @Query("SELECT r FROM Review r WHERE r.restaurant.id = :restaurantId ORDER BY r.rating ASC")
    List<Review> findReviewsByRestaurantIdOrderByRatingAsc(@Param("restaurantId") Long restaurantId);

    @Query("SELECT r FROM Review r WHERE r.restaurant.id = :restaurantId ORDER BY r.rating DESC")
    List<Review> findReviewsByRestaurantIdOrderByRatingDesc(@Param("restaurantId") Long restaurantId);

    @Query("SELECT r FROM Review r WHERE r.restaurant.id = :restaurantId ORDER BY r.createdAt ASC")
    List<Review> findReviewsByRestaurantIdOrderByCreatedAtAsc(@Param("restaurantId") Long restaurantId);

    @Query("SELECT r FROM Review r WHERE r.restaurant.id = :restaurantId ORDER BY r.createdAt DESC")
    List<Review> findReviewsByRestaurantIdOrderByCreatedAtDesc(@Param("restaurantId") Long restaurantId);
}
