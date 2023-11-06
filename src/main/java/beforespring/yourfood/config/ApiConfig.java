package beforespring.yourfood.config;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class ApiConfig {
    @Value("${developer.api.key}")
    private String developerApiKey;

    @Bean
    public XmlMapper xmlMapper() {
        return new XmlMapper();
    }
}
