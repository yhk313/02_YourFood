package beforespring.yourfood.batch.rawrestaurant.model;

import static beforespring.yourfood.batch.BatchFixture.aRawRestaurant;
import static org.assertj.core.api.Assertions.assertThat;

import beforespring.yourfood.app.restaurant.domain.CuisineType;
import beforespring.yourfood.batch.rawrestaurant.model.RawRestaurant.RawRestaurantBuilder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RawRestaurantTest {

    @Test
    @DisplayName("변화할수 없는 정보는 체크하지 않음.")
    void isUpdatedFrom_do_not_check_immutable_info() {
        RawRestaurantBuilder baseBuilder = aRawRestaurant();
        RawRestaurant rawA = baseBuilder
                                 .id(1L)
                                 .REFINE_WGS84_LAT("32.123")
                                 .REFINE_WGS84_LOGT("123.123")
                                 .build();
        RawRestaurant rawB = baseBuilder
                                 .id(null)
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
                                 .id(1L)
                                 .BSN_STATE_NM("영업")
                                 .build();
        RawRestaurant rawB = baseBuilder
                                 .id(null)
                                 .BSN_STATE_NM("폐업")
                                 .build();

        assertThat(rawA.isOutdatedCompareTo(rawB))
            .isTrue();
    }

    @Test
    @DisplayName("서비스에 필요한 정보에 변경사항이 있으면 CrucialInfoUpdatedAt 업데이트")
    void updateDataFrom_should_set_IS_UPDATE_to_true() {
        // given
        LocalDateTime now = LocalDateTime.now();

        RawRestaurantBuilder baseBuilder = aRawRestaurant();

        RawRestaurant fetched = baseBuilder
                                    .id(null)
                                    .BSN_STATE_NM("폐업")
                                    .CLSBIZ_DE(LocalDate.now().toString())
                                    .fetchedAt(now)
                                    .crucialInfoFetchedAt(now)
                                    .build();

        LocalDateTime oldUpdatedDate = now.minusYears(1);
        RawRestaurant existing = baseBuilder
                                     .id(1L)
                                     .BSN_STATE_NM("영업")
                                     .CLSBIZ_DE(null)
                                     .crucialInfoFetchedAt(oldUpdatedDate)
                                     .hasNonUpdatedInfo(false)
                                     .build();


        // when
        existing.updateDataFrom(fetched);

        // then
        assertThat(existing.isHasNonUpdatedInfo())
            .describedAs("Restaurant에 반영되지 않은 값이 새로 들어왔으므로 true")
            .isTrue();

        assertThat(existing.getCrucialInfoFetchedAt())
            .describedAs("변경시간이 업데이트되어야함.")
            .isEqualTo(now);
    }

    @Test
    @DisplayName("변경사항이 있지만 서비스에 필요 없는 정보인 경우 CrucialInfoUpdatedAt 변경되지 않음.")
    void updateDataFrom_should_not_set_IS_UPDATED_to_true() {
        // given
        RawRestaurantBuilder baseBuilder = aRawRestaurant();

        RawRestaurant fetched = baseBuilder
                                    .id(null)
                                    .GRAD_FACLT_DIV_NM("필요없는 정보")
                                    .CLSBIZ_DE(LocalDate.now().minusDays(10).toString())
                                    .fetchedAt(LocalDateTime.now().minusDays(10))
                                    .build();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oldUpdatedDate = now.minusYears(1);
        RawRestaurant existing = baseBuilder
                                     .id(1L)
                                     .GRAD_FACLT_DIV_NM("필요없는 정보가 변경됨")
                                     .crucialInfoFetchedAt(oldUpdatedDate)
                                     .hasNonUpdatedInfo(false)
                                     .build();

        // when
        existing.updateDataFrom(fetched);

        // then
        assertThat(existing.isHasNonUpdatedInfo())
            .describedAs("서비스에 필요한 값이 변경되지 않음. false")
            .isFalse();

        assertThat(existing.getCrucialInfoFetchedAt())
            .describedAs("필요없는 정보가 변경되었으므로 crucialInfoUpdatedAt은 변경되지 않음.")
            .isEqualTo(oldUpdatedDate);
    }

    /**
     * API를 통해 음식점을 분류별로 가져오는데, 한 음식점이 여러 분류를 가질 수 있음.
     * 때문에 여러 분류를 가지게 되는 경우에는 값을 덮어 씌우면 안 되고, collection을 활용하여 추가함.
     */
    @Test
    @DisplayName("cuisine type이 업데이트 되는 상황 테스트")
    void cuisineType_update_should_add_cuisineType() {
        RawRestaurantBuilder baseBuilder = aRawRestaurant();

        LocalDateTime now = LocalDateTime.now();
        RawRestaurant fetched = baseBuilder
                                    .id(null)
                                    .cuisineType(CuisineType.KOREAN)
                                    .fetchedAt(now)
                                    .build();


        RawRestaurant existing = baseBuilder
                                     .id(1L)
                                     .cuisineType(CuisineType.CHINESE)
                                     .hasNonUpdatedInfo(false)
                                     .fetchedAt(now)
                                     .build();

        // when
        existing.updateDataFrom(fetched);

        // then

        Set<CuisineType> cuisineTypes = existing.getCuisineTypes();

        assertThat(cuisineTypes)
            .describedAs("기존 값이 지워지지 않아야하고, 새로운 값이 들어가야함.")
            .containsExactlyInAnyOrder(CuisineType.KOREAN, CuisineType.CHINESE);

        assertThat(existing.isHasNonUpdatedInfo())
            .describedAs("중요 정보이므로 값이 true가 되어야함.")
            .isTrue();
    }
}