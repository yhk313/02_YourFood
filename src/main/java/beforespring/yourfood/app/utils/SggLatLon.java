package beforespring.yourfood.app.utils;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SggLatLon {

    private String siDo;
    private String siGunGu;
    private String lon;
    private String lat;

    @Builder
    public SggLatLon(String siDo, String siGunGu, String lon, String lat) {
        this.siDo = siDo;
        this.siGunGu = siGunGu;
        this.lon = lon;
        this.lat = lat;
    }
}
