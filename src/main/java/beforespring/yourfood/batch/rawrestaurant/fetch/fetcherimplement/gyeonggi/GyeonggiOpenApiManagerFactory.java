package beforespring.yourfood.batch.rawrestaurant.fetch.fetcherimplement.gyeonggi;

import beforespring.yourfood.app.restaurant.domain.CuisineType;
import beforespring.yourfood.batch.rawrestaurant.fetch.fetcherimplement.OpenApiManager;
import beforespring.yourfood.batch.rawrestaurant.fetch.fetcherimplement.PagingQueryHttpUriBase;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

public class GyeonggiOpenApiManagerFactory {
    private final Map<CuisineType, String> cuisineTypePathMap;
    private final RestTemplate restTemplate;
    private final XmlMapper mapper;
    private final URI baseUri;

    private URI getUriWithCuisineType(CuisineType cuisineType) {
        return UriComponentsBuilder.fromUri(baseUri)
                .path(cuisineTypePathMap.get(cuisineType))
                .build()
                .toUri();
    }

    public OpenApiManager createOpenApiManager(CuisineType cuisineType) {
        PagingQueryHttpUriBase pagingQueryHttpUriBase = new PagingQueryHttpUriBase(getUriWithCuisineType(cuisineType), "pSize", "pIndex");
        return new OpenApiManager(pagingQueryHttpUriBase, restTemplate);
    }

    public GyeonggiOpenApiManagerFactory(Map<CuisineType, String> cuisineTypePathMap, RestTemplate restTemplate, XmlMapper mapper, String devKey) {
        this.cuisineTypePathMap = cuisineTypePathMap;
        this.restTemplate = restTemplate;
        this.mapper = mapper;
        this.baseUri = UriComponentsBuilder
                        .fromHttpUrl("https://openapi.gg.go.kr")
                        .queryParam("KEY", devKey)
                        .build()
                        .toUri();
    }
}
