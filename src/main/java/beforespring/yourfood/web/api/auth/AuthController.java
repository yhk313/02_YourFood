package beforespring.yourfood.web.api.auth;

import beforespring.yourfood.auth.authmember.service.AuthMemberService;
import beforespring.yourfood.auth.authmember.service.dto.PasswordAuth;
import beforespring.yourfood.auth.authmember.service.dto.RefreshTokenAuth;
import beforespring.yourfood.auth.jwt.domain.AuthToken;
import beforespring.yourfood.web.api.common.GenericResponse;
import beforespring.yourfood.web.api.common.StatusCode;
import beforespring.yourfood.web.api.member.request.SignupMemberRequest;
import beforespring.yourfood.web.api.member.response.SignupResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthMemberService authMemberService;
    /**
     * 회원가입
     *
     * @param signupMemberRequest
     * @return
     */
    @PostMapping("/api/v1/members")
    public GenericResponse<SignupResponse> registerMember(@RequestBody SignupMemberRequest signupMemberRequest) {
        Long memberId = authMemberService.join(signupMemberRequest);
        SignupResponse signupResponse = SignupResponse.builder()
            .id(memberId)
            .build();

        return GenericResponse.<SignupResponse>builder()
            .statusCode(StatusCode.CREATED)
            .message("Success")
            .data(signupResponse).build();

    }

    @PostMapping("/api/v1/member/auth")
    public GenericResponse<AuthToken> authWithUsernamePassword(@RequestBody PasswordAuth passwordAuth) {
        AuthToken authToken = authMemberService.authenticate(passwordAuth);
        return GenericResponse.<AuthToken>builder()
            .statusCode(StatusCode.OK)
            .message("Success")
            .data(authToken).
            build();

    }

    @PostMapping("/api/v1/member/renew")
    public GenericResponse<AuthToken> authWithUsernamePassword(@RequestBody RefreshTokenAuth refreshTokenAuth) {
        AuthToken authToken = authMemberService.authenticate(refreshTokenAuth);
        GenericResponse genericResponse = GenericResponse.builder()
            .statusCode(StatusCode.OK)
            .message("success")
            .data(authToken)
            .build();
        return genericResponse;
    }

}
