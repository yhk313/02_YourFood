package beforespring.yourfood.batch.rawrestaurant.fetch.fetcherimplement;

import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class OpenApiManager {
    private final PagingQueryHttpUriBase pagingQueryHttpUriBase;
    private final RestTemplate restTemplate;

    @SneakyThrows
    public ResponseEntity<String> fetch(int page, int pageSize) {
        // 간혹 XML 응답 양식 공공API ContentType 헤더가 JSON으로 설정된 경우가 있어서 문자열로 받은 후 XML 매퍼를 사용하도록 지정함.
        return restTemplate.getForEntity(
                pagingQueryHttpUriBase.getUriString(page, pageSize),
                String.class
        );
    }

    public OpenApiManager(PagingQueryHttpUriBase pagingQueryHttpUriBase, RestTemplate restTemplate) {
        this.pagingQueryHttpUriBase = pagingQueryHttpUriBase;
        this.restTemplate = restTemplate;
    }
}
