package beforespring.yourfood.app.recommendation.infra;

import beforespring.yourfood.app.recommendation.domain.Recommendation;
import beforespring.yourfood.app.recommendation.domain.RecommendationNotifier;
import beforespring.yourfood.app.recommendation.domain.Subscriber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DiscordRecommendationNotifier implements RecommendationNotifier {
    @Override
    public void notify(Subscriber subscriber, Recommendation recommendation) {
        log.info("notified. subscriber={} recommendation={}", subscriber, recommendation);
        log.warn("DiscordRecommendationNotifier has not been implemented!");
    }
}
