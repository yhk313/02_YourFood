package beforespring.yourfood.app.member.service;

import beforespring.yourfood.app.member.domain.Member;
import beforespring.yourfood.app.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    @Override
    public void updateLunchRecommendation(boolean lunchRecommendationConsent, Long memberId) {
    }

    @Override
    public void updateLocation(String lat, String lon, Long memberId) {

    }

    @Override
    public Long createMember(String username) {
        Member member = Member.builder()
            .username(username).build();
        memberRepository.save(member);
        return member.getId();
    }
}
