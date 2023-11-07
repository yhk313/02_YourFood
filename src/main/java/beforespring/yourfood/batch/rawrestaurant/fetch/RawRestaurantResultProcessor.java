package beforespring.yourfood.batch.rawrestaurant.fetch;

import static org.springframework.util.StringUtils.hasText;

import beforespring.yourfood.batch.rawrestaurant.model.RawRestaurant;
import org.springframework.batch.item.ItemProcessor;

public class RawRestaurantResultProcessor implements
    ItemProcessor<RawRestaurantReaderResult, RawRestaurant> {
    private final String sido;

    public RawRestaurantResultProcessor(String sido) {
        this.sido = sido;
    }

    /**
     * DB에서 불러온 기존 값과 API를 통해 불러온 값을 비교하고, 변경사항이 있는 경우 DB에서 불러온 값을 업데이트함.
     * DB의 값이 null인 경우 API로 불러온 값을 insert
     * insert 쿼리와 update 쿼리를 writer가 적절히 처리하는 것을 가정하였음.
     * Restaurant#isNewlyFetched() 메서드를 통해서 처리하게 됨.
     * @param item
     * @return RawRestaurant
     */
    @Override
    public RawRestaurant process(RawRestaurantReaderResult item) throws Exception {
        RawRestaurant newlyFetched = item.newlyFetched();
        RawRestaurant existing = item.existing();

        if (!isIdValid(newlyFetched)) {
            return null;
        }

        if (!isCoordsValid(newlyFetched)) {
            return null;
        }

        // 기존에 DB에 저장되지 않은 상태이고, 현재 영업중인 경우 newlyFetched를 반환.
        if (newInfo(existing)) {
            if (isOperating(newlyFetched)) {
                newlyFetched.setSido(sido);
                return newlyFetched;
            } else {
                return null;
            }
        }

        // DB에 데이터가 있으면 기존 값을 update 후 return.
        // ItemReader, ItemWriter가 JPA로 구현된 경우엔 알아서 변경감지가 수행되고 update 쿼리가 나가기 때문에 RawRestaurant를 반환할 필요가 없음.
        // 그러나 다른 DB 접근 기술을 사용하여 구현한 경우 RawRestaurant가 필요할 수 있음. 반환해야함.
        if (existing.isOutdatedCompareTo(newlyFetched)) {
            existing.updateDataFrom(newlyFetched);
            return existing;
        }

        // DB에 데이터가 있으나, 변경사항이 없다면 처리하지 않음.
        return null;
    }

    private static boolean isOperating(RawRestaurant newlyFetched) {
        return "영업".equals(newlyFetched.getBSN_STATE_NM());
    }

    private static boolean newInfo(RawRestaurant existing) {
        return existing == null;
    }

    private static boolean isCoordsValid(RawRestaurant newlyFetched) {
        return (hasText(newlyFetched.getREFINE_WGS84_LAT())
                    && hasText(newlyFetched.getREFINE_WGS84_LOGT()));
    }

    private static boolean isIdValid(RawRestaurant newlyFetched) {
        return (hasText(newlyFetched.getRawRestaurantId().getBIZPLC_NM())
                     && hasText(newlyFetched.getRawRestaurantId().getREFINE_ROADNM_ADDR()));
    }
}
