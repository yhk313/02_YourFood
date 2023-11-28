package beforespring.yourfood.app.recommendation.domain;

/**
 * 추천 기준 구현 등이 필요함.
 */
public interface Recommender {
    Recommendation getRecommendation(Subscriber subscriber);
}
