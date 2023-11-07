package beforespring.yourfood.app.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

class CoordinatesTest {
    BigDecimal lat = new BigDecimal("127.09770130710115");
    BigDecimal lon = new BigDecimal("37.51022757351883");
    Coordinates origin = new Coordinates(lat, lon);


    @Test
    @DisplayName("측지 좌표 간 거리 계산")
    void calculate_distance_test() {
        //given
        BigDecimal lat2 = new BigDecimal("127.09784849516217");
        BigDecimal lon2 = new BigDecimal("37.528205853832276");
        Coordinates destination2 = new Coordinates(lat2, lon2);

        //when
        BigInteger distance = origin.calculateDistance(destination2);

        //then
        assertThat(Math.round(distance.floatValue() / 1000)).isEqualTo(2)
            .describedAs("좌표 사이의 거리는 대략 2km입니다.");

    }

    @Test
    @DisplayName("2km 떨어진 좌표값 구하기")
    void get_lat_diff_by_distance_test() {
        //given

        //when
        BigDecimal latDiff = origin.distanceToLatDiff(2000);
        Coordinates nearCoordinates = new Coordinates(origin.getLat().subtract(latDiff), origin.getLon());
        BigInteger distance = origin.calculateDistance(nearCoordinates);
        //then
        assertThat(Math.round(distance.floatValue())).isEqualTo(2000)
            .describedAs("좌표 사이의 거리는 2km입니다.");
    }

    @Test
    @DisplayName("제공된 거리(meter)만큼 더한 lat, lon 좌표 값 반환")
    void test() {
        // given
        BigDecimal givenLat = new BigDecimal("127.09784849516217");
        BigDecimal givenLon = new BigDecimal("37.528205853832276");
        Coordinates givenCoords = new Coordinates(givenLat, givenLon);
        int givenDistance = 1000;

        // when
        BigDecimal latResult = givenCoords.getLatPlusDistance(givenDistance);
        BigDecimal lonResult = givenCoords.getLonPlusDistance(givenDistance);

        // then
        assertThat(latResult)
            .isEqualTo(Coordinates.distanceToLatDiff(givenDistance).add(givenLat));

        assertThat(lonResult)
            .isEqualTo(Coordinates.distanceToLonDiff(givenDistance).add(givenLon));
    }
}