package beforespring.yourfood.batch.rawrestaurant;

import beforespring.yourfood.batch.rawrestaurant.model.RawRestaurant;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;
import net.bytebuddy.utility.RandomString;


/**
 * RestaurantFetcher 더미 구현체. 생성자로 넘겨받은 총 생성 수량만큼 RawRestaurant를 생성함.
 */
public class DummyRawRestaurantFetcher implements RawRestaurantFetcher {

    private final List<RawRestaurant> contents;


    public DummyRawRestaurantFetcher(Long totalItems) {
        this.contents = Stream.generate(() -> aRaw().build())
                            .limit(totalItems)
                            .toList();
    }

    private final Random random = new Random();

    private RawRestaurant.RawRestaurantBuilder aRaw() {
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


    @Override
    public RawRestaurantFetchResult find(int page, int pageSize) {
        int from = (page - 1) * pageSize;
        int to = Math.min(contents.size(), page * pageSize);
        List<RawRestaurant> rawRestaurants = contents.subList(from, to);
        return new RawRestaurantFetchResult(
            page,
            pageSize,
            contents.size(),
            rawRestaurants
        );
    }
}
