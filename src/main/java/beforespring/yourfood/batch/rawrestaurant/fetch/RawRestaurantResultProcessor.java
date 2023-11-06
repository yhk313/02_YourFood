package beforespring.yourfood.batch.rawrestaurant.fetch;

import beforespring.yourfood.batch.rawrestaurant.model.RawRestaurant;
import org.springframework.batch.item.ItemProcessor;

public class RawRestaurantResultProcessor implements
    ItemProcessor<RawRestaurantReaderResult, RawRestaurant> {

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

        // DB에 데이터가 없으면 새로 불러온 값을 insert
        if (existing == null) {
            return newlyFetched;
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
}
