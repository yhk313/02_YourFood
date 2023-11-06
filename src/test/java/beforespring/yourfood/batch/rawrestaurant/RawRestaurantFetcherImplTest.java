package beforespring.yourfood.batch.rawrestaurant;

import beforespring.yourfood.batch.rawrestaurant.mapping.Genrestrt;
import beforespring.yourfood.batch.rawrestaurant.mapping.Head;
import beforespring.yourfood.batch.rawrestaurant.mapping.OpenApiManager;
import beforespring.yourfood.batch.rawrestaurant.mapping.Row;
import beforespring.yourfood.batch.rawrestaurant.model.RawRestaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class RawRestaurantFetcherImplTest {
    private RawRestaurantFetcherImpl rawRestaurantFetcher;

    @Mock
    private OpenApiManager openApiManager;

    @BeforeEach
    public void setUp() {
        rawRestaurantFetcher = new RawRestaurantFetcherImpl(openApiManager);
    }

    @Test
    public void testFind() {
        Genrestrt fakeGenrestrt = new Genrestrt();
        fakeGenrestrt.setHead(new Head());
        List<Row> fakeRows = new ArrayList<>();
        Row fakeRow = getRow();


        fakeRows.add(fakeRow);
        fakeGenrestrt.setRow(fakeRows);

        // openApiManager.fetch() 메서드 호출 시 위에서 생성한 가짜 데이터 반환하도록 설정
        Mockito.when(openApiManager.fetch(Mockito.anyInt(), Mockito.anyInt())).thenReturn(fakeGenrestrt);

        // page 비교하려면 같은 값 입력
        // RawRestaurantFetcherImpl의 find 메서드 호출
        RawRestaurantFetchResult result = rawRestaurantFetcher.find(1, 10);

        // 테스트 코드: 원하는 결과 확인
        List<RawRestaurant> rawRestaurants = new ArrayList<>();
        RawRestaurant rawRestaurant = RawRestaurant.builder()
                                          .SIGUN_NM(fakeRow.getSigunNm())
                                          .SIGUN_CD(fakeRow.getSigunCd())
                                          .BIZPLC_NM(fakeRow.getBizplcNm())
                                          .LICENSG_DE(fakeRow.getLicensgDe())
                                          .BSN_STATE_NM(fakeRow.getBsnStateNm())
                                          .CLSBIZ_DE(fakeRow.getClsbizDe())
                                          .LOCPLC_AR(fakeRow.getLocplcAr())
                                          .GRAD_FACLT_DIV_NM(fakeRow.getGradFacltDivNm())
                                          .MALE_ENFLPSN_CNT(fakeRow.getMaleEnflpsnCnt())
                                          .YY(fakeRow.getYy())
                                          .MULTI_USE_BIZESTBL_YN(fakeRow.getMultiUseBizestblYn())
                                          .TOT_FACLT_SCALE(fakeRow.getTotFacltScale())
                                          .FEMALE_ENFLPSN_CNT(fakeRow.getFemaleEnflpsnCnt())
                                          .BSNSITE_CIRCUMFR_DIV_NM(fakeRow.getBsnsiteCircumfrDivNm())
                                          .SANITTN_INDUTYPE_NM(fakeRow.getSanittnIndutypeNm())
                                          .SANITTN_BIZCOND_NM(fakeRow.getSanittnBizcondNm())
                                          .TOT_EMPLY_CNT(fakeRow.getTotEmplyCnt())
                                          .REFINE_LOTNO_ADDR(fakeRow.getRefineLotnoAddr())
                                          .REFINE_ROADNM_ADDR(fakeRow.getRefineRoadnmAddr())
                                          .REFINE_ZIP_CD(fakeRow.getRefineZipCd())
                                          .REFINE_WGS84_LOGT("0.0")
                                          .REFINE_WGS84_LAT("0.0")
                                          .build();

        rawRestaurants.add(rawRestaurant);
        printTestResults(rawRestaurants);
    }

    private static Row getRow() {
        Row fakeRow = new Row();

// 모든 필드 값을 설정
        fakeRow.setSigunNm("TestSigun");
        fakeRow.setSigunCd("TestSigunCd");
        fakeRow.setBizplcNm("TestBizplcNm");
        fakeRow.setLicensgDe("TestLicensgDe");
        fakeRow.setBsnStateNm("TestBsnStateNm");
        fakeRow.setClsbizDe("TestClsbizDe");
        fakeRow.setLocplcAr("TestLocplcAr");
        fakeRow.setGradFacltDivNm("TestGradFacltDivNm");
        fakeRow.setMaleEnflpsnCnt("TestMaleEnflpsnCnt");
        fakeRow.setYy("TestYy");
        fakeRow.setMultiUseBizestblYn("TestMultiUseBizestblYn");
        fakeRow.setGradDivNm("TestGradDivNm");
        fakeRow.setTotFacltScale("TestTotFacltScale");
        fakeRow.setFemaleEnflpsnCnt("TestFemaleEnflpsnCnt");
        fakeRow.setBsnsiteCircumfrDivNm("TestBsnsiteCircumfrDivNm");
        fakeRow.setSanittnIndutypeNm("TestSanittnIndutypeNm");
        fakeRow.setSanittnBizcondNm("TestSanittnBizcondNm");
        fakeRow.setTotEmplyCnt("TestTotEmplyCnt");
        fakeRow.setRefineLotnoAddr("TestRefineLotnoAddr");
        fakeRow.setRefineRoadnmAddr("TestRefineRoadnmAddr");
        fakeRow.setRefineZipCd("TestRefineZipCd");
        fakeRow.setRefineWgs84Logt("0.0");
        fakeRow.setRefineWgs84Lat("0.0");
        return fakeRow;
    }

    private void printTestResults(List<RawRestaurant> rawRestaurants) {
        for (RawRestaurant restaurant : rawRestaurants) {
            System.out.println("SIGUN_NM: " + restaurant.getSIGUN_NM());

            System.out.println("--------------------------------------------------------");
        }
    }
}
