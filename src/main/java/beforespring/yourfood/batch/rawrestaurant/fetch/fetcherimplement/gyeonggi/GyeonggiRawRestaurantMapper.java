package beforespring.yourfood.batch.rawrestaurant.fetch.fetcherimplement.gyeonggi;

import beforespring.yourfood.app.restaurant.domain.CuisineType;
import beforespring.yourfood.batch.rawrestaurant.fetch.RawRestaurantFetchResult;
import beforespring.yourfood.batch.rawrestaurant.fetch.fetcherimplement.gyeonggi.xmlmapper.Genrestrt;
import beforespring.yourfood.batch.rawrestaurant.model.RawRestaurant;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class GyeonggiRawRestaurantMapper {
    private final XmlMapper xmlMapper = new XmlMapper();  // todo 싱글톤
    public RawRestaurantFetchResult mapFrom(Genrestrt genrestrt, CuisineType cuisineType, int page, int size) {
        List<RawRestaurant> rawRestaurants = genrestrt.getRow().stream()
                .map(row -> RawRestaurant.builder()
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
                        .sido("경기도")
                        .cuisineType(cuisineType)
                        .build())
                .toList();
        return new RawRestaurantFetchResult(page, size, genrestrt.getHead().getListTotalCount(), rawRestaurants);
    }

    @SneakyThrows
    public RawRestaurantFetchResult mapFrom(
            ResponseEntity<String> response,
            CuisineType cuisineType,
            int page,
            int size
    ) {
        Genrestrt genrestrt = xmlMapper.readValue(response.getBody(), Genrestrt.class);
        return mapFrom(genrestrt, cuisineType, page, size);
    }
}
