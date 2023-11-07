package beforespring.yourfood.batch.rawrestaurant.update;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RestaurantUpdateReaderRepository {

    Page<RestaurantUpdateReaderResult> findUpdateReaderResultPage(
        Pageable pageable,
        LocalDateTime from
    );

    List<RestaurantUpdateReaderResult> findUpdateReaderResultList(
        Pageable pageable,
        LocalDateTime from
    );
}
