package beforespring.yourfood.batch.rawrestaurant.update;

import beforespring.yourfood.app.restaurant.domain.AddressCode;
import beforespring.yourfood.app.restaurant.domain.Restaurant;
import beforespring.yourfood.app.utils.Coordinates;
import beforespring.yourfood.batch.rawrestaurant.model.RawRestaurant;
import java.math.BigDecimal;
import org.springframework.batch.item.ItemProcessor;

public class RestaurantUpdateProcessor implements ItemProcessor<RestaurantUpdateReaderResult, Restaurant> {
    // todo address code, cuisine type
    @Override
    public Restaurant process(RestaurantUpdateReaderResult item) throws Exception {
        RawRestaurant rawRestaurant = item.rawRestaurant();
        Restaurant restaurant = item.restaurant();

        if (restaurant == null) {  // 새로 생성됨.
            return newRestaurantFrom(rawRestaurant);
        }
        else {
            return updateRestaurantFrom(restaurant, rawRestaurant);
        }
    }

    private static Restaurant updateRestaurantFrom(
        Restaurant restaurant,
        RawRestaurant rawRestaurant
    ) {
        restaurant.updateOperating(isOperating(rawRestaurant));
        rawRestaurant.markInfoUpdatedToRestaurant();
        return restaurant;
    }

    private static boolean isOperating(RawRestaurant rawRestaurant) {
        return rawRestaurant.getBSN_STATE_NM()
                                .contains("영업");
    }

    private static Restaurant newRestaurantFrom(RawRestaurant rawRestaurant) {
        boolean operating = isOperating(rawRestaurant);

        rawRestaurant.markInfoUpdatedToRestaurant();
        return Restaurant.builder()
                   .address(rawRestaurant.getRawRestaurantId().getREFINE_ROADNM_ADDR())
                   .name(rawRestaurant.getRawRestaurantId().getBIZPLC_NM())
                   .coordinates(
                       new Coordinates(
                           new BigDecimal(rawRestaurant.getREFINE_WGS84_LAT()),
                           new BigDecimal(rawRestaurant.getREFINE_WGS84_LOGT()))
                   )
                   .addressCode(new AddressCode(
                       rawRestaurant.getSido(),
                       rawRestaurant.getSIGUN_NM()))
                   .operating(operating)
                   .description("")  // not null 오류 방지용 초기값. todo 간단 정보 들어가도록 수정
                   .build();
    }
}
