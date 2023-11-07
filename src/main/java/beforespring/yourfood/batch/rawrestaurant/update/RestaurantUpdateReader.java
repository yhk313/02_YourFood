package beforespring.yourfood.batch.rawrestaurant.update;


import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * RawRestaurant와 Restaurant 쌍을 읽어들이는 ItemReader. 마지막 Fetch 작업의 시작 시점을 기준으로, 이후에 update된 RawRestaurant에 해당하는 Restaurant만 불러옴. Fetch 작업과 동시에 실행되어서는 안 됨.
 */
public class RestaurantUpdateReader extends AbstractPagingItemReader<RestaurantUpdateReaderResult> {
    private final LocalDateTime lastFetchJobStartedAt;
    private final RestaurantUpdateReaderRepository restaurantUpdateReaderRepository;

    public RestaurantUpdateReader(
        LocalDateTime lastFetchJobStartedAt,
        RestaurantUpdateReaderRepository restaurantUpdateReaderRepository) {
        this.lastFetchJobStartedAt = lastFetchJobStartedAt;
        this.restaurantUpdateReaderRepository = restaurantUpdateReaderRepository;
    }

    @Override
    protected void doReadPage() {
        if (results == null) {
            results = new CopyOnWriteArrayList<>();
        } else {
            results.clear();
        }

        List<RestaurantUpdateReaderResult> result = getPage() == 0 ? firstQuery() : query();
        results.addAll(result);
    }

    /**
     * 첫 요청시에만 전체 item 개수가 포함된 정보를 받아와서 setMaxItemCount를 호출.
     * @return result
     */
    private List<RestaurantUpdateReaderResult> firstQuery() {
        Page<RestaurantUpdateReaderResult> result = restaurantUpdateReaderRepository.findUpdateReaderResultPage(
            Pageable.ofSize(getPageSize()).withPage(getPage()), lastFetchJobStartedAt);
        int maxItemCount = Long.valueOf(result.getTotalElements()).intValue();
        if (maxItemCount > 0) {
            setMaxItemCount(maxItemCount);
        }
        return result.getContent();
    }

    /**
     * 첫 요청이 아닌 경우, 불필요한 쿼리를 줄이기 위해 List로 요청함.
     */
    private List<RestaurantUpdateReaderResult> query() {
        return restaurantUpdateReaderRepository.findUpdateReaderResultList(
            Pageable.ofSize(getPageSize()).withPage(getPage()), lastFetchJobStartedAt);
    }

    @Override
    protected void doJumpToPage(int itemIndex) {

    }
}
