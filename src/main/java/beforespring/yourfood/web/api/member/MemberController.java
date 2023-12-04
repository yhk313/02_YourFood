package beforespring.yourfood.web.api.member;

import beforespring.yourfood.app.member.service.MemberService;
import beforespring.yourfood.web.api.common.StatusCode;
import beforespring.yourfood.web.api.member.request.UpdateLocationRequest;
import beforespring.yourfood.web.api.member.request.UpdateLunchRecommendationConsent;
import beforespring.yourfood.web.api.common.GenericResponse;
import beforespring.yourfood.web.api.member.response.UpdateUserSettingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    /**
     * 회원 점심 추천 기능 동의 설정 업데이트
     *
     * @param request 업데이트 정보
     */
    @PreAuthorize("idMatches(#request.memberId())")
    @PatchMapping("/lunch-recommendation-consent")
    public GenericResponse<UpdateUserSettingResponse> updateLunchRecommendation(
        @RequestBody UpdateLunchRecommendationConsent request
    ) {

        memberService.updateLunchRecommendationConsent(
            request.consent(),
            request.memberId()
        );

        return GenericResponse.<UpdateUserSettingResponse>builder()
            .statusCode(StatusCode.CREATED)
            .message("Success")
            .build();
    }
    /**
     * 회원 위치 업데이트
     *
     * @param request 업데이트 정보
     */
    @PreAuthorize("idMatches(#request.memberId())")
    @PatchMapping("/location")
    public GenericResponse<UpdateUserSettingResponse> updateLocation(
        @RequestBody UpdateLocationRequest request
    ) {

        memberService.updateLocation(request.lat(), request.lon(), request.memberId());

        return GenericResponse.<UpdateUserSettingResponse>builder()
            .statusCode(StatusCode.CREATED)
            .message("Success")
            .build();
    }

}
