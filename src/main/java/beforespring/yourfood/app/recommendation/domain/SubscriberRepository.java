package beforespring.yourfood.app.recommendation.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 알림 수신 동의 일자가 제공된 일자보다 전인 Subscriber 목록을 가져옴.
 */
public interface SubscriberRepository {
    List<Subscriber> findSubscriberList(LocalDateTime consentDateBefore, Pageable pageable);
    Page<Subscriber> findSubscribersPage(LocalDateTime consentDateBefore, Pageable pageable);
}
