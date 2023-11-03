package beforespring.yourfood.web.api.member;
import beforespring.yourfood.web.api.member.request.UpdateUserSettingsRequest;
import beforespring.yourfood.web.api.common.GenericResponse;
import beforespring.yourfood.web.api.member.response.UpdateUserSettingResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/members")
public class MemberController {

    /**
     * 회원 설정 업데이트
     *
     * @param updateUserSettingsRequest 업데이트 정보
     * @return
     */
    @PatchMapping("/settings")
    public GenericResponse<UpdateUserSettingResponse> updateUserSettings(@RequestBody UpdateUserSettingsRequest updateUserSettingsRequest) {
        return null;
    }
}
