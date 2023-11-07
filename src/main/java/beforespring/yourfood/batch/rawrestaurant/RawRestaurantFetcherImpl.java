package beforespring.yourfood.batch.rawrestaurant;

import beforespring.yourfood.batch.rawrestaurant.mapping.Genrestrt;
import beforespring.yourfood.batch.rawrestaurant.mapping.OpenApiManager;
import beforespring.yourfood.batch.rawrestaurant.mapping.Row;
import beforespring.yourfood.batch.rawrestaurant.model.RawRestaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RawRestaurantFetcherImpl implements RawRestaurantFetcher {
    private final OpenApiManager manager;

    @Override
    public RawRestaurantFetchResult find(int page, int pageSize) {
        Genrestrt genrestrt = manager.fetch(page, pageSize);

        List<RawRestaurant> rawRestaurants = new ArrayList<>();

        for (Row row : genrestrt.getRow()) {
            RawRestaurant rawRestaurant = RawRestaurant.builder()
                                              .SIGUN_NM(row.getSigunNm())
                                              .SIGUN_CD(row.getSigunCd())
                                              .BIZPLC_NM(row.getBizplcNm())
                                              .LICENSG_DE(row.getLicensgDe())
                                              .BSN_STATE_NM(row.getBsnStateNm())
                                              .CLSBIZ_DE(row.getClsbizDe())
                                              .LOCPLC_AR(row.getLocplcAr())
                                              .GRAD_FACLT_DIV_NM(row.getGradFacltDivNm())
                                              .MALE_ENFLPSN_CNT(row.getMaleEnflpsnCnt())
                                              .YY(row.getYy())
                                              .MULTI_USE_BIZESTBL_YN(row.getMultiUseBizestblYn())
                                              .TOT_FACLT_SCALE(row.getTotFacltScale())
                                              .FEMALE_ENFLPSN_CNT(row.getFemaleEnflpsnCnt())
                                              .BSNSITE_CIRCUMFR_DIV_NM(row.getBsnsiteCircumfrDivNm())
                                              .SANITTN_INDUTYPE_NM(row.getSanittnIndutypeNm())
                                              .SANITTN_BIZCOND_NM(row.getSanittnBizcondNm())
                                              .TOT_EMPLY_CNT(row.getTotEmplyCnt())
                                              .REFINE_LOTNO_ADDR(row.getRefineLotnoAddr())
                                              .REFINE_ROADNM_ADDR(row.getRefineRoadnmAddr())
                                              .REFINE_ZIP_CD(row.getRefineZipCd())
                                              .REFINE_WGS84_LAT(row.getRefineWgs84Lat())
                                              .REFINE_WGS84_LOGT(row.getRefineWgs84Logt())
                                              .build();

            rawRestaurants.add(rawRestaurant);
        }

        return new RawRestaurantFetchResult(page, pageSize, genrestrt.getHead().getListTotalCount(), rawRestaurants);
    }
}
