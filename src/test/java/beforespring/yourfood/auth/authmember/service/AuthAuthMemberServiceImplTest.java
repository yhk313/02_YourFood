package beforespring.yourfood.auth.authmember.service;

import static beforespring.Fixture.randString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import beforespring.yourfood.auth.jwt.domain.AuthToken;
import beforespring.yourfood.auth.authmember.domain.Confirm;
import beforespring.yourfood.auth.authmember.domain.ConfirmRepository;
import beforespring.yourfood.auth.authmember.domain.ConfirmStatus;
import beforespring.yourfood.auth.authmember.domain.AuthMember;
import beforespring.yourfood.auth.authmember.domain.AuthMemberRepository;
import beforespring.yourfood.auth.authmember.domain.PasswordHasher;
import beforespring.yourfood.auth.authmember.domain.TokenSender;
import beforespring.yourfood.auth.authmember.exception.PasswordMismatchException;
import beforespring.yourfood.auth.authmember.exception.TokenMismatchException;
import beforespring.yourfood.auth.authmember.infra.TokenSenderImpl;
import beforespring.yourfood.auth.authmember.service.dto.ConfirmTokenDto.ConfirmTokenRequest;
import beforespring.yourfood.auth.authmember.service.dto.PasswordAuth;
import beforespring.yourfood.auth.authmember.service.dto.RefreshTokenAuth;
import beforespring.yourfood.auth.authmember.service.exception.ConfirmNotFoundException;
import beforespring.yourfood.auth.authmember.service.exception.AuthMemberNotFoundException;
import java.util.List;
import javax.persistence.EntityManager;

import beforespring.yourfood.web.api.member.request.SignupMemberRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class AuthAuthMemberServiceImplTest {

    @Autowired
    AuthMemberService authMemberService;
    @Autowired
    AuthMemberRepository authMemberRepository;
    @Autowired
    ConfirmRepository confirmRepository;
    @Autowired
    PasswordHasher passwordHasher;
    @Autowired
    EntityManager em;

    private SignupMemberRequest signupMemberRequest;
    private ConfirmTokenRequest confirmTokenRequest;


    @BeforeEach
    public void setup() {
        String givenUsername = "givenUsername";
        String givenEmail = "givenEmail@gmail.com";
        String givenPassword = "passwdThatShou!dBe0kay";

        signupMemberRequest = new SignupMemberRequest(givenUsername, givenEmail, givenPassword);
    }

    @Test
    @DisplayName("회원가입한 유저의 이름이 저장된 이름과 동일해야 함.")
    void member_join_test() {

        //when
        Long authMemberId = authMemberService.join(signupMemberRequest);
        AuthMember findAuthMember = authMemberRepository.findById(authMemberId).orElseThrow(
            AuthMemberNotFoundException::new);

        //then
        assertThat(findAuthMember.getUsername()).isEqualTo(signupMemberRequest.getUsername());
    }

    @Test
    @DisplayName("confirm에 6자리 승인코드가 저장되어야 함.")
    void create_confirm_test() {

        //when
        Long authMemberId = authMemberService.join(signupMemberRequest);
        AuthMember findAuthMember = authMemberRepository.findById(authMemberId).orElseThrow(
            AuthMemberNotFoundException::new);
        Confirm findConfirm = confirmRepository.findByAuthMember(findAuthMember).orElseThrow(
            ConfirmNotFoundException::new);

        //then
        assertThat(findConfirm.getToken().length()).isEqualTo(6);
    }

    @Test
    @DisplayName("가입 승인 요청의 토큰과 저장된 토큰이 일치하면 멤버가 승인 상태가 되어야 합니다.")
    void join_confirm_test() {
        //given
        Long authMemberId = authMemberService.join(signupMemberRequest);
        AuthMember findAuthMember = authMemberRepository.findById(authMemberId).orElseThrow(
            AuthMemberNotFoundException::new);
        Confirm findConfirm = confirmRepository.findByAuthMember(findAuthMember).orElseThrow(ConfirmNotFoundException::new);

        //when
        String token = findConfirm.getToken();
        confirmTokenRequest = new ConfirmTokenRequest(signupMemberRequest.getUsername(), signupMemberRequest.getPassword(), token);
        authMemberService.joinConfirm(confirmTokenRequest);

        //then
        assertThat(findAuthMember.getStatus()).isEqualTo(ConfirmStatus.AUTHORIZED);
    }

    @Test
    @DisplayName("가입 승인 요청의 토큰과 저장된 토큰이 일치하지 않으면 가입 승인 요청이 실패해야 합니다.")
    void join_confirm_deny_test() {
        //given
        authMemberService.join(signupMemberRequest);
        TokenSender tokenSender = new TokenSenderImpl();

        //when //then
        String newToken = tokenSender.generateToken();
        confirmTokenRequest = new ConfirmTokenRequest(signupMemberRequest.getUsername(), signupMemberRequest.getPassword(), newToken);

        assertThatThrownBy(() -> authMemberService.joinConfirm(confirmTokenRequest))
            .isInstanceOf(TokenMismatchException.class)
            .describedAs("인증 토큰이 일치하지 않으면 가입 요청에 실패해야 합니다.");
    }

    @Test
    @DisplayName("가입 승인 요청 시 저장된 계정이 없으면 가입 승인 요청이 실패해야 합니다.")
    void join_confirm_no_user_test() {
        //given
        Long authMemberId = authMemberService.join(signupMemberRequest);
        AuthMember findAuthMember = authMemberRepository.findById(authMemberId).orElseThrow(
            AuthMemberNotFoundException::new);
        Confirm findConfirm = confirmRepository.findByAuthMember(findAuthMember).orElseThrow(ConfirmNotFoundException::new);
        String token = findConfirm.getToken();

        //when //then
        String wrongUsername = "aaa";
        confirmTokenRequest = new ConfirmTokenRequest(wrongUsername, signupMemberRequest.getPassword(), token);

        assertThatThrownBy(() -> authMemberService.joinConfirm(confirmTokenRequest))
            .isInstanceOf(AuthMemberNotFoundException.class)
            .describedAs("계정이 일치하지 않으면 가입 요청에 실패해야 합니다.");

    }

    @Test
    @DisplayName("가입 승인 요청 시 저장된 멤버와 비밀번호가 일치하지 않으면 가입 승인 요청이 실패해야 합니다.")
    void join_confirm_mismatch_password_test() {
        //given
        Long memberId = authMemberService.join(signupMemberRequest);
        AuthMember findAuthMember = authMemberRepository.findById(memberId).orElseThrow(
            AuthMemberNotFoundException::new);
        Confirm findConfirm = confirmRepository.findByAuthMember(findAuthMember).orElseThrow(ConfirmNotFoundException::new);
        String token = findConfirm.getToken();

        //when //then
        String wrongPassword = "aaa";
        confirmTokenRequest = new ConfirmTokenRequest(signupMemberRequest.getUsername(), wrongPassword, token);

        assertThatThrownBy(() -> authMemberService.joinConfirm(confirmTokenRequest))
            .isInstanceOf(PasswordMismatchException.class)
            .describedAs("비밀번호가 일치하지 않으면 가입 요청에 실패해야 합니다.");
    }

    void initAuthMember(String username, String password, Long yourFoodId) {
        AuthMember givenAuthMember = AuthMember.builder()
                                         .username(username)
                                         .raw(password)
                                         .hasher(passwordHasher)
                                         .build();
        givenAuthMember.updateYourFoodId(yourFoodId);
        authMemberRepository.save(givenAuthMember);
        em.flush();
        em.clear();
    }

    @Test
    @DisplayName("인증 성공시 JWT 발급")
    void authenticate_password() {
        // given
        String givenUsername = randString();
        String givenPassword = randString();
        Long givenYourFoodId = 10L;

        initAuthMember(givenUsername, givenPassword, givenYourFoodId);

        // when
        AuthToken res = authMemberService.authenticate(
            new PasswordAuth(givenUsername, givenPassword));

        assertThat(res).isNotNull();
        assertThat(List.of(res.refreshToken(), res.accessToken()))
            .doesNotContainNull();
    }

    @Test
    @DisplayName("refresh token 인증")
    void authenticate_refresh_token() {
        // given
        String givenUsername = randString();
        String givenPassword = randString();

        Long givenYourFoodId = 10L;

        initAuthMember(givenUsername, givenPassword, givenYourFoodId);

        AuthToken authenticate = authMemberService.authenticate(
            new PasswordAuth(givenUsername, givenPassword));

        // when
        AuthToken res = authMemberService.authenticate(
            new RefreshTokenAuth(givenUsername, authenticate.refreshToken()));

        // then
        assertThat(res).isNotNull();
        assertThat(List.of(res.refreshToken(), res.accessToken()))
            .doesNotContainNull();
    }
}