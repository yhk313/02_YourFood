package beforespring.yourfood.batch.rawrestaurant.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class RawRestaurantId implements Serializable {

    private String BIZPLC_NM;
    private String REFINE_ROADNM_ADDR;

    public RawRestaurantId(
        String BIZPLC_NM,
        String REFINE_ROADNM_ADDR
    ) {
        this.BIZPLC_NM = BIZPLC_NM;
        this.REFINE_ROADNM_ADDR = REFINE_ROADNM_ADDR;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof RawRestaurantId that)) {
            return false;
        }
        return Objects.equals(getBIZPLC_NM(), that.getBIZPLC_NM())
                   && Objects.equals(getREFINE_ROADNM_ADDR(), that.getREFINE_ROADNM_ADDR());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBIZPLC_NM(), getREFINE_ROADNM_ADDR());
    }

    @Override
    public String toString() {
        return "RawRestaurantId[" +
                   "BIZPLC_NM=" + BIZPLC_NM + ", " +
                   "REFINE_ROADNM_ADDR=" + REFINE_ROADNM_ADDR + ']';
    }


}
