package beforespring.yourfood.batch.rawrestaurant.fetch;

import beforespring.yourfood.app.restaurant.domain.CuisineType;
import beforespring.yourfood.batch.rawrestaurant.fetch.fetcherimplement.GyeonggiFetcherImpl;
import beforespring.yourfood.batch.rawrestaurant.fetch.fetcherimplement.gyeonggi.GyeonggiOpenApiManagerFactory;
import beforespring.yourfood.batch.rawrestaurant.model.RawRestaurant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RawRestaurantFetcherImplTest {

    @Autowired
    private GyeonggiOpenApiManagerFactory gyeonggiOpenApiManagerFactory;

    /**
     *
     * assertThat으로 체크를 여러번 하고 있지만,
     * 최종적으로는 입력된 pageSize 만큼 데이터를 잘 불러오는지 확인하고 있습니다.
     */
    @Test
    void test_find_method() {
        // given
        RawRestaurantFetcher rawRestaurantFetcher = new GyeonggiFetcherImpl(gyeonggiOpenApiManagerFactory, CuisineType.CAFE);

        // when
        int page = 2;
        int pageSize = 10;
        RawRestaurantFetchResult rawRestaurants = rawRestaurantFetcher.find(page, pageSize);

        // then
        assertThat(rawRestaurants).isNotNull();

        List<RawRestaurant> rawRestaurantList = rawRestaurants.rawRestaurants();
        assertThat(rawRestaurantList).isNotNull();

        assertThat(rawRestaurantList.size()).isEqualTo(pageSize);

        /**
         * 해당 반목문으로 출력하는 부분은 추후 삭제예정
         */
        for (RawRestaurant rawRestaurant : rawRestaurantList) {
            System.out.println(rawRestaurant.toString());
        }
    }
}