package beforespring.yourfood.batch.rawrestaurant.mapping;

import beforespring.yourfood.batch.rawrestaurant.mapping.exception.MapperProcessingException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class OpenApiManager {
    private final String url;
    private final XmlMapper mapper;

    private final RestTemplate restTemplate;
    public Genrestrt fetch() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        Genrestrt data;

        try {
            data = mapper.readValue(responseEntity.getBody(), Genrestrt.class);
        } catch (JsonProcessingException e) {
            throw new MapperProcessingException(e);
        }
        return data;
    }

    public OpenApiManager(String url, XmlMapper mapper, RestTemplate restTemplate) {
        this.url = url;
        this.mapper = mapper;
        this.restTemplate = restTemplate;
    }
}
