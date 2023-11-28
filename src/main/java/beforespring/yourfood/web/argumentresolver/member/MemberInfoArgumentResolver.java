package beforespring.yourfood.web.argumentresolver.member;

import beforespring.yourfood.auth.security.YourFoodAuthentication;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


/**
 * Jwt 인증시, 토큰의 payload를 읽어 Member 정보를 반환하는 클래스
 *
 * @see ResolveMemberInfo
 * @see MemberInfo
 */
public class MemberInfoArgumentResolver implements HandlerMethodArgumentResolver {
    private MemberInfo convert(YourFoodAuthentication authentication) {
        return new MemberInfo(authentication.getMemberId(), authentication.getAuthMemberId(), authentication.getName());
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(ResolveMemberInfo.class);
    }

    /**
     * @param parameter     the method parameter to resolve. This parameter must
     *                      have previously been passed to {@link #supportsParameter} which must
     *                      have returned {@code true}.
     * @param mavContainer  the ModelAndViewContainer for the current request
     * @param webRequest    the current request
     * @param binderFactory a factory for creating {@link WebDataBinder} instances
     * @return {@link MemberInfo}
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            throw new MemberInfoResolverException("Authentication is null.");
        }

        if (!(authentication instanceof YourFoodAuthentication)) {
            throw new MemberInfoResolverException("authentication should be instance of YourFoodAuthentication. check security filter chain configuration. authentication class=" + authentication.getClass());
        }

        return convert((YourFoodAuthentication) authentication);
    }
}
