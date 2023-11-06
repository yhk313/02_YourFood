package beforespring.yourfood.app.restaurant.domain;
import org.springframework.data.jpa.repository.JpaRepository;
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
