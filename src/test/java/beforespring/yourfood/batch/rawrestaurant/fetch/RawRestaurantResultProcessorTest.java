package beforespring.yourfood.batch.rawrestaurant.fetch;

import static beforespring.yourfood.batch.BatchFixture.aRawRestaurant;
import static org.assertj.core.api.Assertions.assertThat;

import beforespring.yourfood.batch.rawrestaurant.model.RawRestaurant;
import beforespring.yourfood.batch.rawrestaurant.model.RawRestaurant.RawRestaurantBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RawRestaurantResultProcessorTest {

    RawRestaurantResultProcessor rawRestaurantResultProcessor = new RawRestaurantResultProcessor();

    @Test
    @DisplayName("새로운 정보가 들어온 경우")
    void process_not_existing_new_data() throws Exception {
        // given
        RawRestaurant fetched = aRawRestaurant()
                                  .build();
        RawRestaurant existing = null;
        RawRestaurantReaderResult given = new RawRestaurantReaderResult(fetched,
            existing);

        // when
        RawRestaurant actual = rawRestaurantResultProcessor.process(given);

        // then
        assertThat(actual)
            .isSameAs(fetched);
    }

    @Test
    @DisplayName("기존 정보와 동일한 경우 null 반환")
    void process_no_difference() throws Exception {
        // given
        RawRestaurantBuilder baseBuilder = aRawRestaurant();
        RawRestaurant fetched = baseBuilder
                                  .build();
        RawRestaurant existing = baseBuilder
                                     .id(1L)
                                     .build();

        RawRestaurantReaderResult given = new RawRestaurantReaderResult(fetched, existing);

        // when
        RawRestaurant actual = rawRestaurantResultProcessor.process(given);

        // then
        assertThat(actual)
            .isNull();
    }

    @Test
    @DisplayName("기존에 존재하는 데이터이지만, 값이 변경된 경우. 이 케이스의 변경된 값은 서비스에서 필요 없는 값.")
    void process_updated_non_important_data() throws Exception {
        // given
        RawRestaurantBuilder baseBuilder = aRawRestaurant();
        RawRestaurant fetched = baseBuilder
                                    .GRAD_FACLT_DIV_NM("중요하지 않은 정보가 변경됨")
                                  .build();
        RawRestaurant existing = baseBuilder
                                     .id(1L)
                                     .GRAD_FACLT_DIV_NM("중요하지 않은 정보")
                                     .build();

        String beforeUpdate = existing.getGRAD_FACLT_DIV_NM();

        RawRestaurantReaderResult given = new RawRestaurantReaderResult(fetched, existing);

        // when
        RawRestaurant actual = rawRestaurantResultProcessor.process(given);

        // then
        assertThat(actual)
            .describedAs("기존 데이터를 반환해야함.")
            .isSameAs(existing)
            .extracting(RawRestaurant::getGRAD_FACLT_DIV_NM)
            .describedAs("변동이 있는 필드를 update하고 반환해야함.")
                .isNotEqualTo(beforeUpdate)
                .isEqualTo(fetched.getGRAD_FACLT_DIV_NM())
        ;
    }

    @Test
    @DisplayName("기존에 존재하는 데이터이지만, 값이 변경된 경우. 이 케이스의 변경된 값은 서비스에서 필요한 값.")
    void process_updated_important_data() throws Exception {
        // given
        RawRestaurantBuilder baseBuilder = aRawRestaurant();
        RawRestaurant fetched = baseBuilder
                                    .BSN_STATE_NM("폐업")
                                  .build();
        RawRestaurant existing = baseBuilder
                                     .id(1L)
                                     .BSN_STATE_NM("영업")
                                     .build();

        String beforeUpdate = existing.getGRAD_FACLT_DIV_NM();

        RawRestaurantReaderResult given = new RawRestaurantReaderResult(fetched, existing);

        // when
        RawRestaurant actual = rawRestaurantResultProcessor.process(given);

        // then
        assertThat(actual)
            .describedAs("기존 데이터를 반환해야함.")
            .isSameAs(existing)
            .extracting(RawRestaurant::getBSN_STATE_NM)
            .describedAs("변동이 있는 필드를 update하고 반환해야함.")
                .isNotEqualTo(beforeUpdate)
                .isEqualTo(fetched.getBSN_STATE_NM())
        ;
    }
}