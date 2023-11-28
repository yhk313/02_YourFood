package beforespring.yourfood.app.recommendation.infra;

import beforespring.yourfood.app.recommendation.domain.Recommendation;
import beforespring.yourfood.app.recommendation.domain.Recommender;
import beforespring.yourfood.app.recommendation.domain.Subscriber;
import org.springframework.stereotype.Component;

/**
 * 빈 주입을 위해 임시로 만들어둔 클래스.
 */
@Component
public class RecommenderImpl implements Recommender {
    @Override
    public Recommendation getRecommendation(Subscriber subscriber) {
        return null;
    }
}
