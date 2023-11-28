package beforespring.yourfood.auth.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT Authentication 생성 이후에 실행되어야함.(Bearer Token 인증 필터 이후)
 * JwtAuthenticationToken 을 YourFoodJwtAuthenticationToken 으로 대체함.
 */
public class YourFoodJwtFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken) {
            SecurityContextHolder.getContext().setAuthentication(new YourFoodJwtAuthenticationToken((JwtAuthenticationToken) authentication));
        }
        filterChain.doFilter(request, response);
    }
}
