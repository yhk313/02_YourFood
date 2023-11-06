package beforespring.yourfood.batch.rawrestaurant.model;

import static beforespring.yourfood.batch.BatchFixture.aRawRestaurant;
import static org.assertj.core.api.Assertions.assertThat;

import beforespring.yourfood.batch.rawrestaurant.model.RawRestaurant.RawRestaurantBuilder;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RawRestaurantTest {

    @Test
    @DisplayName("변화할수 없는 정보는 체크하지 않음.")
    void isUpdatedFrom_do_not_check_immutable_info() {
        RawRestaurantBuilder baseBuilder = aRawRestaurant();
        RawRestaurant rawA = baseBuilder
                                 .REFINE_WGS84_LAT("32.123")
                                 .REFINE_WGS84_LOGT("123.123")
                                 .build();
        RawRestaurant rawB = baseBuilder
                                 .REFINE_WGS84_LAT("23.456")
                                 .REFINE_WGS84_LOGT("123.456")
                                 .build();

        assertThat(rawA.isOutdatedCompareTo(rawB))
            .isFalse();
    }

    @Test
    @DisplayName("폐업 정보 변경 체크")
    void isUpdatedFrom_do() {
        RawRestaurantBuilder baseBuilder = aRawRestaurant();
        RawRestaurant rawA = baseBuilder
                                 .BSN_STATE_NM("영업")
                                 .build();
        RawRestaurant rawB = baseBuilder
                                 .BSN_STATE_NM("폐업")
                                 .build();

        assertThat(rawA.isOutdatedCompareTo(rawB))
            .isTrue();
    }

    @Test
    @DisplayName("서비스에 필요한 정보에 변경사항이 있으면 IS_UPDATED가 true로 설정되어야함.")
    void updateDataFrom_should_set_IS_UPDATE_to_true() {
        // given
        RawRestaurantBuilder baseBuilder = aRawRestaurant();

        RawRestaurant fetched = baseBuilder
                               .BSN_STATE_NM("폐업")
                               .CLSBIZ_DE(LocalDate.now().toString())
                               .build();

        RawRestaurant existing = baseBuilder
                                     .id(1L)
                                     .BSN_STATE_NM("영업")
                                     .CLSBIZ_DE(null)
                                     .IS_UPDATED(false)
                                     .build();

        // when
        existing.updateDataFrom(fetched);


        // then
        assertThat(existing.isIS_UPDATED())
            .describedAs("영업상태가 변경되었으므로 변경사항이 있다고 표시되어야함.")
            .isTrue();
    }

    @Test
    @DisplayName("변경사항이 있지만 서비스에 필요 없는 정보인 경우 IS_UPDATED를 변경하지 않음.")
    void updateDataFrom_should_not_set_IS_UPDATED_to_true() {
        // given
        RawRestaurantBuilder baseBuilder = aRawRestaurant();

        RawRestaurant fetched = baseBuilder
                                    .GRAD_FACLT_DIV_NM("필요없는 정보")
                               .build();

        RawRestaurant existing = baseBuilder
                                     .id(1L)
                                     .IS_UPDATED(false)
                                     .build();

        // when
        existing.updateDataFrom(fetched);


        // then
        assertThat(existing.isIS_UPDATED())
            .describedAs("필요없는 정보가 변경되었으므로 updated는 false여야함.")
            .isFalse();
    }
}