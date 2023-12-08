package beforespring.yourfood.app.member.service;

import java.math.BigDecimal;

public interface MemberService {
    void updateLunchRecommendationConsent(boolean lunchRecommendationConsent, Long memberId);
    void updateLocation(BigDecimal lat, BigDecimal lon, Long memberId);
    Long createMember(String username);
}
