package beforespring.yourfood.app.restaurant.infra;

import static beforespring.yourfood.app.restaurant.domain.QRestaurant.restaurant;

import beforespring.yourfood.app.restaurant.domain.Restaurant;
import beforespring.yourfood.app.restaurant.domain.RestaurantQueryRepository;
import beforespring.yourfood.app.utils.Coordinates;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.math.BigDecimal;
import java.util.List;
import java.util.function.Predicate;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Repository;


@Repository
public class RestaurantQueryRepositoryImpl implements RestaurantQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public RestaurantQueryRepositoryImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Restaurant> findAllWithin(Coordinates currentCoords, int rangeInMeters) {
        QueryRange param = QueryRange.from(currentCoords, rangeInMeters);
        return jpaQueryFactory
                   .selectFrom(restaurant)
                   .where(
                       latBetween(param.latFrom(), param.latTo()),
                       lonBetween(param.lonFrom(), param.lonTo())
                   )
                   .stream()
                   .filter(distanceToCurrentCoordsWithin(currentCoords, rangeInMeters))
                   .toList()
            ;
    }

    private static Predicate<Restaurant> distanceToCurrentCoordsWithin(Coordinates currentCoords,
        int rangeInMeter) {
        return rest ->
                   Coordinates.calculateDistance(currentCoords, rest.getCoordinates())
                       .intValue() <= rangeInMeter;
    }

    private static BooleanExpression latBetween(BigDecimal latFrom, BigDecimal latTo) {
        return restaurant.coordinates.lat.goe(latFrom)
                   .and(restaurant.coordinates.lat.loe(latTo));
    }

    private static BooleanExpression lonBetween(BigDecimal lonFrom, BigDecimal lonTo) {
        return restaurant.coordinates.lon.goe(lonFrom)
                   .and(restaurant.coordinates.lon.loe(lonTo));
    }
}
