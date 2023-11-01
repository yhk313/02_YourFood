package beforespring.yourfood.web.request.member;

public record UpdateUserSettingsRequest(boolean lunchRecommendationConsent, String lat, String lon) {
}
