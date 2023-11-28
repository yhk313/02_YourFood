package beforespring.yourfood.app.recommendation.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecommendationDomainService {
    private final Recommender recommender;
    private final RecommendationNotifier notifier;
    public void recommend(Subscriber subscriber) {
        notifier.notify(subscriber, recommender.getRecommendation(subscriber));
    }
}
