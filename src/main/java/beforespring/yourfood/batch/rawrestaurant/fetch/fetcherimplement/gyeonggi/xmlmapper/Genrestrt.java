package beforespring.yourfood.batch.rawrestaurant.fetch.fetcherimplement.gyeonggi.xmlmapper;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter
@ToString
public class Genrestrt {
    /**
     * 공공 데이터 전체를 담기 위한 객체입니다.
     * 공공 데이터의 경우, 전체 데이터와 api버전등을 가지고 있는 head와
     * 각각의 음식점 데이터를 가진 row로 이루어져 있습니다.
     *
     * 해당 객체의 name은 맵핑되지 않아서 전체를 감싸는 데이터의 이름과 달라도 jackson
     * 라이브러리가 데이터를 객체에 잘 담아줍니다.
     */
    public Head head;

    @JacksonXmlElementWrapper(useWrapping = false)
    public List<Row> row;
}
