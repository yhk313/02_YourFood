package beforespring.yourfood.batch.rawrestaurant.mapping;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Head {
    /**
     * head의 경우 3가지 데이터로 나뉘어 있으며
     * 각각의 필드와 맵핑이 되도록 변수의 이름을 똑같이 맞춰주거나
     * @JacsonXmlProperty 어노테이션을 이용해서 맵핑을 해줘야됩니다.
     */
    @JacksonXmlProperty(localName = "list_total_count")
    public int listTotalCount;

    @JacksonXmlProperty(localName = "RESULT")
    public Result result;

    @JacksonXmlProperty(localName = "api_version")
    public String apiVersion;
}
