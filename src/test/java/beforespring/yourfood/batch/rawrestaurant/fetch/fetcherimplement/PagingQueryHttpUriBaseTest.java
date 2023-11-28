package beforespring.yourfood.batch.rawrestaurant.fetch.fetcherimplement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

class PagingQueryHttpUriBaseTest {

    PagingQueryHttpUriBase pagingQueryHttpUriBase;
    URI givenUri = UriComponentsBuilder.fromHttpUrl("http://somename.com").build().toUri();
    String givenSizeParamName = "size";
    String givenPageParamName = "page";
    @BeforeEach
    void init() {
        pagingQueryHttpUriBase = new PagingQueryHttpUriBase(
                givenUri
                , givenSizeParamName,
                givenPageParamName
        );
    }
    @Test
    void getUriComponents() {
        UriComponents expected = UriComponentsBuilder.fromUri(givenUri)
                .queryParam("page", 112)
                .queryParam("size", 100)
                .build();

        UriComponents result = pagingQueryHttpUriBase.getUriComponents(112, 100);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void getUri() {
        URI expected = UriComponentsBuilder.fromUri(givenUri)
                .queryParam("page", 112)
                .queryParam("size", 100)
                .build()
                .toUri();

        URI result = pagingQueryHttpUriBase.getUri(112, 100);

        assertThat(result).isEqualTo(expected);

    }

    @Test
    void getUriString() {
        String expected = UriComponentsBuilder.fromUri(givenUri)
                .queryParam("page", 112)
                .queryParam("size", 100)
                .build()
                .toUriString();  // http://somename.com?page=112&size=100

        String  result = pagingQueryHttpUriBase.getUriString(112, 100);

        assertThat(result).isEqualTo(expected);

    }
}