package beforespring.yourfood.config;

import beforespring.yourfood.auth.security.YourFoodMethodSecurityExpressionHandler;
import beforespring.yourfood.auth.security.YourFoodPermissionEvaluator;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {
    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        YourFoodMethodSecurityExpressionHandler expressionHandler = new YourFoodMethodSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(new YourFoodPermissionEvaluator());
        return expressionHandler;
    }
}
