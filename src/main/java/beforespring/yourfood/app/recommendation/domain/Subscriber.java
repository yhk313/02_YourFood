package beforespring.yourfood.app.recommendation.domain;

import beforespring.yourfood.app.utils.Coordinates;
import com.querydsl.core.annotations.QueryProjection;

public record Subscriber(
        Long memberId,
        String username,
        Boolean notificationAgreed,
        Coordinates coordinates
) {
    @QueryProjection
    public Subscriber {
    }
}
