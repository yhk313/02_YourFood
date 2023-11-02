package beforespring.yourfood.app.utils;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
public class Coordinate {
    @Column(nullable = false, precision = 11, scale = 8, columnDefinition = "DECIMAL(11,8)")
    private BigDecimal lat;
    @Column(nullable = false ,precision = 11, scale = 8, columnDefinition = "DECIMAL(11,8)")
    private BigDecimal lon;
}
