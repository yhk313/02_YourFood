package beforespring.yourfood.app.recommendation.config;

import beforespring.yourfood.app.recommendation.domain.Recommender;
import beforespring.yourfood.app.recommendation.infra.RecommenderImpl;
import beforespring.yourfood.app.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
@RequiredArgsConstructor
@Slf4j
public class RecommenderConfig {
    private final RestaurantService restaurantService;

    @Value("${beforespring.recommendation.rangeInMeters}")
    private int rangeInMeters;

    @Value("${beforespring.recommendation.limitByCuisineType}")
    private int limitByCuisineType;

    @Bean
    public Recommender recommender() {
        return new RecommenderImpl(restaurantService, rangeInMeters, limitByCuisineType);
    }
}
