package beforespring.yourfood.app.restaurant.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    @Query("SELECT r, rv FROM Restaurant r " +
        "LEFT JOIN Review rv ON r.id = rv.restaurant.id " +
        "WHERE r.id = :restaurantId")
    List<Object[]> findRestaurantWithReviews(@Param("restaurantId") Long restaurantId);

}
