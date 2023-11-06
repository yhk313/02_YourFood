package beforespring.yourfood.app.member.service;

import beforespring.yourfood.app.member.domain.Member;
import beforespring.yourfood.app.member.domain.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class MemberServiceImplTest {
    @InjectMocks
    private MemberServiceImpl memberService;

    @Mock
    private MemberRepository memberRepository;

    @Test
    @DisplayName("사용자 점심추천기능동의 업데이트 테스트")
    public void testUpdateLunchRecommendationConsent() {
        Long memberId = 1L;
        boolean lunchRecommendationConsent = true;
        Member member = Member.builder().build();

        when(memberRepository.getReferenceById(memberId)).thenReturn(member);

        memberService.updateLunchRecommendationConsent(lunchRecommendationConsent, memberId);

        assertTrue(member.isLunchNotiStatus());
    }

    @Test
    @DisplayName("사용자 위치 업데이트 테스트")
    public void testUpdateLocation() {
        Long memberId = 1L;
        Member member = Member.builder()
            .username("Test username")

            .build();
        when(memberRepository.getReferenceById(memberId)).thenReturn(member);

        memberService.updateLocation("37.12222", "128.95555", memberId);

        assertEquals(new BigDecimal("37.12222"), member.getCoordinates().getLat());
        assertEquals(new BigDecimal("128.95555"), member.getCoordinates().getLon());
    }

}