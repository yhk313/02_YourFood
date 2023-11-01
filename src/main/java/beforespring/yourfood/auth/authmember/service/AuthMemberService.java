package beforespring.yourfood.auth.authmember.service;


import beforespring.yourfood.auth.jwt.domain.AuthToken;
import beforespring.yourfood.auth.authmember.service.dto.ConfirmTokenDto.ConfirmTokenRequest;
import beforespring.yourfood.auth.authmember.service.dto.CreateMemberDto.CreateMemberRequest;
import beforespring.yourfood.auth.authmember.service.dto.PasswordAuth;
import beforespring.yourfood.auth.authmember.service.dto.RefreshTokenAuth;

public interface AuthMemberService {

    /**
     * 가입 요청. 가입 요청시 6자리의 랜덤 코드를 이메일로 발송. (이메일 발송 생략에 대해서 논의 필요)
     *
     * @param request 멤버 생성 요청 DTO
     * @return member id
     */
    Long join(CreateMemberRequest request);

    /**
     * 가입 승인
     *
     * @param request 토큰 승인 요청 DTO
     */
    void joinConfirm(ConfirmTokenRequest request);

    /**
     * username and password authentication
     * @param passwordAuth
     * @return Jwt
     */
    AuthToken authenticate(PasswordAuth passwordAuth);

    /**
     * refresh token authentication
     * @param refreshTokenAuth
     * @return Jwt
     */
    AuthToken authenticate(RefreshTokenAuth refreshTokenAuth);

}
