package beforespring.yourfood.app.review.infra;

import beforespring.yourfood.app.review.domain.Review;
import beforespring.yourfood.app.review.domain.ReviewQueryRepository;
import beforespring.yourfood.app.utils.OrderBy;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static beforespring.yourfood.app.review.domain.QReview.review;

@Repository
public class ReviewQueryRepositoryImpl implements ReviewQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;


    public ReviewQueryRepositoryImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Review> findReviewsByRestaurantIdOrderBy(boolean desc, OrderBy orderBy, Long restaurantId, Pageable pageable) {
        List<Review> reviews = jpaQueryFactory.selectFrom(review)
            .where(review.restaurantId.eq(restaurantId))
            .orderBy(getOrderSpecifier(orderBy, desc))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
        return PageableExecutionUtils.getPage(reviews, pageable, () -> countReviews(restaurantId));
    }

    private OrderSpecifier<?> getOrderSpecifier(OrderBy orderBy, boolean desc) {
        ComparableExpressionBase<?> condition = switch (orderBy) {
            case RATING -> review.rating;
            case TIME -> review.createdAt;
            default -> review.rating;
        };
        return desc ? condition.desc() : condition.asc();
    }

    public long countReviews(Long restaurantId) {
        //noinspection ConstantConditions
        return jpaQueryFactory
            .select(review.count())
            .from(review)
            .where(review.restaurantId.eq(restaurantId))
            .fetchOne();
    }
}
