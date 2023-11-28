package beforespring.yourfood.batch.rawrestaurant.fetch.fetcherimplement;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * 페이징 쿼리시 HTTP URI에 페이지 번호, 페이지 사이즈 파라미터를 지정할수 있도록 하는 클래스
 * ex) url: http://some.com, sizeParamName: size, pageParamName: page, size: 100, page: 10-> http://some.com?size=100&page=10
 */
public class PagingQueryHttpUriBase {
    /**
     * 페이지 크기 파라미터 이름.
     */
    private final String sizeParamName;
    /**
     * 페이지 번호 파라미터 이름.
     */
    private final String pageParamName;
    /**
     * 요청 URI
     * @see URI
     */
    private final URI baseUri;

    public UriComponents getUriComponents(int page, int size) {
        return UriComponentsBuilder.fromUri(baseUri)
                .queryParam(pageParamName, page)
                .queryParam(sizeParamName, size)
                .build();
    }

    public URI getUri(int page, int size) {
        return getUriComponents(page, size).toUri();
    }

    public String getUriString(int page, int size) {
        return getUriComponents(page, size).toUriString();
    }

    public PagingQueryHttpUriBase(URI baseUri, String sizeParamName, String pageParamName) {
        this.baseUri = baseUri;
        this.sizeParamName = sizeParamName;
        this.pageParamName = pageParamName;
    }
}
