package beforespring.yourfood.batch.rawrestaurant;

import beforespring.yourfood.batch.rawrestaurant.model.RawRestaurant;
import beforespring.yourfood.batch.rawrestaurant.model.RawRestaurantId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RawRestaurantRepository extends JpaRepository<RawRestaurant, Long> {

//    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    @Query("select r from RawRestaurant r where r.rawRestaurantId in :idList")
    List<RawRestaurant> findRawRestaurantByRawRestaurantId(@Param("idList") List<RawRestaurantId> idList);
}
