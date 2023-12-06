package beforespring.yourfood.app.recommendation.domain;


import beforespring.yourfood.app.restaurant.service.dto.CuisineGroup;

import java.util.List;

public record Recommendation(
        Subscriber subscriber,
        List<CuisineGroup> recommendation
) {
}
