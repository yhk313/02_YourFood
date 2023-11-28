package beforespring.yourfood.app.recommendation.domain;

public interface RecommendationNotifier {
    void notify(Subscriber subscriber, Recommendation recommendation);
}
