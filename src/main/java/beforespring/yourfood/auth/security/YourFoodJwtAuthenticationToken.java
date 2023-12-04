package beforespring.yourfood.auth.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import javax.security.auth.Subject;
import java.util.Collection;
import java.util.Map;

/**
 * JwtAuthenticationToken 을 감싸는 프록시 클래스. JwtAuthenticationToken 에 YourFoodAuthentication 인터페이스를 추가함.
 */
public class YourFoodJwtAuthenticationToken extends JwtAuthenticationToken implements YourFoodAuthentication {
    private final Long memberId;
    private final Long authMemberId;
    private final String username;
    private final JwtAuthenticationToken target;

    public YourFoodJwtAuthenticationToken(JwtAuthenticationToken target) {
        super(target.getToken());
        Map<String, Object> attributes = target.getTokenAttributes();
        this.memberId = Long.parseLong(attributes.get("memberId").toString());
        this.authMemberId = Long.parseLong(attributes.get("authId").toString());
        this.username = (String) attributes.get("username");
        this.target = target;
    }

    @Override
    public Long getMemberId() {
        return memberId;
    }

    @Override
    public Long getAuthMemberId() {
        return authMemberId;
    }

    @Override
    public String getUsername() {
        return username;
    }


    @Override
    public Object getPrincipal() {
        return target.getPrincipal();
    }

    @Override
    public Object getCredentials() {
        return target.getCredentials();
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return target.getAuthorities();
    }

    @Override
    public boolean isAuthenticated() {
        return target.isAuthenticated();
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        target.setAuthenticated(authenticated);
    }

    @Override
    public Object getDetails() {
        return target.getDetails();
    }

    @Override
    public void setDetails(Object details) {
        target.setDetails(details);
    }

    @Override
    public void eraseCredentials() {
        target.eraseCredentials();
    }

    @Override
    public boolean equals(Object obj) {
        return this.equals(obj);
    }

    @Override
    public int hashCode() {
        return target.hashCode();
    }

    @Override
    public String toString() {
        return target.toString();
    }

    @Override
    public boolean implies(Subject subject) {
        return target.implies(subject);
    }
}
