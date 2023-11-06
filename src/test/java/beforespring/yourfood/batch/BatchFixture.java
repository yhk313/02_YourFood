package beforespring.yourfood.batch;

import static beforespring.Fixture.random;

import beforespring.yourfood.batch.rawrestaurant.model.RawRestaurant;
import java.time.LocalDate;
import net.bytebuddy.utility.RandomString;

public class BatchFixture {

    static public RawRestaurant.RawRestaurantBuilder aRawRestaurant() {
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

}
