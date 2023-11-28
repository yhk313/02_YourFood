package beforespring.yourfood.batch.rawrestaurant.model;

import beforespring.yourfood.app.restaurant.domain.CuisineType;
import beforespring.yourfood.app.restaurant.infra.CuisineTypeConverter;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.*;

@Entity
@Table(
    name = "raw_restaurant",
    indexes = {
        @Index(
            name = "idx__raw_restaurant__BIZPLC_NM__REFINE_ROADNM_ADDR",
            columnList = "BIZPLC_NM, REFINE_ROADNM_ADDR",
            unique = true
        ),
        @Index(
            name = "idx__raw_restaurant__crucial_info_fetched_at",
            columnList = "crucial_info_fetched_at"
        )
    }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class RawRestaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private RawRestaurantId rawRestaurantId;
    private String SIGUN_NM;
    private String SIGUN_CD;
    private String LICENSG_DE;
    private String BSN_STATE_NM;
    private String CLSBIZ_DE;
    private String LOCPLC_AR;
    private String GRAD_FACLT_DIV_NM;
    private String MALE_ENFLPSN_CNT;
    private String YY;
    private String MULTI_USE_BIZESTBL_YN;
    private String TOT_FACLT_SCALE;
    private String FEMALE_ENFLPSN_CNT;
    private String BSNSITE_CIRCUMFR_DIV_NM;
    private String SANITTN_INDUTYPE_NM;
    private String SANITTN_BIZCOND_NM;
    private String TOT_EMPLY_CNT;
    private String REFINE_LOTNO_ADDR;
    private String REFINE_ZIP_CD;
    private String REFINE_WGS84_LAT;
    private String REFINE_WGS84_LOGT;

    /**
     * 변경된 값이 Restaurant에 반영되었으면 false, 아직 반영되지 않았으면 true
     */
    @Column(name = "has_non_updated_info")
    private boolean hasNonUpdatedInfo;

    /**
     * 식당 정보가 변동된 날짜. 서비스에 필요한 정보를 판별하는데 사용됨. IS_UPDATED 등, 식당 정보와 관련 없는 정보가 변동될 때에는 업데이트 되면 안 됨.
     */
    @Column(name = "fetched_at")
    private LocalDateTime fetchedAt;

    /**
     * 서비스에 필요한 주요 정보가 업데이트된 시각. 레스토랑 정보 업데이트 배치 작업의 커서로 활용됨.
     */
    @Column(name = "crucial_info_fetched_at")
    private LocalDateTime crucialInfoFetchedAt;

    private String sido;

    @Column(name = "cuisine_types")
    @Convert(converter = CuisineTypeConverter.class)
    private Set<CuisineType> cuisineTypes = new TreeSet<>();

    /**
     * @param id 존재하지 않는 경우 외부 API를 통해 새로 불러온 객체로 판단함.
     * @param hasNonUpdatedInfo true인 경우, 서비스에 필요한 Restaurant에 RawRestaurant의
     * @param crucialInfoFetchedAt API에서 불러온 값이 DB의 값과 다를 때 발생함.
     * @param fetchedAt API에서 불러와 DB에 저장한 시간. null일시 현재 시각
     * @param cuisineType 음식점 종류
     * @param sido 시, 도 정보(ex. 경기도)
     */
    @Builder
    public RawRestaurant(
        Long id,
        String SIGUN_NM,
        String SIGUN_CD,
        String BIZPLC_NM,
        String LICENSG_DE,
        String BSN_STATE_NM,
        String CLSBIZ_DE,
        String LOCPLC_AR,
        String GRAD_FACLT_DIV_NM,
        String MALE_ENFLPSN_CNT,
        String YY,
        String MULTI_USE_BIZESTBL_YN,
        String TOT_FACLT_SCALE,
        String FEMALE_ENFLPSN_CNT,
        String BSNSITE_CIRCUMFR_DIV_NM,
        String SANITTN_INDUTYPE_NM,
        String SANITTN_BIZCOND_NM,
        String TOT_EMPLY_CNT,
        String REFINE_ROADNM_ADDR,
        String REFINE_LOTNO_ADDR,
        String REFINE_ZIP_CD,
        String REFINE_WGS84_LAT,
        String REFINE_WGS84_LOGT,
        Boolean hasNonUpdatedInfo,
        LocalDateTime crucialInfoFetchedAt,
        LocalDateTime fetchedAt,
        CuisineType cuisineType,
        String sido
    ) {
        this.id = id;
        this.rawRestaurantId = new RawRestaurantId(BIZPLC_NM, REFINE_ROADNM_ADDR);
        this.SIGUN_NM = SIGUN_NM;
        this.SIGUN_CD = SIGUN_CD;
        this.LICENSG_DE = LICENSG_DE;
        this.BSN_STATE_NM = BSN_STATE_NM;
        this.CLSBIZ_DE = CLSBIZ_DE;
        this.LOCPLC_AR = LOCPLC_AR;
        this.GRAD_FACLT_DIV_NM = GRAD_FACLT_DIV_NM;
        this.MALE_ENFLPSN_CNT = MALE_ENFLPSN_CNT;
        this.YY = YY;
        this.MULTI_USE_BIZESTBL_YN = MULTI_USE_BIZESTBL_YN;
        this.TOT_FACLT_SCALE = TOT_FACLT_SCALE;
        this.FEMALE_ENFLPSN_CNT = FEMALE_ENFLPSN_CNT;
        this.BSNSITE_CIRCUMFR_DIV_NM = BSNSITE_CIRCUMFR_DIV_NM;
        this.SANITTN_INDUTYPE_NM = SANITTN_INDUTYPE_NM;
        this.SANITTN_BIZCOND_NM = SANITTN_BIZCOND_NM;
        this.TOT_EMPLY_CNT = TOT_EMPLY_CNT;
        this.REFINE_LOTNO_ADDR = REFINE_LOTNO_ADDR;
        this.REFINE_ZIP_CD = REFINE_ZIP_CD;
        this.REFINE_WGS84_LAT = REFINE_WGS84_LAT;
        this.REFINE_WGS84_LOGT = REFINE_WGS84_LOGT;

        LocalDateTime now = LocalDateTime.now();

        this.sido = sido;

        // null인 경우 외부에서 읽어온 값. 기본값은 false
        if (hasNonUpdatedInfo != null) {
            this.hasNonUpdatedInfo = hasNonUpdatedInfo;
        }

        // null인 경우 외부에서 읽어온 값임. now로 설정
        this.crucialInfoFetchedAt = crucialInfoFetchedAt == null ? now : crucialInfoFetchedAt;

        // null인 경우 외부에서 읽어온 값임. now로 설정
        this.fetchedAt = fetchedAt == null ? now : fetchedAt;

        // 생성 시점에는 레스토랑 타입이 하나만 추가됨
        if (cuisineType != null) {
            this.cuisineTypes.add(cuisineType);
        }
    }


    /**
     * DB에서 불러온 엔티티의 값을 외부에서 새로 불러온 엔티티의 값으로 업데이트함. 외부에서 새로 불러온 객체의 값을 업데이트해선 안 됨. (잠재적 버그 발생 방지)
     *
     * @param fetched 새로 fetch 받아온 데이터
     */
    public void updateDataFrom(RawRestaurant fetched) {
        // id가 일치하지 않는 경우 업데이트 불가
        this.validateRawRestaurantIdMatches(fetched);
        // 외부 api를 통해 새로 불러온 객체가 아닌, DB에서 가져온 객체만 호출 가능
        this.validateExistingEntity();

        updateMiscInfoFrom(fetched);
        updateCrucialInfoFrom(fetched);
    }

    private void updateMiscInfoFrom(RawRestaurant fetched) {
        // 부가 정보가 업데이트 되지 않음.
        if (!isMiscInfoOutdatedCompareTo(fetched)) {
            return;
        }

        // 변경 시각 갱신
        this.fetchedAt = fetched.getFetchedAt();

        this.LICENSG_DE = fetched.getLICENSG_DE();
        this.LOCPLC_AR = fetched.getLOCPLC_AR();
        this.GRAD_FACLT_DIV_NM = fetched.getGRAD_FACLT_DIV_NM();
        this.MALE_ENFLPSN_CNT = fetched.getMALE_ENFLPSN_CNT();
        this.YY = fetched.getYY();
        this.MULTI_USE_BIZESTBL_YN = fetched.getMULTI_USE_BIZESTBL_YN();
        this.TOT_FACLT_SCALE = fetched.getTOT_FACLT_SCALE();
        this.FEMALE_ENFLPSN_CNT = fetched.getFEMALE_ENFLPSN_CNT();
        this.BSNSITE_CIRCUMFR_DIV_NM = fetched.getBSNSITE_CIRCUMFR_DIV_NM();
        this.SANITTN_INDUTYPE_NM = fetched.getSANITTN_INDUTYPE_NM();
        this.SANITTN_BIZCOND_NM = fetched.getSANITTN_BIZCOND_NM();
    }

    /**
     * 음식점 종류(cuisineType)에 대해서 List로 구현한 이유는, 하나의 식당이 여러개의 종류에 포함될 수 있다고 판단했기 때문임.
     * ex) 한식+분식, 일식+복어, 주점+한식 등
     */
    private void updateCrucialInfoFrom(RawRestaurant fetched) {
        // 변경된 정보가 없으면 바로 return
        if (!this.isCrucialInfoOutdatedCompareTo(fetched)) {
            return;
        }

        // 반영되지 않은 주요 정보 업데이트 되었다고 표시
        this.hasNonUpdatedInfo = true;

        // 주요 정보 변경 시각 업데이트
        this.crucialInfoFetchedAt = fetched.getFetchedAt();

        // 영업 상태, 폐업 일자 업데이트
        this.BSN_STATE_NM = fetched.getBSN_STATE_NM();
        this.CLSBIZ_DE = fetched.getCLSBIZ_DE();

        // 음식점 종류 추가
        this.cuisineTypes.addAll(fetched.getCuisineTypes());
    }

    private void validateExistingEntity() {
        if (this.isNewlyFetched()) {
            throw new IllegalCallerException("외부에서 새로 불러온 객체가 아닌, DB에서 가져온 객체만 호출 가능");
        }
    }

    private void validateRawRestaurantIdMatches(RawRestaurant fetched) {
        if (!this.rawRestaurantId.equals(fetched.getRawRestaurantId())) {
            throw new IllegalStateException("rawRestaurantId가 일치하지 않음");
        }
    }

    /**
     * 이전 RawRestaurant와 비교하여 정보가 변경되었는지 여부. 변경 불가능한 값은 체크하지 않음.
     *
     * @param newlyFetched 비교할 대상
     * @return 업데이트 되었는지 여부
     */
    public boolean isOutdatedCompareTo(RawRestaurant newlyFetched) {
        // 외부 api를 통해 새로 불러온 객체가 아닌, DB에서 가져온 객체만 호출 가능
        this.validateExistingEntity();
        // RawRestaurantId가 일치하지 않으면
        this.validateRawRestaurantIdMatches(newlyFetched);

        boolean miscInfoOutdated = isMiscInfoOutdatedCompareTo(newlyFetched);
        boolean crucialInfoOutdated = isCrucialInfoOutdatedCompareTo(newlyFetched);
        return miscInfoOutdated || crucialInfoOutdated;
    }

    /**
     * 서비스에서 필요로 하는 값 중, 변경된 값이 있는지 확인함. 현재 필요한 값은 영업 여부, 음식점 종류 값.
     * 값 중 하나라도 변경된 경우 true
     *
     * @param newlyFetched
     * @return 영업 여부가 변경되었는지, 음식점 종류에 변화가 있는지.
     */
    public boolean isCrucialInfoOutdatedCompareTo(RawRestaurant newlyFetched) {
        // 음식점 종류 변화 여부
        if (cuisineTypeOutdatedCompareTo(newlyFetched)) {
            return true;
        }
        // 영업 상태 변화 여부
        if (operatingStatusOutdatedCompareTo(newlyFetched)) {
            return true;
        }

        return false;
    }

    private boolean isMiscInfoOutdatedCompareTo(RawRestaurant newlyFetched) {
        return !miscInfoMatches(newlyFetched);
    }

    private boolean miscInfoMatches(RawRestaurant newlyFetched) {
        return Objects.equals(getSIGUN_NM(), newlyFetched.getSIGUN_NM())
                   && Objects.equals(getSIGUN_CD(), newlyFetched.getSIGUN_CD())
                   && Objects.equals(getLICENSG_DE(), newlyFetched.getLICENSG_DE())
                   && Objects.equals(getCLSBIZ_DE(), newlyFetched.getCLSBIZ_DE())
                   && Objects.equals(getLOCPLC_AR(), newlyFetched.getLOCPLC_AR())
                   && Objects.equals(getGRAD_FACLT_DIV_NM(), newlyFetched.getGRAD_FACLT_DIV_NM())
                   && Objects.equals(getMALE_ENFLPSN_CNT(), newlyFetched.getMALE_ENFLPSN_CNT())
                   && Objects.equals(getYY(), newlyFetched.getYY())
                   && Objects.equals(getMULTI_USE_BIZESTBL_YN(),
            newlyFetched.getMULTI_USE_BIZESTBL_YN())
                   && Objects.equals(getTOT_FACLT_SCALE(), newlyFetched.getTOT_FACLT_SCALE())
                   && Objects.equals(getFEMALE_ENFLPSN_CNT(), newlyFetched.getFEMALE_ENFLPSN_CNT())
                   && Objects.equals(getBSNSITE_CIRCUMFR_DIV_NM(),
            newlyFetched.getBSNSITE_CIRCUMFR_DIV_NM())
                   && Objects.equals(getSANITTN_INDUTYPE_NM(),
            newlyFetched.getSANITTN_INDUTYPE_NM())
                   && Objects.equals(getSANITTN_BIZCOND_NM(), newlyFetched.getSANITTN_BIZCOND_NM())
                   && Objects.equals(getTOT_EMPLY_CNT(), newlyFetched.getTOT_EMPLY_CNT());
    }

    private boolean operatingStatusOutdatedCompareTo(RawRestaurant newlyFetched) {
        return !(Objects.equals(getBSN_STATE_NM(), newlyFetched.getBSN_STATE_NM()));
    }

    private boolean cuisineTypeOutdatedCompareTo(RawRestaurant newlyFetched) {
        return !cuisineTypes.containsAll(newlyFetched.getCuisineTypes());
    }

    public boolean isNewlyFetched() {
        return this.id == null;
    }

    public void markInfoUpdatedToRestaurant() {
        this.hasNonUpdatedInfo = false;
    }

    // todo refactor 시도정보는 fetcher에서 붙여줘야함.
    public void setSido(String sido) {
        this.sido = sido;
    }
}
