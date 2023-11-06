package beforespring.yourfood.app.member.service;

import beforespring.yourfood.app.member.domain.Member;
import beforespring.yourfood.app.member.domain.MemberRepository;
import beforespring.yourfood.app.utils.Coordinates;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    /**
     *점심 추천 기능 동의 설정 업데이트
     *@param lunchRecommendationConsent 점심 추천 기능 동의
     *@param memberId 사용자Id
     */
    @Override
    public void updateLunchRecommendationConsent(boolean lunchRecommendationConsent, Long memberId) {
        Member member = memberRepository.getReferenceById(memberId);
        member.updateLunchNotiStatus(lunchRecommendationConsent, LocalDateTime.now());
    }

    /**
     *사용자 위치 업데이트
     *@param lat 위도
     *@param lon 경도
     *@param memberId 사용자Id
     */
    @Override
    public void updateLocation(String lat, String lon, Long memberId) {
        Member member = memberRepository.getReferenceById(memberId);
        Coordinates coordinate = new Coordinates(new BigDecimal(lat), new BigDecimal(lon));
        member.updateMemberLocation(coordinate);
    }

    /**
     *사용자를 생성하고 저장
     *@param username username
     *@return 생성된 사용자Id
     */
    @Override
    public Long createMember(String username) {
        Member member = Member.builder()
            .username(username).build();
        memberRepository.save(member);
        return member.getId();
    }
}
