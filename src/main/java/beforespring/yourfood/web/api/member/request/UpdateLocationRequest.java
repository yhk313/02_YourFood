package beforespring.yourfood.web.api.member.request;

import java.math.BigDecimal;

public record UpdateLocationRequest(BigDecimal lat, BigDecimal lon, Long memberId) {
}
