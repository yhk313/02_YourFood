package beforespring.yourfood.web.api.auth;

import beforespring.yourfood.auth.authmember.service.AuthMemberService;
import beforespring.yourfood.auth.authmember.service.dto.ConfirmTokenDto;
import beforespring.yourfood.auth.authmember.service.dto.PasswordAuth;
import beforespring.yourfood.auth.authmember.service.dto.RefreshTokenAuth;
import beforespring.yourfood.auth.jwt.domain.AuthToken;
import beforespring.yourfood.web.api.common.GenericResponse;
import beforespring.yourfood.web.api.member.request.SignupMemberRequest;
import beforespring.yourfood.web.api.member.response.SignupResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class AuthController {

    private final AuthMemberService authMemberService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GenericResponse<SignupResponse> registerMember(
        @RequestBody SignupMemberRequest signupMemberRequest
    ) {
        Long memberId = authMemberService.join(signupMemberRequest);

        SignupResponse response = new SignupResponse(memberId);

        return GenericResponse.ok(response);
    }

    @PostMapping("/auth")
    public GenericResponse<AuthToken> authWithUsernamePassword(
        @RequestBody PasswordAuth passwordAuth
    ) {
        AuthToken authToken = authMemberService.authenticate(passwordAuth);
        return GenericResponse.ok(authToken);
    }

    @PostMapping("/confirm")
    public GenericResponse<AuthToken> confirm(
        @RequestBody ConfirmTokenDto.ConfirmTokenRequest request
    ) {
        authMemberService.joinConfirm(request);
        return GenericResponse.ok();
    }

    @PostMapping("/renew")
    public GenericResponse<AuthToken> authWithUsernamePassword(
        @RequestBody RefreshTokenAuth refreshTokenAuth
    ) {

        AuthToken authToken = authMemberService.authenticate(refreshTokenAuth);
        return GenericResponse.ok(authToken);
    }
}
