package beforespring.yourfood.app.utils;

import beforespring.yourfood.web.api.restaurant.response.RegionDto;

import java.util.List;

public interface SggLatLonService {
    List<RegionDto> getAllSggLatLon();
}
