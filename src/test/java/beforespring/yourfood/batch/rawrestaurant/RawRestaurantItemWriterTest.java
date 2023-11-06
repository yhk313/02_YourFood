package beforespring.yourfood.batch.rawrestaurant;

import static beforespring.Fixture.random;
import static org.assertj.core.api.Assertions.*;

import beforespring.yourfood.batch.rawrestaurant.model.RawRestaurant;
import beforespring.yourfood.batch.rawrestaurant.fetch.RawRestaurantItemWriter;
import beforespring.yourfood.batch.rawrestaurant.model.RawRestaurantId;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.EntityManager;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class RawRestaurantItemWriterTest {

    RawRestaurant.RawRestaurantBuilder aRaw() {
        double lat = random.nextDouble(124, 132);
        double logt = random.nextDouble(33, 43);
        boolean isClosed = random.nextBoolean();
        LocalDate openedAt = LocalDate.now().minusDays(random.nextLong(0, 2000));
        LocalDate closedAt = isClosed ? openedAt.minusDays(random.nextLong(0, 2000)) : null;

        return RawRestaurant.builder()
                   .SIGUN_NM(RandomString.make())
                   .SIGUN_CD(RandomString.make())
                   .BIZPLC_NM(RandomString.make())
                   .LICENSG_DE(openedAt.toString())
                   .BSN_STATE_NM(isClosed ? "폐업": "영업")
                   .CLSBIZ_DE(isClosed ? closedAt.toString() : null)
                   .LOCPLC_AR(RandomString.make())
                   .GRAD_FACLT_DIV_NM(RandomString.make())
                   .MALE_ENFLPSN_CNT(RandomString.make())
                   .YY(String.valueOf(openedAt.getYear()))
                   .MULTI_USE_BIZESTBL_YN(RandomString.make())
                   .TOT_FACLT_SCALE(RandomString.make())
                   .FEMALE_ENFLPSN_CNT(RandomString.make())
                   .BSNSITE_CIRCUMFR_DIV_NM(RandomString.make())
                   .SANITTN_INDUTYPE_NM(RandomString.make())
                   .SANITTN_BIZCOND_NM(RandomString.make())
                   .TOT_EMPLY_CNT(RandomString.make())
                   .REFINE_ROADNM_ADDR(RandomString.make())
                   .REFINE_LOTNO_ADDR(RandomString.make())
                   .REFINE_ZIP_CD(RandomString.make())
                   .REFINE_WGS84_LAT(Double.toString(lat))
                   .REFINE_WGS84_LOGT(Double.toString(logt));
    }


    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    EntityManager em;

    @Autowired
    RawRestaurantRepository rawRestaurantRepository;

    RawRestaurantItemWriter rawRestaurantItemWriter;

    @BeforeEach
    void init() {
        rawRestaurantItemWriter = new RawRestaurantItemWriter(rawRestaurantRepository);
    }


//    @Commit
//    @RepeatedTest(10)
    @Test
    @Transactional
    void write_new_data_test() throws Exception {
        // given
        int howMany = 20;
        List<RawRestaurant> fetched = Stream.generate(() -> aRaw().build())
                                       .limit(howMany)
                                       .toList();

        // when
        rawRestaurantItemWriter.write(fetched);
        em.flush();
        em.clear();

        // then
        List<RawRestaurant> found = rawRestaurantRepository.findRawRestaurantByRawRestaurantId(
            fetched.stream().map(RawRestaurant::getRawRestaurantId).toList());
        Set<RawRestaurantId> givenRawRestaurantIds = fetched.stream()
                                                         .map(RawRestaurant::getRawRestaurantId)
                                                         .collect(Collectors.toSet());


        boolean foundDistinctAll = givenRawRestaurantIds.containsAll(
            found.stream().map(RawRestaurant::getRawRestaurantId).toList());

        assertThat(found)
            .hasSize(500);
        assertThat(foundDistinctAll)
            .isTrue();
    }

}