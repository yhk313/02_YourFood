package beforespring.yourfood.batch.rawrestaurant;

import static org.assertj.core.api.Assertions.assertThat;

import beforespring.yourfood.batch.rawrestaurant.model.RawRestaurant;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class RawRestaurantRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    RawRestaurantRepository rawRestaurantRepository;

    DummyRawRestaurantFetcher dummyRawRestaurantFetcher = new DummyRawRestaurantFetcher(1L);

    RawRestaurant given;

    @BeforeEach
    void init() {
        RawRestaurantFetchResult rawRestaurantFetchResult = dummyRawRestaurantFetcher.find(1, 10);
        given = rawRestaurantFetchResult.rawRestaurants().get(0);
        em.persist(given);
        em.flush();
        em.clear();
    }

    @Test
    void findRawRestaurantByRawRestaurantId() {
        // when
        List<RawRestaurant> res = rawRestaurantRepository.findRawRestaurantByRawRestaurantId(
            List.of(given.getRawRestaurantId()));

        // then
        assertThat(res)
            .isNotEmpty()
            .hasSize(1);

        RawRestaurant actual = res.get(0);
        assertThat(actual.isOutdatedCompareTo(given))
            .isFalse();
    }
}