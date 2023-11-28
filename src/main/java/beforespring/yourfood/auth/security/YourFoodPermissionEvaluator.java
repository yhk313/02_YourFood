package beforespring.yourfood.auth.security;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

/**
 * 메서드 시큐리티 표현식 생성에 필요하여 선언한 클래스. 현재 자원 권한 판정에는 사용되지 않기 때문에 구현하지 않음.
 */
public class YourFoodPermissionEvaluator implements PermissionEvaluator {
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        throw new IllegalStateException("YourFoodPermissionEvaluator not implemented.");
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        throw new IllegalStateException("YourFoodPermissionEvaluator not implemented.");
    }
}
