package beforespring.yourfood.batch.rawrestaurant.update;

import static beforespring.yourfood.app.restaurant.domain.QRestaurant.restaurant;
import static beforespring.yourfood.batch.rawrestaurant.model.QRawRestaurant.rawRestaurant;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

public class RestaurantUpdateReaderRepositoryQueryDslImpl implements RestaurantUpdateReaderRepository {

    private final JPAQueryFactory queryFactory;

    public RestaurantUpdateReaderRepositoryQueryDslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public long countTotalAfter(LocalDateTime from) {
        //noinspection DataFlowIssue
        return queryFactory
                   .select(rawRestaurant.count())
                   .from(rawRestaurant)
                   .where(rawRestaurant.crucialInfoFetchedAt.after(from))
                   .fetchOne();
    }

    @Override
    public Page<RestaurantUpdateReaderResult> findUpdateReaderResultPage(
        Pageable pageable,
        LocalDateTime from
    ) {
        List<RestaurantUpdateReaderResult> results = findUpdateReaderResultList(pageable, from);
        return PageableExecutionUtils.getPage(results, pageable, () -> countTotalAfter(from));
    }

    @Override
    public List<RestaurantUpdateReaderResult> findUpdateReaderResultList(Pageable pageable, LocalDateTime from) {
        return queryFactory
                   .select(new QRestaurantUpdateReaderResult(rawRestaurant, restaurant))
                   .from(rawRestaurant)
                   .leftJoin(restaurant).on(nameAndAddressMatches())
                   .where(crucialInfoFetchedAfter(from))
                   .limit(pageable.getPageSize())
                   .offset(pageable.getOffset())
                   .fetch();
    }

    private static BooleanExpression crucialInfoFetchedAfter(LocalDateTime from) {
        return rawRestaurant.crucialInfoFetchedAt.after(from);
    }

    private static BooleanExpression nameAndAddressMatches() {
        return restaurant.name.eq(rawRestaurant.rawRestaurantId.BIZPLC_NM)
                   .and(restaurant.address.eq(rawRestaurant.rawRestaurantId.REFINE_ROADNM_ADDR));
    }
}
