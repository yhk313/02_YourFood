package beforespring.yourfood.web.api.member.request;

public record UpdateLunchRecommendationConsent(
    boolean consent,
    Long memberId
) {
}
