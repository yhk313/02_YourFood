package beforespring.yourfood.app.restaurant.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddressCode {
    @Column(nullable = false)
    private String sido;
    @Column(nullable = false)
    private String sigungu;

    public AddressCode(String sido, String sigungu) {
        this.sido = sido;
        this.sigungu = sigungu;
    }
}
