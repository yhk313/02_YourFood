package beforespring.yourfood.app.restaurant.infra;

import beforespring.yourfood.app.restaurant.domain.Restaurant;
import beforespring.yourfood.app.utils.Coordinates;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class RestaurantQueryRepositoryImplTest {

    @Autowired
    RestaurantQueryRepositoryImpl restaurantQueryRepository;

    // todo 테스트 코드 개선 (테스트용 고정 데이터 추가)
    @Test
    @Transactional
    void findAllWithin() {
        BigDecimal givenLat = new BigDecimal("37.67842088599542");
        BigDecimal givenLon = new BigDecimal("126.76608008832636");
        Coordinates givenCoords = new Coordinates(givenLat, givenLon);
        List<Restaurant> allWithin = restaurantQueryRepository.findAllWithin(
            givenCoords,
            2000
        );

        List<BigInteger> collect = allWithin.stream()
                                       .map(restaurant -> Coordinates.calculateDistance(
                                           restaurant.getCoordinates(),
                                           givenCoords))
                                       .toList();
    }
}