package beforespring.yourfood.batch.rawrestaurant.fetch.fetcherimplement.gyeonggi.xmlmapper;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Row {
    /**
     * API 호출로 받은 데이터 중 <row /> 부분의 필드를 맵핑하기위해
     * 각각 대입할 수 있는 타입으로 필드들을 만들었습니다.
     *
     * 각각 변수명을 필드명에 맞추면 알아서 해준다고 되어 있었는데
     * @JacksonXmlProperty 어노테이션을 통해서 직접 맵핑을 해주지 않으면
     * 에러가 나는 문제가 있어, 각각 직접 맵핑해주었습니다.
     *
     *  @JacksonXmlProperty(localName = "SIGUN_NM")에서
     *  localName이 입력받은 Xml 데이터중에서 찾을 필드명을 입력하면 됩니다.
     */
    @JacksonXmlProperty(localName = "SIGUN_NM") String sigunNm;

    @JacksonXmlProperty(localName = "SIGUN_CD") String sigunCd;

    @JacksonXmlProperty(localName = "BIZPLC_NM") String bizplcNm;

    @JacksonXmlProperty(localName = "LICENSG_DE") String licensgDe;

    @JacksonXmlProperty(localName = "BSN_STATE_NM") String bsnStateNm;

    @JacksonXmlProperty(localName = "CLSBIZ_DE") String clsbizDe;

    @JacksonXmlProperty(localName = "LOCPLC_AR") String locplcAr;

    @JacksonXmlProperty(localName = "GRAD_FACLT_DIV_NM") String gradFacltDivNm;

    @JacksonXmlProperty(localName = "MALE_ENFLPSN_CNT") String maleEnflpsnCnt;

    @JacksonXmlProperty(localName = "YY") String yy;

    @JacksonXmlProperty(localName = "MULTI_USE_BIZESTBL_YN") String multiUseBizestblYn;

    @JacksonXmlProperty(localName = "GRAD_DIV_NM") String gradDivNm;

    @JacksonXmlProperty(localName = "TOT_FACLT_SCALE") String totFacltScale;

    @JacksonXmlProperty(localName = "FEMALE_ENFLPSN_CNT") String femaleEnflpsnCnt;

    @JacksonXmlProperty(localName = "BSNSITE_CIRCUMFR_DIV_NM") String bsnsiteCircumfrDivNm;

    @JacksonXmlProperty(localName = "SANITTN_INDUTYPE_NM") String sanittnIndutypeNm;

    @JacksonXmlProperty(localName = "SANITTN_BIZCOND_NM") String sanittnBizcondNm;

    @JacksonXmlProperty(localName = "TOT_EMPLY_CNT") String totEmplyCnt;

    @JacksonXmlProperty(localName = "REFINE_LOTNO_ADDR") String refineLotnoAddr;

    @JacksonXmlProperty(localName = "REFINE_ROADNM_ADDR") String refineRoadnmAddr;

    @JacksonXmlProperty(localName = "REFINE_ZIP_CD") String refineZipCd;

    @JacksonXmlProperty(localName = "REFINE_WGS84_LOGT") String refineWgs84Logt;

    @JacksonXmlProperty(localName = "REFINE_WGS84_LAT") String refineWgs84Lat;
}

