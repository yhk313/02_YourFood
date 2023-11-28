package beforespring.yourfood.auth.security;

import lombok.RequiredArgsConstructor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;

@RequiredArgsConstructor
public class YourFoodMethodSecurityExpressionHandler extends DefaultMethodSecurityExpressionHandler {
    private final AuthenticationTrustResolver trustResolver = new AuthenticationTrustResolverImpl();
    @Override
    protected MethodSecurityExpressionOperations createSecurityExpressionRoot(Authentication authentication, MethodInvocation invocation) {
        YourFoodMethodSecurityExpressionRoot expressionRoot = new YourFoodMethodSecurityExpressionRoot(authentication);
        expressionRoot.setPermissionEvaluator(getPermissionEvaluator());
        expressionRoot.setTrustResolver(this.trustResolver);
        expressionRoot.setRoleHierarchy(getRoleHierarchy());
        return expressionRoot;
    }
}
