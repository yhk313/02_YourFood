package beforespring.yourfood.app.member.service;

public interface MemberService {
    void updateLunchRecommendation(boolean lunchRecommendationConsent, Long memberId);
    void updateLocation(String lat, String lon, Long memberId);
    Long createMember(String username);
}
