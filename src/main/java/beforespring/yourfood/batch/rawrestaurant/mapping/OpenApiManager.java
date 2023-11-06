package beforespring.yourfood.batch.rawrestaurant.mapping;

import beforespring.yourfood.batch.rawrestaurant.mapping.exception.MapperProcessingException;
import beforespring.yourfood.config.ApiConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@Slf4j
@Setter
@RequiredArgsConstructor
public class OpenApiManager {
    private String baseUrl = "https://openapi.gg.go.kr";
    private String apiUrl = "/Genrestrtlunch";

    private final ApiConfig apiConfig;

    private final XmlMapper mapper;

    public Genrestrt fetch(int page, int pageSize) {
        String url = makeUrl(page, pageSize);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        Genrestrt lunch;

        try {
            lunch = mapper.readValue(responseEntity.getBody(), Genrestrt.class);
        } catch (JsonProcessingException e) {
            throw new MapperProcessingException(e);
        }
        return lunch;
    }

    private String makeUrl(int page, int pageSize) {
        return UriComponentsBuilder.fromHttpUrl(baseUrl)
                   .path(apiUrl)
                   .queryParam("KEY", apiConfig.getDeveloperApiKey())
                   .queryParam("pIndex", page)
                   .queryParam("pSize", pageSize)
                   .toUriString();
    }
}
