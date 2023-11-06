package beforespring.yourfood.batch.rawrestaurant;

import beforespring.yourfood.batch.rawrestaurant.model.RawRestaurant;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.batch.item.database.AbstractPagingItemReader;

/**
 * RawRestaurantFetcher를 사용하여 페이지 아이템을 읽어오는 클래스. AbstractPagingItemReader를 확장하여 구현함.
 * @see RawRestaurantFetcher
 * @see AbstractPagingItemReader
 */
public class RawRestaurantPagingItemReader extends AbstractPagingItemReader<RawRestaurant> {

    private final RawRestaurantFetcher rawRestaurantFetcher;

    @Override
    protected void doReadPage() {
        if (results == null) {
            results = new CopyOnWriteArrayList<>();
        }
        else {
            results.clear();
        }

        RawRestaurantFetchResult rawRestaurantFetchResult = rawRestaurantFetcher.find(getPage() + 1, getPageSize());  // 1페이지부터 시작

        if (getPage() == 0) {  // first page
            if (logger.isDebugEnabled()) {
                logger.debug("readFirstPage, totalItems=" + rawRestaurantFetchResult.totalItems());
            }
            setMaxItemCount(rawRestaurantFetchResult.totalItems());
        }

        results.addAll(rawRestaurantFetchResult.rawRestaurants());
    }

    @Override
    protected void doJumpToPage(int itemIndex) {

    }

    public RawRestaurantPagingItemReader(RawRestaurantFetcher rawRestaurantFetcher, int pageSize) {
        this.rawRestaurantFetcher = rawRestaurantFetcher;
        setPageSize(pageSize);
    }
}
