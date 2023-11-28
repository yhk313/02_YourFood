//package beforespring.yourfood.batch.rawrestaurant.mapping;
//
//import beforespring.yourfood.app.restaurant.domain.CuisineType;
//import beforespring.yourfood.batch.rawrestaurant.exception.MapperProcessingException;
//import beforespring.yourfood.batch.config.ApiConfig;
//import com.fasterxml.jackson.dataformat.xml.XmlMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.web.client.RestTemplateBuilder;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.client.RestTemplate;
//
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//public class OpenApiManagerTest {
//
//    private final RestTemplate restTemplate = new RestTemplate();
//
//    private OpenApiManager openApiManager;
//
//    private final XmlMapper xmlMapper = new XmlMapper();
//
//
//    @BeforeEach
//    public void setUp() {
//        ApiConfig apiConfig = new ApiConfig();
//        XmlMapper xmlMapper = new XmlMapper();
//
//        // OpenApiManagerFactory를 사용하여 OpenApiManager를 생성합니다.
//        OpenApiManagerFactory openApiManagerFactory = new OpenApiManagerFactory(apiConfig.getCuisineTypeNames(), restTemplate, xmlMapper, apiConfig.getDeveloperApiKey());
//        openApiManager = openApiManagerFactory.createOpenApiManager(1, 10, CuisineType.KOREAN);
//    }
//
//    /**
//     * 실제 URL을 이용해서 데이터를 받은 후
//     * XmlMapper를 이용해 객체로 담는 부분을 테스트합니다.
//     */
//    @Test
//    public void test_data_mapping_use_Genrestrt() {
//        String apiUrl = "/Genrestrtlunch?pIndex=1&pSize=10";
//        String url = "https://openapi.gg.go.kr" + apiUrl;
//
//        RestTemplate restTemplate = new RestTemplateBuilder().build();
//        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
//
//        Genrestrt genrestrt;
//        try {
//            genrestrt = xmlMapper.readValue(responseEntity.getBody(), Genrestrt.class);
//        } catch (Exception e) {
//            throw new MapperProcessingException(e);
//        }
//
//        assertNotNull(genrestrt, "Genrestrt 객체가 null이 아니라면 API 호출 및 매핑이 성공적으로 수행된 것으로 가정");
//        /**
//         * 아래 반복문으로 요소 확인하는 부분은 삭제 예정
//         */
//        for (Row row : genrestrt.getRow()) {
//            System.out.println(row.toString());
//        }
//    }
//
//    /**
//     * manager가 데이터를 불러올 수 있어야됩니다.
//     */
//    @Test
//    public void test_data_mapping_use_OpenApiManager() {
//        Genrestrt genrestrt = openApiManager.fetch();
//
//        assertNotNull(genrestrt, "Genrestrt 객체가 null이 아니라면 API 호출 및 매핑이 성공적으로 수행된 것으로 가정");
//        /**
//         * 아래 반복문으로 요소 확인하는 부분은 삭제 예정
//         */
//        for (Row row : genrestrt.getRow()) {
//            System.out.println(row.toString());
//        }
//    }
//
//}
//
//
