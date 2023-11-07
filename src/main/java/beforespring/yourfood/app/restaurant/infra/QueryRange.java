package beforespring.yourfood.app.restaurant.infra;

import beforespring.yourfood.app.utils.Coordinates;
import java.math.BigDecimal;

public record QueryRange(
    BigDecimal latFrom,
    BigDecimal latTo,
    BigDecimal lonFrom,
    BigDecimal lonTo
) {
    static public QueryRange from(Coordinates coordinates, int rangeInMeters) {
        BigDecimal latTo = coordinates.getLatPlusDistance(rangeInMeters);
        BigDecimal latFrom = coordinates.getLatPlusDistance(-rangeInMeters);
        BigDecimal lonTo = coordinates.getLonPlusDistance(rangeInMeters);
        BigDecimal lonFrom = coordinates.getLonPlusDistance(-rangeInMeters);
        return new QueryRange(latFrom, latTo, lonFrom, lonTo);
    }
}
