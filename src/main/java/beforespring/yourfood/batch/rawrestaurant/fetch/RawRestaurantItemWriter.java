package beforespring.yourfood.batch.rawrestaurant.fetch;

import beforespring.yourfood.batch.rawrestaurant.RawRestaurantRepository;
import beforespring.yourfood.batch.rawrestaurant.model.RawRestaurant;
import java.util.List;
import org.springframework.batch.item.ItemWriter;


/**
 * Reader로부터 값을 받아와 업데이트 대상을 필터링하고, RawRestaurant의 값을 업데이트함.
 * JPA로 구현되어 대량 쿼리시 성능이 좋지 못함. 그러나, 하루에 한 번 수행하는 것을 전제로 만들어진 배치 작업이기 때문에 큰 문제는 아닐 것으로 보임.
 *
 */
public class RawRestaurantItemWriter implements ItemWriter<RawRestaurant> {
    private final RawRestaurantRepository rawRestaurantRepository;

    public RawRestaurantItemWriter(
        RawRestaurantRepository rawRestaurantRepository) {
        this.rawRestaurantRepository = rawRestaurantRepository;
    }

    @Override
    public void write(List<? extends RawRestaurant> items) throws Exception {
        // 새로 생성되어 id가 null인 객체에 대해서만 saveAll
        List<RawRestaurant> newRestaurants = (List<RawRestaurant>) items;
        rawRestaurantRepository.saveAll(
            newRestaurants.stream()
                .filter(rr -> rr.getId() == null)
                .toList()
        );
    }
}
