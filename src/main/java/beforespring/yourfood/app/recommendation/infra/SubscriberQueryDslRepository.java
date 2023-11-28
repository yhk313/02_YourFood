package beforespring.yourfood.app.recommendation.infra;

import beforespring.yourfood.app.recommendation.domain.QSubscriber;
import beforespring.yourfood.app.recommendation.domain.Subscriber;
import beforespring.yourfood.app.recommendation.domain.SubscriberRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static beforespring.yourfood.app.member.domain.QMember.member;

/**
 * 패키지 위치와 결합도 면에서 대해 리팩터링 여지 있음.
 */
@Repository
public class SubscriberQueryDslRepository implements SubscriberRepository {
    private final JPAQueryFactory queryFactory;
    public SubscriberQueryDslRepository(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    private long getTotalSubscriberCount(LocalDateTime consentDateBefore) {
        //noinspection DataFlowIssue
        return queryFactory
                .select(member.count())
                .from(member)
                .where(member.notiAgreedAt.before(consentDateBefore))
                .fetchOne();
    }

    @Override
    public List<Subscriber> findSubscriberList(LocalDateTime consentDateBefore, Pageable pageable) {
        return queryFactory
                .select(new QSubscriber(member.id, member.username, member.lunchNotiStatus, member.coordinates))
                .from(member)
                .where(member.notiAgreedAt.before(consentDateBefore))
                .orderBy(member.notiAgreedAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public Page<Subscriber> findSubscribersPage(LocalDateTime consentDateBefore, Pageable pageable) {
        List<Subscriber> result = findSubscriberList(consentDateBefore, pageable);
        return PageableExecutionUtils.getPage(result, pageable, () -> this.getTotalSubscriberCount(consentDateBefore));
    }
}
