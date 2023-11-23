package beforespring.yourfood.app.review.domain.event;

import lombok.Getter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Validated
public class ReviewCreatedEvent {
    private final Long restaurantId;
    private final BigDecimal rating;

    public ReviewCreatedEvent(@NotBlank Long restaurantId, @NotBlank Integer rating) {
        this.restaurantId = restaurantId;
        this.rating = BigDecimal.valueOf(rating).setScale(5, RoundingMode.HALF_UP);
    }
}
