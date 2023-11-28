package beforespring.yourfood.app.recommendation.infra.springbatch;

import beforespring.yourfood.app.recommendation.domain.Subscriber;
import beforespring.yourfood.app.recommendation.domain.SubscriberRepository;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * 알림을 수신할 구독자 목록을 읽어오는 ItemReader 구현체
 */
public class SubscriberReader extends AbstractPagingItemReader<Subscriber> {
    private final SubscriberRepository subscriberRepository;
    private final LocalDateTime triggeredDate;

    public SubscriberReader(SubscriberRepository subscriberRepository, LocalDateTime triggeredDate, int pageSize) {
        this.subscriberRepository = subscriberRepository;
        this.triggeredDate = triggeredDate;
        this.setPageSize(pageSize);
    }

    @Override
    protected void doReadPage() {
        if (results == null) {
            results = new CopyOnWriteArrayList<>();
        } else {
            results.clear();
        }
        List<Subscriber> result = getPage() == 0 ? firstQuery() : query();
        results.addAll(result);
    }

    List<Subscriber> firstQuery() {
        Page<Subscriber> subscribersPage = subscriberRepository.findSubscribersPage(triggeredDate, getPageable());
        this.setMaxItemCount(Long.valueOf(subscribersPage.getTotalElements()).intValue());
        return subscribersPage.getContent();
    }

    List<Subscriber> query() {
        return subscriberRepository.findSubscriberList(triggeredDate, getPageable());
    }

    private Pageable getPageable() {
        return Pageable.ofSize(getPageSize()).withPage(getPage());
    }

    @Override
    protected void doJumpToPage(int itemIndex) {

    }
}
