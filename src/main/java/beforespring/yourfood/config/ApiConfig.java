package beforespring.yourfood.config;

import beforespring.yourfood.app.restaurant.domain.CuisineType;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Getter
public class ApiConfig {
    @Value("${developer.api.key}")
    private String developerApiKey;

    @Bean
    public Map<CuisineType, String> cuisineTypeMap() {
        Map<CuisineType, String> cuisineTypeNames = new HashMap<>();
        cuisineTypeNames.put(CuisineType.KOREAN, "/Genrestrtlunch");
        cuisineTypeNames.put(CuisineType.CAFE, "/Genrestrtcate");
        cuisineTypeNames.put(CuisineType.FUGU, "/Genrestrtfugu");
        cuisineTypeNames.put(CuisineType.SASHIMI, "/Genrestrtsash");
        cuisineTypeNames.put(CuisineType.BUFFET, "/Genrestrtbuff");
        cuisineTypeNames.put(CuisineType.FASTFOOD, "/Genrestrtfastfood");
        cuisineTypeNames.put(CuisineType.CHINESE, "/Genrestrtchifood");
        cuisineTypeNames.put(CuisineType.JAPANESE, "/Genrestrtjpnfood");
        cuisineTypeNames.put(CuisineType.SOUP, "/Genrestrtsoup");
        return cuisineTypeNames;
    }

    @Bean
    public XmlMapper xmlMapper() {
        return new XmlMapper();
    }
}
