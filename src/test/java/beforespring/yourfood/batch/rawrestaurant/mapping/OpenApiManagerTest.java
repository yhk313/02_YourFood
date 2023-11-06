package beforespring.yourfood.batch.rawrestaurant.mapping;

import beforespring.yourfood.batch.rawrestaurant.mapping.exception.MapperProcessingException;
import beforespring.yourfood.config.ApiConfig;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OpenApiManagerTest {

    private final RestTemplate restTemplate = new RestTemplate();

    private OpenApiManager openApiManager;

    private final XmlMapper xmlMapper = new XmlMapper();


    @BeforeEach
    public void setUp() {
        ApiConfig apiConfig = new ApiConfig();
        openApiManager = new OpenApiManager(apiConfig, xmlMapper);
    }

    /**
     * 실제 URL을 이용해서 데이터를 받은 후
     * XmlMapper를 이용해 객체로 담는 부분을 테스트합니다.
     */
    @Test
    public void test_data_mapping_use_Genrestrt() {
        String apiUrl = "/Genrestrtlunch?pIndex=1&pSize=10";
        String url = "https://openapi.gg.go.kr" + apiUrl;

        RestTemplate restTemplate = new RestTemplateBuilder().build();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        Genrestrt genrestrt;
        try {
            genrestrt = xmlMapper.readValue(responseEntity.getBody(), Genrestrt.class);
        } catch (Exception e) {
            throw new MapperProcessingException(e);
        }

        assertNotNull(genrestrt, "Genrestrt 객체가 null이 아니라면 API 호출 및 매핑이 성공적으로 수행된 것으로 가정");
        /**
         * 아래 반복문으로 요소 확인하는 부분은 삭제 예정
         */
        for (Row row : genrestrt.getRow()) {
            System.out.println(row.toString());
        }
    }

    /**
     * manager가 데이터를 불러올 수 있어야됩니다.
     */
    @Test
    public void test_data_mapping_use_OpenApiManager() {
        int page = 1;
        int pageSize = 10;

        Genrestrt genrestrt = openApiManager.fetch(page, pageSize);

        assertNotNull(genrestrt, "Genrestrt 객체가 null이 아니라면 API 호출 및 매핑이 성공적으로 수행된 것으로 가정");
        /**
         * 아래 반복문으로 요소 확인하는 부분은 삭제 예정
         */
        for (Row row : genrestrt.getRow()) {
            System.out.println(row.toString());
        }
    }

    /**
     * 정상적인 다른 api url을 가지고도 데이터를 받을 수 있어야됩니다.
     */
    @Test
    public void test_data_fetch_another_api_url() {
        String apiUrl = "/Genrestrtcate";
        String url = "https://openapi.gg.go.kr";
        int page = 1;
        int pageSize = 10;

        openApiManager.setApiUrl(apiUrl);
        openApiManager.setBaseUrl(url);

        Genrestrt genrestrt = openApiManager.fetch(page, pageSize);

        assertNotNull(genrestrt, "Genrestrt 객체가 null이 아니라면 API 호출 및 매핑이 성공적으로 수행된 것으로 가정");
        /**
         * 아래 반복문으로 요소 확인하는 부분은 삭제 예정
         */
        for (Row row : genrestrt.getRow()) {
            System.out.println(row.toString());
        }
    }
}
