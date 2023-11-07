package beforespring.yourfood.app.utils;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

/**
 * 측지 좌표를 나타냄
 * 위도와 경도는 고정 소수, 정밀도 11, 소수점 이하 8 까지 나타냄
 * 북위 37도 기준 1도 당 111km, 동경 127도 기준 1도 당 90km로 환산함
 * 모든 거리 단위는 meter
 */
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Coordinates {

    private static final MathContext mc = new MathContext(11);

    @Column(precision = 11, scale = 8, columnDefinition = "DECIMAL(11,8)")
    private BigDecimal lat = BigDecimal.ZERO;
    @Column(precision = 11, scale = 8, columnDefinition = "DECIMAL(11,8)")
    private BigDecimal lon = BigDecimal.ZERO;

    public void setCoordinates(BigDecimal lat, BigDecimal lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public BigDecimal getLonPlusDistance(int distanceInMeters) {
        return lon.add(distanceToLonDiff(distanceInMeters));
    }

    public BigDecimal getLatPlusDistance(int distanceInMeters) {
        return lat.add(distanceToLatDiff(distanceInMeters));
    }


    /**
     * 현재 좌표와 인자의 좌표 사이의 거리를 구함
     *
     * @param destination 도착점
     * @return 두 좌표 사이의 거리(meter)
     */
    public BigInteger calculateDistance(Coordinates destination) {
        if (this.lon.equals(BigDecimal.ZERO) && this.lat.equals(BigDecimal.ZERO))
            throw new IllegalArgumentException("시작점이 설정되지 않았습니다.");
        return Coordinates.calculateDistance(this, destination);
    }

    /**
     * 두 좌표 사이의 거리를 구함
     *
     * @param origin      시작점
     * @param destination 도착점
     * @return 두 좌표 사이의 거리
     */
    public static BigInteger calculateDistance(Coordinates origin, Coordinates destination) {
        // 경도와 위도 차이 계산
        BigDecimal xDiff = destination.getLon().subtract(origin.getLon());
        BigDecimal yDiff = destination.getLat().subtract(origin.getLat());

        // 지측 좌표 변화량을 거리로 환산
        BigDecimal xDis = getMeterPerLongitude(xDiff);
        BigDecimal yDis = getMeterPerLatitude(yDiff);

        // 피타고리스의 정리로 직선거리 구함
        BigDecimal xDiffSquared = xDis.pow(2);
        BigDecimal yDiffSquared = yDis.pow(2);
        BigDecimal distance = xDiffSquared.add(yDiffSquared).sqrt(mc);

        return distance.toBigInteger();
    }

    /**
     * 거리에 따른 위도 차이 계산
     *
     * @param distance 거리
     * @return 위도 차이
     */
    public static BigDecimal distanceToLatDiff(Integer distance) {
        return new BigDecimal((distance.floatValue() / 111000), mc);
    }

    /**
     * 거리에 따른 경도 차이 계산
     *
     * @param distance 거리
     * @return 경도 차이
     */
    public static BigDecimal distanceToLonDiff(Integer distance) {
        return new BigDecimal(distance.floatValue() / 90000, mc);
    }

    /**
     * 위도 차이를 거리로 환산
     *
     * @param latDiff 위도 차이
     * @return 환산된 거리
     */
    public static BigDecimal getMeterPerLatitude(BigDecimal latDiff) {
        BigDecimal meterPerLat = new BigDecimal(111000);
        return latDiff.multiply(meterPerLat, mc);
    }

    /**
     * 경도 차이를 거리로 환산
     *
     * @param lonDiff 경도 차이
     * @return 환산된 거리
     */
    public static BigDecimal getMeterPerLongitude(BigDecimal lonDiff) {
        BigDecimal meterPerLon = new BigDecimal(90000);
        return lonDiff.multiply(meterPerLon, mc);
    }


}
