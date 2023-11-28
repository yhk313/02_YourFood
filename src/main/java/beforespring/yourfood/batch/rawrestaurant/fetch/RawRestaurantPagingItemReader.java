package beforespring.yourfood.batch.rawrestaurant.fetch;

import beforespring.yourfood.batch.rawrestaurant.model.RawRestaurant;
import beforespring.yourfood.batch.rawrestaurant.model.RawRestaurantId;
import beforespring.yourfood.batch.rawrestaurant.RawRestaurantRepository;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import org.springframework.batch.item.database.AbstractPagingItemReader;

/**
 * RawRestaurantFetcher를 사용하여 페이지 아이템을 읽어오는 클래스. AbstractPagingItemReader를 확장하여 구현함. 여기서 불러오는 레스토랑에
 * 대해서, 영속성 컨텍스트에 캐싱을 해둬야함.
 *
 * @see RawRestaurantFetcher
 * @see AbstractPagingItemReader
 */
public class RawRestaurantPagingItemReader extends
    AbstractPagingItemReader<RawRestaurantReaderResult> {

    private final RawRestaurantFetcher rawRestaurantFetcher;
    private final RawRestaurantRepository rawRestaurantRepository;

    @Override
    protected void doReadPage() {
        if (results == null) {
            results = new CopyOnWriteArrayList<>();
        } else {
            results.clear();
        }

        RawRestaurantFetchResult rawRestaurantFetchResult = rawRestaurantFetcher.find(getPage() + 1,
            getPageSize());  // 1페이지부터 시작

        List<RawRestaurant> rawRestaurants = rawRestaurantFetchResult.rawRestaurants();
        Map<RawRestaurantId, RawRestaurant> existingRawRestaurantsMap = readFromDb(rawRestaurants);

        if (getPage() == 0) {  // first page
            if (logger.isDebugEnabled()) {
                logger.debug("readFirstPage, totalItems=" + rawRestaurantFetchResult.totalItems());
            }
            setMaxItemCount(rawRestaurantFetchResult.totalItems());
        }

        results.addAll(rawRestaurants.stream()
                           .map(fetched ->
                                    new RawRestaurantReaderResult(
                                        fetched,
                                        existingRawRestaurantsMap.get(
                                            fetched.getRawRestaurantId())
                                    ))
                           .toList());
    }

    private Map<RawRestaurantId, RawRestaurant> readFromDb(
        List<RawRestaurant> rawRestaurants) {
        List<RawRestaurant> existing = rawRestaurantRepository.findRawRestaurantByRawRestaurantId(
            rawRestaurants.stream().map(RawRestaurant::getRawRestaurantId).toList());
        return existing.stream()
                   .collect(Collectors.toMap(
                       RawRestaurant::getRawRestaurantId,
                       rr -> rr
                   ));
    }

    @Override
    protected void doJumpToPage(int itemIndex) {

    }

    public RawRestaurantPagingItemReader(RawRestaurantFetcher rawRestaurantFetcher,
        RawRestaurantRepository rawRestaurantRepository, int pageSize) {
        this.rawRestaurantFetcher = rawRestaurantFetcher;
        this.rawRestaurantRepository = rawRestaurantRepository;
        setPageSize(pageSize);
    }
}
