package beforespring.yourfood.batch.rawrestaurant.fetch.fetcherimplement.gyeonggi.xmlmapper;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Result {
    /**
     * 해당 부분에서는 변수명과 데이터의 이름이 같아서
     * 어노테이션을 주지 않아도 데이터를 제대로 담아줍니다.
     */
    public String CODE;
    public String MESSAGE;
}