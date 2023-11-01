package beforespring.yourfood.app.restaurant.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AddressCode {
    @Column(nullable = false)
    private String sido;
    @Column(nullable = false)
    private String sigungu;
}
