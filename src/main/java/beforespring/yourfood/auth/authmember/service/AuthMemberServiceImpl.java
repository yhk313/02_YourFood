package beforespring.yourfood.auth.authmember.service;

import beforespring.yourfood.app.member.service.MemberService;
import beforespring.yourfood.auth.authmember.service.dto.ConfirmTokenDto;
import beforespring.yourfood.auth.authmember.service.dto.PasswordAuth;
import beforespring.yourfood.auth.authmember.service.dto.PasswordPatternChecker;
import beforespring.yourfood.auth.authmember.service.dto.RefreshTokenAuth;
import beforespring.yourfood.auth.authmember.service.exception.ConfirmNotFoundException;
import beforespring.yourfood.auth.jwt.domain.AuthToken;
import beforespring.yourfood.auth.jwt.service.JwtIssuer;
import beforespring.yourfood.auth.authmember.domain.Confirm;
import beforespring.yourfood.auth.authmember.domain.ConfirmRepository;
import beforespring.yourfood.auth.authmember.domain.AuthMember;
import beforespring.yourfood.auth.authmember.domain.AuthMemberRepository;
import beforespring.yourfood.auth.authmember.domain.PasswordHasher;
import beforespring.yourfood.auth.authmember.domain.TokenSender;
import beforespring.yourfood.auth.authmember.service.exception.AuthMemberNotFoundException;
import beforespring.yourfood.web.api.member.request.SignupMemberRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class AuthMemberServiceImpl implements AuthMemberService {

    private final JwtIssuer jwtIssuer;

    private final AuthMemberRepository authMemberRepository;
    private final ConfirmRepository confirmRepository;
    private final PasswordHasher passwordHasher;
    private final TokenSender tokenSender;
    private final TransactionTemplate transactionTemplate;
    private final PasswordPatternChecker passwordPatternChecker;
    private final MemberService memberService;

    @Override
    public Long join(SignupMemberRequest request) {
        String password = request.getPassword();

        // 비밀번호 유효성 검사
        passwordPatternChecker.checkPasswordPattern(password);

        String token = tokenSender.generateToken();
        Long memberId = transactionTemplate.execute(joinTransaction(request, token));

        tokenSender.sendEmail(request.getEmail(), token);

        return memberId;
    }

    private TransactionCallback<Long> joinTransaction(SignupMemberRequest request, String token) {
        return transactionStatus -> {
            AuthMember authMember = AuthMember.builder()
                .username(request.getUsername())
                .raw(request.getPassword())
                .hasher(passwordHasher)
                .build();
            authMemberRepository.save(authMember);
            Confirm confirm = Confirm.builder()
                .authMember(authMember)
                .token(token)
                .build();
            confirmRepository.save(confirm);

            memberService.createMember(request.getUsername());
            return authMember.getId();
        };
    }

    @Override
    @Transactional
    public void joinConfirm(ConfirmTokenDto.ConfirmTokenRequest request) {
        AuthMember authMember = authMemberRepository.findByUsername(request.getUsername()).orElseThrow(
            AuthMemberNotFoundException::new);
        Confirm confirm = confirmRepository.findByAuthMember(authMember).orElseThrow(
            ConfirmNotFoundException::new);
        authMember.verifyPassword(request.getPassword(), passwordHasher);
        confirm.verifyToken(request.getToken());
        authMember.joinConfirm();
    }

    @Override
    public AuthToken authenticate(PasswordAuth passwordAuth) {
        AuthMember authMember = authMemberRepository.findByUsername(passwordAuth.username())
            .orElseThrow(AuthMemberNotFoundException::new);
        authMember.verifyPassword(passwordAuth.password(), passwordHasher);

        return jwtIssuer.issue(authMember.getId(), authMember.getUsername());
    }

    @Override
    public AuthToken authenticate(RefreshTokenAuth refreshTokenAuth) {
        return jwtIssuer.renew(refreshTokenAuth.refreshToken(), refreshTokenAuth.username());
    }
}
