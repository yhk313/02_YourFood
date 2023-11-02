package beforespring.yourfood.web.api.member.request;

public record UpdateUserSettingsRequest(boolean lunchRecommendationConsent, String lat, String lon) {
}
